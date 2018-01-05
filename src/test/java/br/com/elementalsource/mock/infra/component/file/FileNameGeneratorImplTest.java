package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.infra.property.FileExtensionProperty;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileNameGeneratorImplTest {

    private static final String EXTENSION = ".json";

    @InjectMocks
    private FileNameGeneratorImpl fileNameGenerator;

    @Mock
    private FileExtensionProperty fileExtensionProperty;

    private Path path;

    @Before
    public void init() throws IOException {
        path =
                Files.createTempDirectory("filenametest")
                .toAbsolutePath();
    }

    @Test
    public void shouldHavePathToTest() {
        assertNotNull("Something is wrong with base path", path);
    }

    @Test
    public void shouldGenerateFileWhenFolderIsEmpty() {
        // given
        // empty folder

        // when
        when(fileExtensionProperty.getFileExtension()).thenReturn(EXTENSION);
        final String fileName = fileNameGenerator.fromPath(path.toFile().getAbsolutePath());

        // then
        assertEquals("1", fileName);
    }

    @Test
    public void shouldGenerateFileWhenFolderHave1File() throws IOException {
        // given
        createFileWithName("1");

        // when
        when(fileExtensionProperty.getFileExtension()).thenReturn(EXTENSION);
        final String fileName = fileNameGenerator.fromPath(path.toFile().getAbsolutePath());

        // then
        assertEquals("2", fileName);
    }

    @Test
    public void shouldGenerateFileWhenFolderHave2File() throws IOException {
        // given
        createFileWithName("1");
        createFileWithName("2");

        // when
        when(fileExtensionProperty.getFileExtension()).thenReturn(EXTENSION);
        final String fileName = fileNameGenerator.fromPath(path.toFile().getAbsolutePath());

        // then
        assertEquals("3", fileName);
    }

    @Test
    public void shouldGenerateFileWhenFolderHaveFileNameEqualSizeFilesIntoFolder() throws IOException {
        // given
        createFileWithName("2");
        createFileWithName("3");

        // when
        when(fileExtensionProperty.getFileExtension()).thenReturn(EXTENSION);
        final String fileName = fileNameGenerator.fromPath(path.toFile().getAbsolutePath());

        // then
        assertEquals("4", fileName);
    }

    private void createFileWithName(final String name) throws IOException {
        Files.createFile(Paths.get(path.toFile().getAbsolutePath(), name + EXTENSION));
    }

}
