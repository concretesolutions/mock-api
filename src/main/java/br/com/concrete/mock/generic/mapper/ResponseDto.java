package br.com.concrete.mock.generic.mapper;

import br.com.concrete.mock.generic.model.Response;
import br.com.concrete.mock.infra.component.FromJsonStringToObjectConverter;
import br.com.concrete.mock.infra.exception.impl.ApiApplicationException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Optional;

public class ResponseDto implements Serializable {

    private static final Gson GSON = new Gson();

    private final JsonElement body;
    private final Integer httpStatus;

    @JsonCreator
    public ResponseDto(@JsonProperty("body") JsonElement body, @JsonProperty("httpStatus") Integer httpStatus) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

    public ResponseDto(Response response, FromJsonStringToObjectConverter converter) {
        this(
                converter.apply(Optional.ofNullable(response.getBody())),
                response.getHttpStatus().map(HttpStatus::value).orElse(null)
        );
    }

    public Object getBody() {
        return body;
    }

    public Response toModel() {
        final String bodyResponse = Optional
                .ofNullable(body)
                .map(GSON::toJson)
                .orElseThrow(() -> new ApiApplicationException("Cannot have a null body on response"));
        final Optional<HttpStatus> httpStatusResponse = Optional
                .ofNullable(this.httpStatus)
                .map(HttpStatus::valueOf);
        return new Response(bodyResponse, httpStatusResponse);
    }

}
