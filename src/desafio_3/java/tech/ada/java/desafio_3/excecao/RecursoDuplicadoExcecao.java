package tech.ada.java.desafio_3.excecao;

import org.springframework.http.HttpStatus;

public class RecursoDuplicadoExcecao extends RuntimeException {

    public RecursoDuplicadoExcecao() {
    }

    public RecursoDuplicadoExcecao(String message) {
        super(message);
    }

    public RecursoDuplicadoExcecao(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}

