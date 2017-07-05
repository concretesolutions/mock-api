package br.com.concrete.mock.generic.repository.impl;

import br.com.concrete.mock.generic.model.Endpoint;

@FunctionalInterface
interface EndpointFileFilter<T> {

    Boolean apply(Endpoint endpoint, T request);

}
