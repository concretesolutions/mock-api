package br.com.elementasource.mock.infra.component.file;

import org.springframework.web.bind.annotation.RequestMethod;

public interface BaseFileNameBuilder {

    String buildPath(RequestMethod requestMethod, String pathUri);

    String buildPath(String fileBaseName, String methodName, String pathUri);

}
