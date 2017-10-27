package br.com.elementalsource.mock.configuration.service.impl;

import br.com.elementalsource.mock.configuration.service.CaptureExecutor;
import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.ExternalApiResult;
import br.com.elementalsource.mock.generic.service.EndpointBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
