package br.com.elementasource.mock.infra.component;

import br.com.elementasource.mock.infra.model.DefaultHeader;
import br.com.elementasource.mock.infra.model.UriConfiguration;
import br.com.elementasource.mock.infra.property.ApiProperty;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiPropertyTest {

    @Autowired
    private ApiProperty apiProperty;

    @Test
    public void shouldLoadAcceptHeaders() {
        // when
        final List<String> acceptedHeaders = apiProperty.getAcceptedHeaders();

        // then
        assertNotNull(acceptedHeaders);
        assertFalse(acceptedHeaders.isEmpty());
    }

    @Test
    public void shouldLoadDefaultHeaders() {
        // given
        final ImmutableList<String> headers = ImmutableList.<String>builder().add("Connection", "Keep-Alive").build();
        final DefaultHeader defaultHeader = new DefaultHeader("Access-Control-Allow-Origin", headers);

        // when
        final List<DefaultHeader> defaultHeaders = apiProperty.getDefaultHeaders();

        // then
        assertNotNull(defaultHeaders);
        assertFalse(defaultHeaders.isEmpty());
        assertEquals(defaultHeader, defaultHeaders.get(0));
    }

    @Test
    public void shouldLoadUriConfigurations() {
        // given
        final UriConfiguration uriConfiguration = new UriConfiguration("http://www.mocky.io", Pattern.compile("/v2/57fbd6280f0000ed154fd470", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE), false);

        // when
        final List<UriConfiguration> uriConfigurations = apiProperty.getUriConfigurations();

        // then
        assertNotNull(uriConfigurations);
        assertFalse(uriConfigurations.isEmpty());
        assertEquals(uriConfiguration, uriConfigurations.get(0));
    }

}
