package tech.ada.java.desafio_3.excecao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class RestControllerService extends ResponseEntityExceptionHandler
{
    public static final String METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE = "Campo inválido: '%s'. Causa: '%s'.";
    private static final Logger log = LoggerFactory.getLogger(RestControllerService.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException ex)
    {
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(), HttpStatus.BAD_REQUEST, ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RecursoDuplicadoExcecao.class)
    public ResponseEntity<ErrorResponse> recursoDuplicadoExceptionHandler(RecursoDuplicadoExcecao ex) {
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(), ex.getStatus(), ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
    {
        log.error(ex.getMessage(), ex);
        String errorMessage = getErrorMessages(ex.getBindingResult());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(), httpStatus, errorMessage);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }


    @ExceptionHandler(value = RecursoNaoEncontradoExcecao.class)
    public ResponseEntity<ErrorResponse> recursoNaoEncontradoExceptionHandler(RecursoNaoEncontradoExcecao ex)
    {
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(), HttpStatus.NOT_FOUND, ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> defaultExceptionHandler(Exception ex)
    {
        final ErrorResponse errorResponse = new ErrorResponse(ex.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getErrorMessages(BindingResult bindingResult)
    {
        return Stream.concat(
                bindingResult.getFieldErrors().stream().map(this::getMethodArgumentNotValidErrorMessage),
                bindingResult.getGlobalErrors().stream().map(this::getMethodArgumentNotValidErrorMessage)
        ).collect(Collectors.joining(", "));
    }

    private String getMethodArgumentNotValidErrorMessage(FieldError error)
    {
        return String.format(METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE,
                error.getField(), error.getDefaultMessage());
    }

    private String getMethodArgumentNotValidErrorMessage(ObjectError error)
    {
        return String.format(METHOD_ARGUMENT_NOT_VALID_ERROR_MESSAGE,
                error.getObjectName(), error.getDefaultMessage());
    }

}