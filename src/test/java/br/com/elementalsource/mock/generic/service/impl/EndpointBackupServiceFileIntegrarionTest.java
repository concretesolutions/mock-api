package br.com.elementalsource.mock.generic.service.impl;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.infra.property.FileExtensionProperty;
import br.com.elementalsource.mock.infra.property.FileProperty;
import br.com.elementalsource.mock.generic.model.template.EndpointTemplate;
import br.com.elementalsource.mock.generic.service.EndpointBackupService;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.json.JSONException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointBackupServiceFileIntegrarionTest {

    private static final String CONFIGURATION_CAPTURE_STATE = "/configuration/capture-state";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    @Qualifier("FilePropertyBackup")
    private FileProperty fileProperty;

    @Autowired
    private FileExtensionProperty fileExtensionProperty;

    @Autowired
    private EndpointBackupService endpointBackupService;

    @Value("${file.base}")
    private String fileBase;

    private String responseJson;
    private String uri;
    private String baseName;
    private String fileName;
    private URL resource;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.elementalsource.mock.generic.model.template");
    }

    @Before
    public void init() throws IOException {
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID);
        responseJson = endpoint.getResponse().getBody();

        uri = endpoint.getRequest().getUri();
        String backupPathName = new File("").getAbsolutePath() + "/" + fileProperty.getFileBase();
        this.baseName = backupPathName + "/";
        fileName = baseName + endpoint.getRequest().getMethod().name().toLowerCase() + uri + "/1" + fileExtensionProperty.getFileExtension();

        deleteBackupFolder();
        this.resource = getClass().getClassLoader().getResource(fileBase);
    }

    @Test
    public void shouldFileExistsInTest() {
        assertNotNull(resource);
        assertNotNull(resource.getFile());
    }

    public void deleteBackupFolder() throws IOException {
        endpointBackupService.cleanAllBackupData();
    }

    @After
    public void setupRestore() throws IOException {
        restTemplate.delete(CONFIGURATION_CAPTURE_STATE + "/disable"); // change setup
        deleteBackupFolder();
    }

    @Test(timeout = 2000)
    @Ignore
    public void shouldDoARequestAndDoNotCaptureBackupWhenIsNotConfigured() throws IOException, JSONException {
        // when
        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
        assertFalse("File shouldn't exists in: " + fileName, Files.exists(Paths.get(fileName)));
    }

    @Test//(timeout = 2000)
    public void shouldDoARequestAndCaptureBackupWhenIsConfigured() throws IOException, JSONException {
        // given
        final String path = "/v2/5928a3aa0f0000140538834a";
        final String name = baseName + "get" + path;

        // when
        restTemplate.postForEntity(CONFIGURATION_CAPTURE_STATE + "/enable", null, Object.class); // change setup

        final ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals("{  \"response\": {    \"httpStatus\": 204  }}", response.getBody(), false);
        assertTrue("File not exists in: " + name, Files.exists(Paths.get(name)));
    }

}
