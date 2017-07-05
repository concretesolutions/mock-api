package br.com.concrete.mock.configuration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concrete.mock.configuration.service.CaptureExecutor;
import br.com.concrete.mock.generic.model.Endpoint;
import br.com.concrete.mock.generic.model.ExternalApiResult;
import br.com.concrete.mock.generic.service.EndpointBackupService;

@Service
public class CaptureExecutorImpl implements CaptureExecutor {

	private final EndpointBackupService endpointBackupService;

	@Autowired
	public CaptureExecutorImpl(EndpointBackupService endpointBackupService) {
		this.endpointBackupService = endpointBackupService;
	}

	public void execute(ExternalApiResult apiResult, Endpoint endpoint) {
		if (apiResult.getUriConfiguration().isActiveBackup()) {
			endpointBackupService.doBackup(endpoint);
		}
	}
}
