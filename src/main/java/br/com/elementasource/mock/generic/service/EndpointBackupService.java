package br.com.elementasource.mock.generic.service;

import br.com.elementasource.mock.generic.model.Endpoint;

public interface EndpointBackupService {

    void doBackup(Endpoint endpoint);

    void cleanAllBackupData(); // dangerous

}
