package br.com.concrete.mock.configuration.service;

import br.com.concrete.mock.generic.model.Endpoint;
import br.com.concrete.mock.generic.model.ExternalApiResult;

@FunctionalInterface
public interface CaptureExecutor {

	void execute(ExternalApiResult apiResult, Endpoint endpoint);

}
