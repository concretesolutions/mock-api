package br.com.elementalsource.mock.generic.service.impl;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.infra.component.file.BaseFileNameBuilderModel;
import br.com.elementalsource.mock.infra.component.file.FileNameGenerator;
import br.com.elementalsource.mock.infra.property.FileExtensionProperty;
import br.com.elementalsource.mock.generic.model.template.EndpointTemplate;
import br.com.elementalsource.mock.generic.repository.EndpointRepository;
import br.com.elementalsource.mock.infra.component.FromJsonStringToObjectConverter;
import br.com.elementalsource.mock.infra.component.impl.JsonFormatterPretty;
import br.com.elementalsource.mock.infra.property.FileProperty;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EndpointBackupServiceFileTest {
    private static final String BACKUP_TEMP = "backup-temp/";

    @InjectMocks
    private EndpointBackupServiceFile endpointBackupServiceFile;

    @Mock
    private FileProperty fileProperty;
    @Mock
    private FileExtensionProperty fileExtensionProperty;
    @Mock
    private BaseFileNameBuilderModel baseFileNameBuilder;
    @Mock
    private FileNameGenerator fileNameGenerator;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private FromJsonStringToObjectConverter fromJsonStringToObjectConverter;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private JsonFormatterPretty jsonFormatterPretty;
    @Mock
    private EndpointRepository endpointRepository;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.elementalsource.mock.generic.model.template");
    }

    @Test
    public void shouldDoBackup() throws IOException {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID);
        final String pathName = BACKUP_TEMP + endpoint.getRequest().getUri().concat("/");
        final String fileName = "1";
        final String fileExtension = ".json";
        final Path path = Paths.get(pathName.concat("/").concat(fileName).concat(fileExtension));

        // when
        if (Files.exists(path)) Files.delete(path);
        when(endpointRepository.getByMethodAndRequest(any(Request.class))).thenReturn(Optional.empty());
        when(baseFileNameBuilder.buildPath(anyString(), anyString(), anyString())).thenReturn(pathName);
        when(fileExtensionProperty.getFileExtension()).thenReturn(fileExtension);
        when(fileNameGenerator.fromPath(anyString())).thenReturn(fileName);
        when(fileProperty.getFileBase()).thenReturn(BACKUP_TEMP);

        endpointBackupServiceFile.doBackup(endpoint);

        // then
        assertTrue(Files.exists(path));
    }

    @Test
    @Ignore
    public void shouldRemoveEverithingImportantInsidePath() throws IOException {
        // given
        final String baseNameFilesToRemove = BACKUP_TEMP + "data/files";
        final Path pathsToRemove = Paths.get(baseNameFilesToRemove + "/file1.json");

        final String baseNameFilesToNotRemove = BACKUP_TEMP + ".keep-folder";
        final Path pathsDoNotRemove = Paths.get(baseNameFilesToNotRemove + "/fileDotNotRemove1.json");

        final Path keepFilePath = Paths.get(baseNameFilesToNotRemove + ".keep-file");

        // before
        if (Files.exists(pathsToRemove)) Files.delete(pathsToRemove);
        if (Files.exists(pathsDoNotRemove)) Files.delete(pathsDoNotRemove);
        Files.deleteIfExists(keepFilePath);

        // when
        when(fileProperty.getFileBase()).thenReturn(BACKUP_TEMP);

        Files.createDirectories(Paths.get(baseNameFilesToRemove));
        Files.createFile(pathsToRemove);

        Files.createDirectories(Paths.get(baseNameFilesToNotRemove));
        Files.createFile(pathsDoNotRemove);

        assertTrue(Files.exists(pathsToRemove));
        assertTrue(Files.exists(pathsDoNotRemove));

        endpointBackupServiceFile.cleanAllBackupData();

        // then
        assertFalse(Files.exists(pathsToRemove));
        assertTrue(Files.exists(pathsDoNotRemove));

        // after
        FileSystemUtils.deleteRecursively(new File(baseNameFilesToNotRemove)); // dangerous
    }

}
