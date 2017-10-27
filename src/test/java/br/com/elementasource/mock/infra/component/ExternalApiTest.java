package br.com.elementasource.mock.infra.component;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.elementasource.mock.infra.model.UriConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.elementasource.mock.generic.model.ExternalApiResult;
import br.com.elementasource.mock.generic.model.Request;
import br.com.elementasource.mock.infra.property.ApiProperty;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExternalApiTest {

	private static final String EXTERNAL_HOST = "http://www.mocky.io";
	private static final String URI = "/v2/57fbd6280f0000ed154fd470";
	// private static final String EXTERNAL_URL = "http://localhost:8080" + URI;

	@Autowired
	private ExternalApi externalApi;

	@MockBean
	private ApiProperty apiProperty;

	@Test(timeout = 5000)
	public void shouldDoExternalGet() {
		// given
		final Request request = new Request.Builder(RequestMethod.GET, URI).build();

		final UriConfiguration configuration = new UriConfiguration();
		configuration.setHost(EXTERNAL_HOST);
		configuration.setBackup(false);
		configuration.setPattern(URI);

		// when
		when(apiProperty.getConfiguration(anyString())).thenReturn(Optional.of(configuration));
		final Optional<ExternalApiResult> responseEntity = externalApi.execute(request);

		// then
		assertTrue(responseEntity.isPresent());
		assertTrue(responseEntity.get().getApiResult().getStatusCode().is2xxSuccessful());
	}

	@Test(timeout = 5000)
	public void shouldDoExternalPost() {
		// given
		final Request request = new Request.Builder(RequestMethod.POST, URI)
				.withBody(Optional.of("{\"name\": \"Paul\"}")).build();

		final UriConfiguration configuration = new UriConfiguration();
		configuration.setHost(EXTERNAL_HOST);
		configuration.setBackup(false);
		configuration.setPattern(URI);

		// when
		when(apiProperty.getConfiguration(anyString())).thenReturn(Optional.of(configuration));
		final Optional<ExternalApiResult> responseEntity = externalApi.execute(request);

		// then
		assertTrue(responseEntity.isPresent());
		assertTrue(responseEntity.get().getApiResult().getStatusCode().is2xxSuccessful());
	}

	@Test(timeout = 8000)
	public void shouldDoExternalPatch() {
		// given
		final Request request = new Request.Builder(RequestMethod.PATCH, URI)
				.withBody(Optional.of("{\"name\": \"Paul\"}")).build();

		final UriConfiguration configuration = new UriConfiguration();
		configuration.setHost(EXTERNAL_HOST);
		configuration.setBackup(false);
		configuration.setPattern(URI);

		// when
		when(apiProperty.getConfiguration(anyString())).thenReturn(Optional.of(configuration));
		final Optional<ExternalApiResult> responseEntity = externalApi.execute(request);

		// then
		assertTrue(responseEntity.isPresent());
		assertTrue(responseEntity.get().getApiResult().getStatusCode().is2xxSuccessful());
	}

}
