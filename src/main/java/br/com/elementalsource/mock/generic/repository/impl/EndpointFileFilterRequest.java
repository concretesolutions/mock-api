package br.com.elementalsource.mock.generic.repository.impl;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class EndpointFileFilterRequest implements EndpointFileFilter<Request> {

    private final EndpointFileFilterQuery endpointFileFilterQuery;
    private final EndpointFileFilterBody endpointMockFileFilterBody;

    @Autowired
    public EndpointFileFilterRequest(EndpointFileFilterQuery endpointFileFilterQuery, EndpointFileFilterBody endpointMockFileFilterBody) {
        this.endpointFileFilterQuery = endpointFileFilterQuery;
        this.endpointMockFileFilterBody = endpointMockFileFilterBody;
    }

    @Override
    public Boolean apply(Endpoint endpoint, Request request) {
        return endpointFileFilterQuery.apply(endpoint, request.getQuery()) &&
                endpointMockFileFilterBody.apply(endpoint, request.getBody());
    }

}
