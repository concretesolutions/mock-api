package br.com.concrete.mock.infra.component;

import java.util.Optional;

@FunctionalInterface
public interface FromJsonStringToObjectConverter {

    Object apply(final Optional<String> json);

}
