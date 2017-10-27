package br.com.elementalsource.mock.infra.exception.impl;

import br.com.elementalsource.mock.infra.exception.ApplicationException;

public class ErrorApplicationException extends ApplicationExceptionImpl implements ApplicationException {

    public ErrorApplicationException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }

}
