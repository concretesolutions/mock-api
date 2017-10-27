package br.com.elementalsource.mock.infra.exception.impl;

import br.com.elementalsource.mock.infra.exception.ApplicationExceptionMessage;

import java.io.Serializable;

public class ApplicationExceptionMessageImpl implements ApplicationExceptionMessage, Serializable {

    private final String codigo;
    private final String mensagem;

    public ApplicationExceptionMessageImpl(String codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String getMensagem() {
        return mensagem;
    }

}
