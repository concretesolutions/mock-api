package br.com.concrete.mock.generic.repository.impl;

import br.com.concrete.mock.generic.mapper.EndpointMapper;
import br.com.concrete.mock.generic.repository.EndpointRepository;
import br.com.concrete.mock.infra.component.file.BaseFileNameBuilder;
import br.com.concrete.mock.infra.property.FileExtensionProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("EndpointRepositoryBackup")
public class EndpointRepositoryBackup extends EndpointRepositoryBase implements EndpointRepository {

    @Autowired
    public EndpointRepositoryBackup(FileExtensionProperty fileExtensionProperty, EndpointMapper endpointMapper, @Qualifier("BaseFileNameBuilderBackup") BaseFileNameBuilder baseFileNameBuilder, EndpointFileFilterRequest endpointFileFilterRequest) {
        super(
                fileExtensionProperty,
                endpointMapper,
                baseFileNameBuilder,
                endpointFileFilterRequest
        );
    }

}
