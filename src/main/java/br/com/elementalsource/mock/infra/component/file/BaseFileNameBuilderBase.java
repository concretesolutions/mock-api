package br.com.elementalsource.mock.infra.component.file;

import org.springframework.web.bind.annotation.RequestMethod;

import br.com.elementalsource.mock.infra.property.FileProperty;

public class BaseFileNameBuilderBase implements BaseFileNameBuilder {

    private final FileProperty fileProperty;
    
    public BaseFileNameBuilderBase(FileProperty fileProperty) {
        this.fileProperty = fileProperty;
    }

    public String buildPath(RequestMethod requestMethod, String pathUri) {
        return buildPath(fileProperty.getFileBase(), requestMethod.name(), pathUri);
    }

    public String buildPath(String fileBaseName, String methodName, String pathUri) {
        return new StringBuilder()
                .append(fileBaseName)
                .append("/")
                .append(methodName.toLowerCase())
                .append(pathUri)
                .toString();
    }

}
