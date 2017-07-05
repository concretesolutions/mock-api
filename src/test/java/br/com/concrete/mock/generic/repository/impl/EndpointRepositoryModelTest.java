package br.com.concrete.mock.generic.repository.impl;

import br.com.concrete.mock.generic.mapper.EndpointMapper;
import br.com.concrete.mock.generic.model.Endpoint;
import br.com.concrete.mock.generic.model.Request;
import br.com.concrete.mock.generic.model.template.EndpointTemplate;
import br.com.concrete.mock.infra.component.file.BaseFileNameBuilderModel;
import br.com.concrete.mock.infra.property.FileExtensionProperty;
import br.com.concrete.mock.infra.property.FileProperty;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Before;
import org.junit.BeforeClass;
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
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointRepositoryModelTest {

    private static final String NAME = "/get/";
    @InjectMocks
    private EndpointRepositoryModel endpointRepositoryModel;
    @Mock
    private FileProperty fileProperty;
    @Mock
    private FileExtensionProperty fileExtensionProperty;
    @Mock
    private EndpointMapper endpointMapper;
    @Mock
    private BaseFileNameBuilderModel baseFileNameBuilder;
    @Mock
    private EndpointFileFilterRequest endpointMockFileFilterRequest;
    private URL resource;
    @Value("${file.base}")
    private String fileBase;
    @Value("${file.extension}")
    private String fileExtension;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.concrete.mock.generic.model.template");
    }

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
    public void shouldFindSomeResponse() throws IOException {
        // given
        final RequestMethod requestMethod = RequestMethod.GET;
        final String requestUrl = "person/11";
        final String basePath = resource.getFile() + requestUrl;
        final Optional<Endpoint> endpoint = Optional.of(Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID));

        // when
        when(endpointMapper.mapper(any(), any(), any())).thenReturn(endpoint);
        when(fileExtensionProperty.getFileExtension()).thenReturn(fileExtension);
        when(baseFileNameBuilder.buildPath(any(), any())).thenReturn(basePath);

        final Collection<Endpoint> mocks = endpointRepositoryModel.getByMethodAndUri(requestMethod, requestUrl);

        // then
        assertNotNull(mocks);
        assertFalse(mocks.isEmpty());
    }

    @Test
    public void shouldNotFindResponseWhenDoNotExists() throws IOException {
        // given
        final RequestMethod requestMethod = RequestMethod.GET;
        final String requestUrl = "/person/66";
        final String basePath = resource.getFile() + requestUrl;

        // when
        when(baseFileNameBuilder.buildPath(any(), any())).thenReturn(basePath);

        final Collection<Endpoint> mocks = endpointRepositoryModel.getByMethodAndUri(requestMethod, requestUrl);

        // then
        assertNotNull(mocks);
        assertTrue(mocks.isEmpty());
    }

    @Test
    public void shouldFilterByMethodAndUriAndQuery() {
        // given
        final String requestUrl = "person/11";
        final String basePath = resource.getFile() + requestUrl;
        final Optional<Endpoint> result = Optional.empty();

        // when
        when(endpointMapper.mapper(any(), any(), any())).thenReturn(result);
        when(fileExtensionProperty.getFileExtension()).thenReturn(fileExtension);
        when(baseFileNameBuilder.buildPath(any(), any())).thenReturn(basePath);
        when(endpointMockFileFilterRequest.apply(any(), any())).thenReturn(true);

        final Optional<Endpoint> endpointMock = endpointRepositoryModel.getByMethodAndRequest(mock(Request.class));

        // then
        assertEquals(result, endpointMock);
    }

}
