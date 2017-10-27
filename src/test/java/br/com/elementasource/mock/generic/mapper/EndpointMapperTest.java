package br.com.elementasource.mock.generic.mapper;

import br.com.elementasource.mock.infra.component.file.FileJsonReader;
import br.com.elementasource.mock.generic.model.Endpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointMapperTest {

    @Value("${file.base}")
    private String fileBase;
    @Value("${file.extension}")
    private String fileExtension;

    @InjectMocks
    private EndpointMapper endpointMapper;

    @Mock
    private FileJsonReader fileJsonReader;

    private URL resource;

    @Before
    public void init() {
        this.resource = getClass().getClassLoader().getResource(fileBase.concat("/get/"));
    }

    @Test
    public void shouldFileExistsInTest() {
        assertNotNull(resource);
        assertNotNull(resource.getFile());
    }

    @Test
    public void shouldMapFromResponse() throws IOException {
        // given
        final RequestMethod requestMethod = RequestMethod.GET;
        final String requestUrl = "person/11";
        final String basePath = resource.getFile() + requestUrl;
        final String fileNameResponse = basePath + "/my-mock" + fileExtension;

        // when
        when(fileJsonReader.getJsonByFileName(fileNameResponse)).thenReturn(Optional.of("{\"response\":{\"body\":{\"age\": 10}}}"));

        final Optional<Endpoint> endpointMock = endpointMapper.mapper(requestMethod, requestUrl, fileNameResponse);

        // then
        assertNotNull(endpointMock);
        assertTrue(endpointMock.isPresent());
    }

}
