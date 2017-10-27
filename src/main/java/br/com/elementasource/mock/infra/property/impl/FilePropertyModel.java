package br.com.elementasource.mock.infra.property.impl;

import br.com.elementasource.mock.infra.property.FileProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("FilePropertyModel")
public class FilePropertyModel implements FileProperty {

    private final String fileBase;

    public FilePropertyModel(@Value("${file.base}") String fileBase) {
        this.fileBase = fileBase;
    }

    public String getFileBase() {
        return fileBase;
    }

}
