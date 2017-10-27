package br.com.elementasource.mock.infra.exception.impl;

import br.com.elementasource.mock.infra.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ApiApplicationException extends ApplicationExceptionImpl implements ApplicationException {

    public ApiApplicationException(String mensagem) {
        super(String.valueOf(HttpStatus.EXPECTATION_FAILED.value()), "Internal Mock API exception: ".concat(mensagem));
    }

}
