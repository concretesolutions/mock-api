package br.com.concrete.mock.generic.mapper;

import br.com.concrete.mock.generic.model.Request;
import br.com.concrete.mock.infra.component.FromJsonStringToObjectConverter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class RequestDto implements Serializable {

    private static final Gson GSON = new Gson();

    private final HttpHeaders headers;
    private final Map<String, String> query;
    private final Object body;

    @JsonCreator
    public RequestDto(@JsonProperty("headers") HttpHeaders headers, @JsonProperty("query") Map<String, String> query, @JsonProperty("body") Object body) {
        this.headers = headers;
        this.query = query;
        this.body = body;
    }

    public RequestDto(Request request, FromJsonStringToObjectConverter converter) {
        this(
                request.getHeaders().orElse(null),
                request.getQuery().orElse(null),
                converter.apply(request.getBody())
        );

    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public Object getBody() {
        return body;
    }

    public Request toModel(RequestMethod method, String uri) {
        return new Request.Builder(method, uri)
                .withHeader(headers)
                .withQuery(query)
                .withBody(Optional.ofNullable(body).map(GSON::toJson))
                .build();
    }

}
