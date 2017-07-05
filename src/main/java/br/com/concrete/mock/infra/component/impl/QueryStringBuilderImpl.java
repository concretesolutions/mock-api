package br.com.concrete.mock.infra.component.impl;

import br.com.concrete.mock.infra.component.ConvertJson;
import br.com.concrete.mock.infra.component.QueryStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QueryStringBuilderImpl implements QueryStringBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryStringBuilderImpl.class);

    private final ConvertJson convertJson;

    @Autowired
    public QueryStringBuilderImpl(ConvertJson convertJson) {
        this.convertJson = convertJson;
    }

    @Override
    public String fromMap(Map<String, String> queryMap) {
        return queryMap
                .entrySet()
                .stream()
                .map(entry -> "&".concat(entry.getKey()).concat("=").concat(entry.getValue()))
                .reduce(String::concat)
                .map(s -> "?" + s.substring(1, s.length()))
                .orElse("");
    }

}
