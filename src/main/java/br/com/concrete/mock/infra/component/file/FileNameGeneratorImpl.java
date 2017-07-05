package br.com.concrete.mock.infra.component.file;

import br.com.concrete.mock.infra.property.FileExtensionProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileNameGeneratorImpl implements FileNameGenerator {

    private final FileExtensionProperty fileExtensionProperty;

    @Autowired
    public FileNameGeneratorImpl(FileExtensionProperty fileExtensionProperty) {
        this.fileExtensionProperty = fileExtensionProperty;
    }

    public String fromPath(final String pathName) {
        try {
            final Long count = Files.list(Paths.get(pathName)).count();
            return getNewFileName(pathName, count);
        } catch (IOException e) {
            return "1";
        }
    }

    private String getNewFileName(final String pathName, final Long fileCount) {
        final long newFileCount = fileCount + 1;
        final String newFileName = getFileName(pathName, newFileCount);
        return Files.exists(Paths.get(newFileName)) ? getNewFileName(pathName, newFileCount) : String.valueOf(newFileCount);
    }

    private String getFileName(final String pathName, final Long count) {
        return Paths.get(pathName, String.valueOf(count) + fileExtensionProperty.getFileExtension()).toFile().getAbsolutePath();
    }

}
