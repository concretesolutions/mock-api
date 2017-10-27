package br.com.elementalsource.mock.configuration.service;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.ExternalApiResult;

@FunctionalInterface
public interface CaptureExecutor {

	void execute(ExternalApiResult apiResult, Endpoint endpoint);

}
