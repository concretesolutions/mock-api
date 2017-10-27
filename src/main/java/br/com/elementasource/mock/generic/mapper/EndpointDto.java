package br.com.elementasource.mock.generic.mapper;

import br.com.elementasource.mock.generic.model.Endpoint;
import br.com.elementasource.mock.generic.model.Request;
import br.com.elementasource.mock.generic.model.Response;
import br.com.elementasource.mock.infra.component.FromJsonStringToObjectConverter;
import br.com.elementasource.mock.infra.exception.impl.ApiApplicationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.Optional;

public class EndpointDto implements Serializable {

    private final RequestDto request;
    private final ResponseDto response;

    @JsonCreator
    public EndpointDto(@JsonProperty("request") RequestDto request, @JsonProperty("response") ResponseDto response) {
        this.request = request;
        this.response = response;
    }

    public EndpointDto(Endpoint endpoint, FromJsonStringToObjectConverter converter) {
        this(
                endpoint.getRequest() == null || !endpoint.getRequest().isValid() ? null : new RequestDto(endpoint.getRequest(), converter),
                endpoint.getResponse() == null ? null : new ResponseDto(endpoint.getResponse(), converter)
        );
    }

    public RequestDto getRequest() {
        return request;
    }

    public ResponseDto getResponse() {
        return response;
    }

    public Endpoint toModel(RequestMethod method, String uri) {
        final Request request = Optional
                .ofNullable(this.request)
                .map(requestDto -> requestDto.toModel(method, uri))
                .orElse(new Request.Builder(method, uri).build());

        final Response response = Optional
                .ofNullable(this.response)
                .map(ResponseDto::toModel)
                .orElseThrow(() -> new ApiApplicationException("Cannot deserialize: response is null"));

        return new Endpoint.Builder(request, response).build();
    }

}
