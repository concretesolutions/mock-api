package br.com.elementalsource.mock.infra.component;

import java.util.Map;

public interface QueryStringBuilder {
    String fromMap(Map<String, String> queryMap);
}
