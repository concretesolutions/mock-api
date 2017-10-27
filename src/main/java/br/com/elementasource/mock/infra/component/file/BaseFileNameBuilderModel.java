package br.com.elementasource.mock.infra.component.file;

import br.com.elementasource.mock.infra.property.FileProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("BaseFileNameBuilderModel")
public class BaseFileNameBuilderModel extends BaseFileNameBuilderBase implements BaseFileNameBuilder {

    @Autowired
    public BaseFileNameBuilderModel(@Qualifier("FilePropertyModel") FileProperty fileProperty) {
        super(fileProperty);
    }

}
