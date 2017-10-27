package br.com.elementalsource.mock.infra.exception.impl;

import br.com.elementalsource.mock.infra.exception.ApplicationException;
import br.com.elementalsource.mock.infra.exception.ApplicationExceptionMessage;

public class ApplicationExceptionImpl extends RuntimeException implements ApplicationException {

    private final String codigo;

    public ApplicationExceptionImpl(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    @Override
    public ApplicationExceptionMessage buildApplicationExceptionMessage() {
        return new ApplicationExceptionMessageImpl(codigo, getMessage());
    }

}
