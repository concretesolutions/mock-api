package br.com.concrete.mock.generic.service;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import br.com.concrete.mock.generic.model.Request;

public interface GenericApiService {

	Optional<ResponseEntity<String>> genericResponseEntity(Request request);

	Map<String, String> getHeaders(final HttpServletRequest request);

	Optional<ResponseEntity<String>> genericResponseEntityGET(Request request, HttpServletRequest httpServletRequest);

}
