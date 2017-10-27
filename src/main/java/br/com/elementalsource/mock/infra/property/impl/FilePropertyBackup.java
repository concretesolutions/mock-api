package br.com.elementalsource.mock.infra.property.impl;

import br.com.elementalsource.mock.infra.property.FileProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("FilePropertyBackup")
public class FilePropertyBackup implements FileProperty {

    private final String fileBase;

    public FilePropertyBackup(@Value("${file.backup.path}") String fileBase) {
        this.fileBase = fileBase;
    }

    public String getFileBase() {
        return fileBase;
    }

}
