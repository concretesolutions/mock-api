package br.com.concrete.mock.generic.api.v1.controller;

import br.com.concrete.mock.generic.mapper.EndpointDto;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenericApiControllerExternalIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${file.base}")
    private String fileBase;
    @Value("${file.extension}")
    private String fileExtension;

    private File resource;

    @Before
    public void init() throws URISyntaxException {
        this.resource = Paths.get(getClass().getClassLoader().getResource(fileBase).toURI()).toFile();
    }

    @Test
    public void shouldFileExistsInTest() {
        assertNotNull(resource);
    }

    private String getJson(String fileNameExpected) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileNameExpected)));
    }

    @Test(timeout = 10000)
    public void shouldResolvePostWithExternalMock() throws IOException, JSONException {
        shouldResolveWithExternalMock(HttpMethod.POST);
    }

    @Test(timeout = 5000)
    public void shouldResolvePathWithExternalMock() throws IOException, JSONException {
        shouldResolveWithExternalMock(HttpMethod.PATCH);
    }

    private void shouldResolveWithExternalMock(final HttpMethod httpMethod) throws IOException, JSONException {
        final ImmutableMap<String, String> headers = ImmutableMap.<String, String>builder()
                .put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

        shouldResolveWithExternalMock(httpMethod, Optional.of(headers));
    }

    private void shouldResolveWithExternalMock(final HttpMethod httpMethod, final Optional<Map<String, String>> headers)
            throws IOException, JSONException {
        // given
        final String url = "/v2/57fbd6280f0000ed154fd470";

        final HttpStatus httpStatus = HttpStatus.OK;
        final String fileName =
                Paths.get(resource.getAbsolutePath(), httpMethod.name().toLowerCase(), url, "1" + fileExtension)
                        .toAbsolutePath().toString();

        final EndpointDto endpointDto = new Gson().fromJson(getJson(fileName), EndpointDto.class);
        final String requestJson = new Gson().toJson(endpointDto.getRequest().getBody());
        final String responseJson = new Gson().toJson(endpointDto.getResponse().getBody());

        final HttpHeaders httpHeaders = headers.filter(mapHeaders -> !mapHeaders.isEmpty()).map(map -> {
            final HttpHeaders result = new HttpHeaders();
            result.setContentType(MediaType.APPLICATION_JSON);
            return result;
        }).orElse(null);

        final HttpEntity<String> httpEntity = new HttpEntity<>(requestJson, httpHeaders);

        // when
        final ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, httpEntity, String.class);

        // then
        assertEquals(httpStatus, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
        assertNotNull(response.getHeaders().get("Access-Control-Allow-Origin"));
    }

}
