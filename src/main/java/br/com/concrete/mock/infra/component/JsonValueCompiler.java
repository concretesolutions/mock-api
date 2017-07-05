package br.com.concrete.mock.infra.component;

@FunctionalInterface
public interface JsonValueCompiler {

    String compile(final String json);

}
