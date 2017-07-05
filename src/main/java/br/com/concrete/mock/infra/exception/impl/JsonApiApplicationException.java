package br.com.concrete.mock.infra.exception.impl;

import br.com.concrete.mock.infra.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JsonApiApplicationException extends ApplicationExceptionImpl implements ApplicationException {

    public JsonApiApplicationException(String mensagem) {
        super(String.valueOf(HttpStatus.EXPECTATION_FAILED.value()), "Internal Mock API exception on convert json: ".concat(mensagem));
    }

}
