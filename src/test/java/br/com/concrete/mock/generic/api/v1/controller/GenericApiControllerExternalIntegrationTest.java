package br.com.concrete.mock.generic.api.v1.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import br.com.concrete.mock.generic.mapper.EndpointDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenericApiControllerExternalIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Value("${file.base}")
	private String fileBase;
	@Value("${file.extension}")
	private String fileExtension;

	private URL resource;

	@Before
	public void init() {
		this.resource = getClass().getClassLoader().getResource(fileBase);
	}

	@Test
	public void shouldFileExistsInTest() {
		assertNotNull(resource);
		assertNotNull(resource.getFile());
	}

	private String getJson(String fileNameExpected) throws IOException {
		return new String(Files.readAllBytes(Paths.get(fileNameExpected)));
	}

	@Test(timeout = 10000)
	public void shouldResolvePostWithExternalMock() throws IOException {
		shouldResolveWithExternalMock(HttpMethod.POST);
	}

	@Test(timeout = 5000)
	public void shouldResolvePathWithExternalMock() throws IOException {
		shouldResolveWithExternalMock(HttpMethod.PATCH);
	}

	private void shouldResolveWithExternalMock(final HttpMethod httpMethod) throws IOException {
		final ImmutableMap<String, String> headers = ImmutableMap.<String, String>builder()
				.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		shouldResolveWithExternalMock(httpMethod, Optional.of(headers));
	}

	private void shouldResolveWithExternalMock(final HttpMethod httpMethod, final Optional<Map<String, String>> headers)
			throws IOException {
		// given
		final String url = "/v2/57fbd6280f0000ed154fd470";

		final HttpStatus httpStatus = HttpStatus.OK;
		final String fileName = resource.getFile().concat("/").concat(httpMethod.name().toLowerCase()).concat(url)
				.concat("/1").concat(fileExtension);

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
