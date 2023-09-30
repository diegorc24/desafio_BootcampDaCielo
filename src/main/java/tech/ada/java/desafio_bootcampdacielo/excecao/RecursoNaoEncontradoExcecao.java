package tech.ada.java.desafio_bootcampdacielo.excecao;

import org.springframework.http.HttpStatus;

public class RecursoNaoEncontradoExcecao extends RuntimeException
{
    public RecursoNaoEncontradoExcecao()
    {
    }

    public RecursoNaoEncontradoExcecao(String message)
    {
        super(message);
    }
    public RecursoNaoEncontradoExcecao(String message, Throwable cause)
    {
        super(message, cause);
    }

    public HttpStatus getStatus()
    {
        return HttpStatus.NOT_FOUND;
    }
}
