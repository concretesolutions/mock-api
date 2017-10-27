package br.com.elementalsource.mock.infra.component;

@FunctionalInterface
public interface JsonValueCompiler {

    String compile(final String json);

}
