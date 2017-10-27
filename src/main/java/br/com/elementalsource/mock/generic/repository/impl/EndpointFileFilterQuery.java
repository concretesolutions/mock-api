package br.com.elementalsource.mock.generic.repository.impl;

import br.com.elementalsource.mock.infra.component.CompareMap;
import br.com.elementalsource.mock.generic.model.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
class EndpointFileFilterQuery implements EndpointFileFilter<Optional<Map<String, String>>> {

    private final CompareMap compareMap;

    @Autowired
    public EndpointFileFilterQuery(CompareMap compareMap) {
        this.compareMap = compareMap;
    }

    @Override
    public Boolean apply(Endpoint endpoint, Optional<Map<String, String>> request) {
        return request
                .map(requestQuery ->
                        endpoint
                                .getRequest()
                                .getQuery()
                                .map(expetedQuery -> compareMap.isEquivalent(expetedQuery, requestQuery))
                                .orElse(true)
                )
                .orElse(endpoint.getRequest() != null && (!endpoint.getRequest().getQuery().isPresent()));
    }

}
