package br.com.elementalsource.mock.infra.component;

import java.util.HashMap;

@FunctionalInterface
public interface ConvertJson {

    HashMap<String, Object> apply(String jsonKey);

}
