package br.com.concrete.mock.infra.component.file;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileJsonReaderTest {

    private static final String NAME = "/get/person/11/my-mock.json";

    @InjectMocks
    private FileJsonReader fileJsonReader;

    private URL resource;

    @Value("${file.base}")
    private String fileBase;

    @Before
    public void init() {
        this.resource = getClass().getClassLoader().getResource(fileBase.concat(NAME));
    }

    @Test
    public void shouldFileExistsInTest() {
        assertNotNull(resource);
        assertNotNull(resource.getFile());
    }

    @Test
    public void shouldReadFile() throws IOException {
        // when
        final Optional<String> jsonFile = fileJsonReader.getJsonByFileName(resource.getFile());

        // then
        assertTrue(jsonFile.isPresent());
        assertThat(jsonFile.get(), containsString("name"));
    }

    @Test
    public void shouldReturnEmptyWhenFileNotFound() throws IOException {
        // when
        final Optional<String> jsonFile = fileJsonReader.getJsonByFileName(resource.getFile() + "-file-not-found");

        // then
        assertFalse(jsonFile.isPresent());
    }

}
