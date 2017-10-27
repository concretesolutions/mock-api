package br.com.elementasource.mock.infra.component.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class FileJsonReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileJsonReader.class);

    public Optional<String> getJsonByFileName(String fileName) throws IOException {
        return Optional
                .of(Paths.get(fileName))
                .filter(path -> Files.exists(path))
                .map(path -> {
                    try {
                        return new String(Files.readAllBytes(path));
                    } catch (IOException e) {
                        LOGGER.error("Cannot to read json from file", e);
                        return null;
                    }
                })
                .filter(json -> json != null);
    }

}
