package br.com.elementasource.mock.generic.repository.impl;

import br.com.elementasource.mock.generic.model.Endpoint;

@FunctionalInterface
interface EndpointFileFilter<T> {

    Boolean apply(Endpoint endpoint, T request);

}
