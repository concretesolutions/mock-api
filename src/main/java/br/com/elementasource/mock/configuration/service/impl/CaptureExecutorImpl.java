package br.com.elementasource.mock.configuration.service.impl;

import br.com.elementasource.mock.generic.model.Endpoint;
import br.com.elementasource.mock.generic.model.ExternalApiResult;
import br.com.elementasource.mock.generic.service.EndpointBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.elementasource.mock.configuration.service.CaptureExecutor;

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
