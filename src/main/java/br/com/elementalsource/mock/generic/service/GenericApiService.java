package br.com.elementalsource.mock.generic.service;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import br.com.elementalsource.mock.generic.model.Request;
import org.springframework.http.ResponseEntity;

public interface GenericApiService {

	Optional<ResponseEntity<String>> genericResponseEntity(Request request);

	Map<String, String> getHeaders(final HttpServletRequest request);

}
