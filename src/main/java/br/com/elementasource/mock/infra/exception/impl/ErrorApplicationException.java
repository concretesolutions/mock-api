package br.com.elementasource.mock.infra.exception.impl;

import br.com.elementasource.mock.infra.exception.ApplicationException;

public class ErrorApplicationException extends ApplicationExceptionImpl implements ApplicationException {

    public ErrorApplicationException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }

}
