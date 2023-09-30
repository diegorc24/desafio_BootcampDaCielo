package tech.ada.java.desafio_bootcampdacielo.excecao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponse
{
    private Class<?> exceptionClass;
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime thrownAt;

    public <T> ErrorResponse(Class<?> exceptionClass, HttpStatus httpStatus, String message)
    {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
        this.thrownAt = LocalDateTime.now();
    }
}
