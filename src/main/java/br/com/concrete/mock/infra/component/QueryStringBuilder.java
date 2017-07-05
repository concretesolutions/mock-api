package br.com.concrete.mock.infra.component;

import java.util.Map;

public interface QueryStringBuilder {
    String fromMap(Map<String, String> queryMap);
}
