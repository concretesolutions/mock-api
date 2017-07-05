package br.com.concrete.mock.generic.model;

import org.springframework.http.ResponseEntity;

import br.com.concrete.mock.infra.model.UriConfiguration;

public class ExternalApiResult {
	
	private final ResponseEntity<String> apiResult;
	private final UriConfiguration uriConfiguration;
	
	public ExternalApiResult(ResponseEntity<String> apiResult, UriConfiguration uriConfiguration) {
		this.apiResult = apiResult;
		this.uriConfiguration = uriConfiguration;
	}

	public ResponseEntity<String> getApiResult() {
		return apiResult;
	}

	public UriConfiguration getUriConfiguration() {
		return uriConfiguration;
	}
	
}
