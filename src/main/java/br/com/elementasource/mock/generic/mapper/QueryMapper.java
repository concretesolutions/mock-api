package br.com.elementasource.mock.generic.mapper;

import br.com.elementasource.mock.infra.exception.impl.ApiApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

@Component
public class QueryMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryMapper.class);

    public Optional<Map<String, String>> mapper(final String queryRequest) {
        return Optional
                .ofNullable(queryRequest)
                .map(query -> {
                    try {
                        return UriComponentsBuilder
                                .fromUri(new URI("localhost?" + query))
                                .build()
                                .getQueryParams();
                    } catch (URISyntaxException e) {
                        final String message = new StringBuilder()
                                .append("Cannot convert queryString # ")
                                .append(query)
                                .append(" # ")
                                .append(e.getMessage())
                                .toString();

                        LOGGER.error(message, e);

                        throw new ApiApplicationException(message);
                    }

                })
                .filter(parameters -> !parameters.isEmpty())
                .map(MultiValueMap::toSingleValueMap);
    }

}
