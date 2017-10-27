package br.com.elementasource.mock.generic.repository;

import br.com.elementasource.mock.generic.model.Endpoint;
import br.com.elementasource.mock.generic.model.Request;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.Optional;

public interface EndpointRepository {

    Collection<Endpoint> getByMethodAndUri(RequestMethod requestMethod, String pathUri);

    Optional<Endpoint> getByMethodAndRequest(Request request);

}
