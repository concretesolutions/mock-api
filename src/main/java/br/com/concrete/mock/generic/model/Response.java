package br.com.concrete.mock.generic.model;

import org.springframework.http.HttpStatus;

import java.util.Optional;

public class Response {

    private final String body;
    private final Optional<HttpStatus> httpStatus;

    public Response(String body, Optional<HttpStatus> httpStatus) {
        this.body = body;
        this.httpStatus = httpStatus;
    }

    public String getBody() {
        return body;
    }

    public Optional<HttpStatus> getHttpStatus() {
        return httpStatus;
    }

}
