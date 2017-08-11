package br.com.concrete.mock.generic.api.v1.controller;

import br.com.concrete.mock.infra.component.QueryStringBuilder;
import br.com.concrete.mock.generic.mapper.EndpointDto;
import br.com.concrete.mock.generic.model.Endpoint;
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
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenericApiControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QueryStringBuilder queryStringBuilder;

    @Value("${file.extension}")
    private String fileExtension;
    @Value("${file.base}")
    private String fileBase;

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
        final Path path = Paths.get(fileNameExpected);
        return Files.exists(path) ? new String(Files.readAllBytes(path)) : "{}";
    }

    private void shouldResolveGetWithLocalMockMatchQueryCaseX(String uri, String caseX) throws IOException, JSONException {
        // given
        final String fileName =
                Paths.get(resource.getAbsolutePath(), "get", uri, caseX + fileExtension)
                .toAbsolutePath().toString();

        final String endpointJson = getJson(fileName);

        final EndpointDto endpointDto = new Gson().fromJson(endpointJson, EndpointDto.class);
        final Endpoint endpoint = endpointDto.toModel(RequestMethod.GET, uri);
        final String parameters = endpoint
                .getRequest()
                .getQuery()
                .filter(queryMap -> !queryMap.isEmpty())
                .map(queryMap -> queryStringBuilder.fromMap(queryMap))
                .map(""::concat)
                .orElse("");

        final String responseJson = new Gson().toJson(endpointDto.getResponse().getBody());

        // when
        final ResponseEntity<String> response = restTemplate.getForEntity(uri.concat(parameters), String.class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
    }

    @Test(timeout = 2000)
    public void shouldResolveGetWithSimpleResponseWithoutRequest() throws IOException, JSONException {
        // given
        final String uri = "/guests/132/users/21/cc";
        final String fileName =
                Paths.get(resource.getAbsolutePath(), "get", uri, "1" + fileExtension)
                        .toAbsolutePath().toString();
        final String endpointJson = getJson(fileName);
        final EndpointDto endpointDto = new Gson().fromJson(endpointJson, EndpointDto.class);
        final String responseJson = new Gson().toJson(endpointDto.getResponse().getBody());

        // when
        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
    }

    @Test(timeout = 2000)
    public void shouldResolveGetWithSimpleResponseWithRequest() throws IOException, JSONException {
        // given
        final String uri = "/guests/132/users/22/cc";
        final String fileName =
                Paths.get(resource.getAbsolutePath(), "get", uri, "1" + fileExtension)
                        .toAbsolutePath().toString();
        final String endpointJson = getJson(fileName);
        final EndpointDto endpointDto = new Gson().fromJson(endpointJson, EndpointDto.class);
        final String responseJson = new Gson().toJson(endpointDto.getResponse().getBody());

        // when
        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
    }

    @Test(timeout = 2000)
    public void shouldResolveGetWithLocalMock() throws IOException, JSONException {
        // given
        final String uri = "/users/123";

        final String fileName =
                Paths.get(resource.getAbsolutePath(), "get", uri, "1" + fileExtension)
                        .toAbsolutePath().toString();
        final EndpointDto endpointDto = new Gson().fromJson(getJson(fileName), EndpointDto.class);
        final String responseJson = new Gson().toJson(endpointDto.getResponse().getBody());

        // when
        final ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
    }

    @Test(timeout = 2000)
    public void shouldResolveWithHttpStatusCreated() throws IOException, JSONException {
        // given
        final String uri = "/users/123";

        final String fileName =
                Paths.get(resource.getAbsolutePath(), "get", uri, "2" + fileExtension)
                        .toAbsolutePath().toString();
        final EndpointDto endpointDto = new Gson().fromJson(getJson(fileName), EndpointDto.class);
        final String responseJson = new Gson().toJson(endpointDto.getResponse().getBody());
        final String query = queryStringBuilder.fromMap(endpointDto.getRequest().getQuery());

        // when
        final ResponseEntity<String> response = restTemplate.getForEntity(uri + query, String.class);

        // then
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
    }

    @Test(timeout = 2000)
    public void shouldResolvePatchWithLocalMock() throws IOException, JSONException {
        shouldResolveWithLocalMockMatcheRequest("/users/1456", "1", HttpStatus.OK, HttpMethod.PATCH);
    }

    @Test(timeout = 2000)
    public void shouldResolveGetWithLocalMockMatchQueryCase1() throws IOException, JSONException {
        shouldResolveGetWithLocalMockMatchQueryCaseX("/payments/user/detail", "1");
    }

    @Test(timeout = 2000)
    public void shouldResolveGetWithLocalMockMatchQueryCase2() throws IOException, JSONException {
        shouldResolveGetWithLocalMockMatchQueryCaseX("/payments/user/detail", "2");
    }

    // fail
    @Test(timeout = 2000)
    public void shouldResolveGetWithLocalMockWithSubDirectory() throws IOException, JSONException {
        shouldResolveGetWithLocalMockMatchQueryCaseX("/guests/132", "1");
    }

    private void shouldResolvePostWithLocalMockMatcheRequest(final String url, final String caseX, final HttpStatus httpStatus) throws IOException, JSONException {
        shouldResolveWithLocalMockMatcheRequest(url, caseX, httpStatus, HttpMethod.POST);
    }

    private void shouldResolveWithLocalMockMatcheRequest(final String uri, final String caseX, final HttpStatus httpStatus, HttpMethod httpMethod) throws IOException, JSONException {
        // given
        final String fileName =
                Paths.get(resource.getAbsolutePath(), httpMethod.name().toLowerCase(), uri, caseX + fileExtension)
                        .toAbsolutePath().toString();

        final EndpointDto endpointDto = new Gson().fromJson(getJson(fileName), EndpointDto.class);
        final String requestJson = new Gson().toJson(endpointDto.getRequest().getBody());
        final String responseJson = new Gson().toJson(endpointDto.getResponse().getBody());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        // when
        final ResponseEntity<String> response = restTemplate.exchange(uri, httpMethod, request, String.class);

        // then
        assertEquals(httpStatus, response.getStatusCode());
        JSONAssert.assertEquals(responseJson, response.getBody(), false);
    }

    @Test(timeout = 2000)
    public void shouldResolvePostWithLocalMockMatcheRequest() throws IOException, JSONException {
        shouldResolvePostWithLocalMockMatcheRequest("/move/to/country/13", "1", HttpStatus.OK);
    }

    @Test//(timeout = 2000)
    public void shouldResolvePostWithLocalMockMatcheRequestCase1() throws IOException, JSONException {
        shouldResolvePostWithLocalMockMatcheRequest("/move/to/country/13/pi", "1", HttpStatus.OK);
    }

    @Test(timeout = 2000)
    public void shouldResolvePostWithLocalMockMatcheRequestCase2() throws IOException, JSONException {
        shouldResolvePostWithLocalMockMatcheRequest("/move/to/country/13/pi", "2", HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
