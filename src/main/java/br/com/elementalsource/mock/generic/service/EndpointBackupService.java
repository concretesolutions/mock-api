package br.com.elementalsource.mock.generic.service;

import br.com.elementalsource.mock.generic.model.Endpoint;

public interface EndpointBackupService {

    void doBackup(Endpoint endpoint);

    void cleanAllBackupData(); // dangerous

}
