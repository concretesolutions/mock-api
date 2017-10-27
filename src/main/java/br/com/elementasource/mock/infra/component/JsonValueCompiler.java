package br.com.elementasource.mock.infra.component;

@FunctionalInterface
public interface JsonValueCompiler {

    String compile(final String json);

}
