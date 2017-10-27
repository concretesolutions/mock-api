package br.com.elementasource.mock.infra.component;

import java.util.Map;

public interface QueryStringBuilder {
    String fromMap(Map<String, String> queryMap);
}
