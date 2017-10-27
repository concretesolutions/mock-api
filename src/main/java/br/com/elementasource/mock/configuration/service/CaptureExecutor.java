package br.com.elementasource.mock.configuration.service;

import br.com.elementasource.mock.generic.model.Endpoint;
import br.com.elementasource.mock.generic.model.ExternalApiResult;

@FunctionalInterface
public interface CaptureExecutor {

	void execute(ExternalApiResult apiResult, Endpoint endpoint);

}
