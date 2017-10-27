package br.com.elementalsource.mock.infra.component.file;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileJsonReaderTest {

    private static final String NAME = "/get/person/11/my-mock.json";

    @InjectMocks
    private FileJsonReader fileJsonReader;

    private File resource;

    @Value("${file.base}")
    private String fileBase;

    @Before
    public void init() throws URISyntaxException {
        File dir = Paths.get(getClass().getClassLoader().getResource(fileBase).toURI()).toFile();
        this.resource = Paths.get(dir.getAbsolutePath(), NAME).toFile();
    }

    @Test
    public void shouldFileExistsInTest() {
        assertNotNull(resource);
    }

    @Test
    public void shouldReadFile() throws IOException {
        // when
        final Optional<String> jsonFile = fileJsonReader.getJsonByFileName(resource.getAbsolutePath());

        // then
        assertTrue(jsonFile.isPresent());
        assertThat(jsonFile.get(), containsString("name"));
    }

    @Test
    public void shouldReturnEmptyWhenFileNotFound() throws IOException {
        // when
        final Optional<String> jsonFile =
                fileJsonReader.getJsonByFileName(
                        Paths.get(resource.getAbsolutePath(), "-file-not-found"
                        ).toAbsolutePath().toString());

        // then
        assertFalse(jsonFile.isPresent());
    }

}
