package br.com.concrete.mock.generic.service;

import br.com.concrete.mock.generic.model.Endpoint;

public interface EndpointBackupService {

    void doBackup(Endpoint endpoint);

    void cleanAllBackupData(); // dangerous

}
