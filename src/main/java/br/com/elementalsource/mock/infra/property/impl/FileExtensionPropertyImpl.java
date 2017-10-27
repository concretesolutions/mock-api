package br.com.elementalsource.mock.infra.property.impl;

import br.com.elementalsource.mock.infra.property.FileExtensionProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileExtensionPropertyImpl implements FileExtensionProperty {

    private final String fileExtension;

    public FileExtensionPropertyImpl(@Value("${file.extension}") String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

}
