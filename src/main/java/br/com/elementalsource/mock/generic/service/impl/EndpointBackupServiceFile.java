package br.com.elementalsource.mock.generic.service.impl;

import br.com.elementalsource.mock.generic.mapper.EndpointDto;
import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.infra.component.file.BaseFileNameBuilder;
import br.com.elementalsource.mock.infra.component.file.FileNameGenerator;
import br.com.elementalsource.mock.infra.component.impl.JsonFormatterPretty;
import br.com.elementalsource.mock.infra.property.FileExtensionProperty;
import br.com.elementalsource.mock.generic.repository.EndpointRepository;
import br.com.elementalsource.mock.generic.service.EndpointBackupService;
import br.com.elementalsource.mock.infra.component.FromJsonStringToObjectConverter;
import br.com.elementalsource.mock.infra.property.FileProperty;
import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EndpointBackupServiceFile implements EndpointBackupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointBackupServiceFile.class);

    private final FileProperty fileProperty;
    private final FileExtensionProperty fileExtensionProperty;
    private final BaseFileNameBuilder baseFileNameBuilder;
    private final FileNameGenerator fileNameGenerator;
    private final FromJsonStringToObjectConverter fromJsonStringToObjectConverter;
    private final JsonFormatterPretty jsonFormatterPretty;
    private final EndpointRepository endpointRepository;

    @Autowired
    public EndpointBackupServiceFile(@Qualifier("FilePropertyBackup") FileProperty fileProperty, FileExtensionProperty fileExtensionProperty, @Qualifier("BaseFileNameBuilderBackup") BaseFileNameBuilder baseFileNameBuilder, FileNameGenerator fileNameGenerator, FromJsonStringToObjectConverter fromJsonStringToObjectConverter, JsonFormatterPretty jsonFormatterPretty, @Qualifier("EndpointRepositoryBackup") EndpointRepository endpointRepository) {
        this.fileProperty = fileProperty;
        this.fileExtensionProperty = fileExtensionProperty;
        this.baseFileNameBuilder = baseFileNameBuilder;
        this.fileNameGenerator = fileNameGenerator;
        this.fromJsonStringToObjectConverter = fromJsonStringToObjectConverter;
        this.jsonFormatterPretty = jsonFormatterPretty;
        this.endpointRepository = endpointRepository;
    }

    public void doBackup(Endpoint endpoint) {
        final Boolean isNeedToCreateABackup = endpointRepository
                .getByMethodAndRequest(endpoint.getRequest())
                .map(e -> {
                    LOGGER.info("Existent backup not replaced [id=" + e.getId().orElse("no_id") + "]");
                    return false;
                })
                .orElse(true);

        if (isNeedToCreateABackup) execute(endpoint);
    }

    private Boolean execute(final Endpoint endpoint) {
        final Request request = endpoint.getRequest();

        final String pathName = baseFileNameBuilder.buildPath(fileProperty.getFileBase(), request.getMethod().name().toLowerCase(), request.getUri());
        final String fileName = pathName + "/" + fileNameGenerator.fromPath(pathName).concat(fileExtensionProperty.getFileExtension());

        final EndpointDto endpointDto = new EndpointDto(endpoint, fromJsonStringToObjectConverter);
        final String endpointJson = jsonFormatterPretty.format(new Gson().toJson(endpointDto));

        try {
            Files.createDirectories(Paths.get(pathName));
            Files.write(Paths.get(fileName), Arrays.asList(endpointJson), Charset.defaultCharset());
            LOGGER.info("Backup into " + fileName);
        } catch (IOException e) {
            LOGGER.error("Cannot backup endpoint {}", e);
        }
        return true;
    }

    public void cleanAllBackupData() {
        try {
            final String backupPath = fileProperty.getFileBase();
            Files
                    .list(Paths.get(backupPath))
                    .map(path -> path.getFileName().toFile())
                    .filter(file -> !file.getName().startsWith("."))
                    .forEach(FileSystemUtils::deleteRecursively);
        } catch (IOException e) {
            LOGGER.error("Cannot list backup files {}", e);
        }
    }

}
