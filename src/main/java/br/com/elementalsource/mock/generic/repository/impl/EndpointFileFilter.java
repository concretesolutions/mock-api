package br.com.elementalsource.mock.generic.repository.impl;

import br.com.elementalsource.mock.generic.model.Endpoint;

@FunctionalInterface
interface EndpointFileFilter<T> {

    Boolean apply(Endpoint endpoint, T request);

}
