package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.generic.repository.impl.ExistingFile;
import br.com.elementalsource.mock.generic.repository.impl.ExistingFile.PathParamExtractor;
import br.com.elementalsource.mock.generic.repository.impl.ExistingFiles;
import br.com.elementalsource.mock.infra.property.FileProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Component("BaseFileNameBuilderModel")
public class BaseFileNameBuilderModel extends BaseFileNameBuilderBase implements BaseFileNameBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFileNameBuilderModel.class);
    private ExistingFiles existingFiles;
    private HttpServletRequest request;

    @Autowired
    public BaseFileNameBuilderModel(@Qualifier("FilePropertyModel") FileProperty fileProperty, ExistingFiles existingFiles, HttpServletRequest request) {
        super(fileProperty);
        this.existingFiles = existingFiles;
        this.request = request;
        final String fileBase = fileProperty.getFileBase();
        final File file = new File(fileBase);
        LOGGER.info("Base path to files fileBase={}, exists?{}, path={}", fileBase, file.exists(), file.getAbsoluteFile());
    }

    @Override
    public String buildPath(RequestMethod requestMethod, String pathUri) {
        List<ExistingFile> files = existingFiles.getExistingFiles();
        String rawPath = super.buildPath(requestMethod, pathUri);

        return files.stream()
                .map(ef -> ef.extract(rawPath))
                .filter(PathParamExtractor::matches)
                .peek(pe -> {
                   pe.getParameters().forEach(request::setAttribute);
                })
                .map(pe -> pe.getOriginalPath())
                .findFirst()
                .orElse(rawPath);
    }
}
