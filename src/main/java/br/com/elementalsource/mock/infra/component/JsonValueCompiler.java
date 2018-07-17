package br.com.elementalsource.mock.infra.component;

import java.util.function.Function;

@FunctionalInterface
public interface JsonValueCompiler extends Function<String, String> {

    String compile(final String json);

    @Override
    default String apply(String s) {
        return compile(s);
    }

}
