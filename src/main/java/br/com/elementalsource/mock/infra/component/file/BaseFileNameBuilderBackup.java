package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.infra.property.FileProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("BaseFileNameBuilderBackup")
public class BaseFileNameBuilderBackup extends BaseFileNameBuilderBase implements BaseFileNameBuilder {

    @Autowired
    public BaseFileNameBuilderBackup(@Qualifier("FilePropertyBackup") FileProperty fileProperty) {
        super(fileProperty);
    }

}
