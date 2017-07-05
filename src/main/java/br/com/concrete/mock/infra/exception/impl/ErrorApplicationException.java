package br.com.concrete.mock.infra.exception.impl;

import br.com.concrete.mock.infra.exception.ApplicationException;

public class ErrorApplicationException extends ApplicationExceptionImpl implements ApplicationException {

    public ErrorApplicationException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }

}
