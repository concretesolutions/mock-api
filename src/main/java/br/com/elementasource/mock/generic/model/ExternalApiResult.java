package br.com.elementasource.mock.generic.model;

import br.com.elementasource.mock.infra.model.UriConfiguration;
import org.springframework.http.ResponseEntity;

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
