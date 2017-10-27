package br.com.elementalsource.mock.generic.repository.impl;

import br.com.elementalsource.mock.generic.mapper.EndpointMapper;
import br.com.elementalsource.mock.generic.repository.EndpointRepository;
import br.com.elementalsource.mock.infra.component.file.BaseFileNameBuilder;
import br.com.elementalsource.mock.infra.property.FileExtensionProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("EndpointRepositoryModel")
public class EndpointRepositoryModel extends EndpointRepositoryBase implements EndpointRepository {

    @Autowired
    public EndpointRepositoryModel(FileExtensionProperty fileExtensionProperty, EndpointMapper endpointMapper, @Qualifier("BaseFileNameBuilderModel") BaseFileNameBuilder baseFileNameBuilder, EndpointFileFilterRequest endpointFileFilterRequest) {
        super(
                fileExtensionProperty, endpointMapper,
                baseFileNameBuilder,
                endpointFileFilterRequest
        );
    }

}
