package br.com.elementalsource.mock.generic.repository.impl;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class EndpointFileFilterRequest implements EndpointFileFilter<Request> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointFileFilterRequest.class);

    private final EndpointFileFilterQuery endpointFileFilterQuery;
    private final EndpointFileFilterBody endpointMockFileFilterBody;

    @Autowired
    public EndpointFileFilterRequest(EndpointFileFilterQuery endpointFileFilterQuery, EndpointFileFilterBody endpointMockFileFilterBody) {
        this.endpointFileFilterQuery = endpointFileFilterQuery;
        this.endpointMockFileFilterBody = endpointMockFileFilterBody;
    }

    @Override
    public Boolean apply(Endpoint endpoint, Request request) {
        final Boolean queryFilter = endpointFileFilterQuery.apply(endpoint, request.getQuery());
        final Boolean bodyFilter = endpointMockFileFilterBody.apply(endpoint, request.getBody());

        return queryFilter && bodyFilter;
    }

}