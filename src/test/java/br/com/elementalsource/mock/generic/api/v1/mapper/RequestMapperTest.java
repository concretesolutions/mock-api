package br.com.elementalsource.mock.generic.api.v1.mapper;

import br.com.elementalsource.mock.generic.mapper.QueryDecoder;
import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.generic.mapper.HeaderMapper;
import br.com.elementalsource.mock.generic.mapper.QueryMapper;
import com.google.common.collect.ImmutableMap;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestMapperTest {

    private RequestMapper requestMapper;

    private QueryMapper queryMapper;
    private HeaderMapper headerMapper;

    @Before
    public void init() {
        this.queryMapper = new QueryMapper(new QueryDecoder().decoderFactoryImplementation(""));
        this.headerMapper = new HeaderMapper();
        this.requestMapper = new RequestMapper(queryMapper, headerMapper);
    }

    @Test
    public void shouldMapRequest() throws URISyntaxException {
        // given
        final MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        final RequestMethod requestMethod = RequestMethod.GET;
        httpServletRequest.setMethod(requestMethod.name().toUpperCase());

        final String uri = "http://localhost/my-request";
        httpServletRequest.setRequestURI(uri);

        // when
        final Request request = requestMapper.mapper(httpServletRequest);

        // then
        assertNotNull(request);
        assertEquals(requestMethod, request.getMethod());
        assertEquals(uri, request.getUri());
        assertTrue(request.getHeaders().isPresent());
        assertTrue(request.getHeaders().get().isEmpty());
        assertFalse(request.getQuery().isPresent());
        assertFalse(request.getBody().isPresent());
    }

    @Test
    public void shouldMapRequestWithHeaders() throws URISyntaxException {
        // given
        final MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        final String key = "keyHeader";
        final String value = "valueHeader";
        httpServletRequest.addHeader(key, value);

        final RequestMethod requestMethod = RequestMethod.GET;
        httpServletRequest.setMethod(requestMethod.name().toUpperCase());

        final String uri = "localhost://my-request";
        httpServletRequest.setRequestURI(uri);

        // whenvalue
        final Request request = requestMapper.mapper(httpServletRequest);

        // then
        assertNotNull(request);
        assertEquals(requestMethod, request.getMethod());
        assertEquals(uri, request.getUri());
        assertTrue(request.getHeaders().isPresent());
        assertTrue(request.getHeaders().get().containsKey(key));
        assertFalse(request.getHeaders().get().get(key).isEmpty());
        assertEquals(value, request.getHeaders().get().get(key).get(0));
    }

    @Test
    public void shouldMapRequestWithQueryString() throws URISyntaxException {
        // given
        final MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        final RequestMethod requestMethod = RequestMethod.GET;
        httpServletRequest.setMethod(requestMethod.name().toUpperCase());

        final String uri = "localhost://my-request";
        httpServletRequest.setRequestURI(uri);

        httpServletRequest.setQueryString("name=Paul&age=10");

        final Map<String, String> expectedQuery = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .put("age", "10")
                .build();

        // when
        final Request request = requestMapper.mapper(httpServletRequest);

        // then
        assertNotNull(request);
        assertEquals(requestMethod, request.getMethod());
        assertEquals(uri, request.getUri());
        assertTrue(request.getQuery().isPresent());
        assertEquals(expectedQuery, request.getQuery().get());
    }

    @Test
    public void shouldMapRequestWithBody() throws URISyntaxException, JSONException {
        // given
        final MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        final RequestMethod requestMethod = RequestMethod.POST;
        httpServletRequest.setMethod(requestMethod.name().toUpperCase());

        final String uri = "localhost://my-request";
        httpServletRequest.setRequestURI(uri);

        final Optional<Object> body = Optional.of(new Person("black"));

        // when
        final Request request = requestMapper.mapper(httpServletRequest, body);

        // then
        assertNotNull(request);
        assertEquals(requestMethod, request.getMethod());
        assertEquals(uri, request.getUri());
        assertTrue(request.getBody().isPresent());
        assertNotNull(request.getBody().get());
        JSONAssert.assertEquals("{\"color\": \"black\"}", request.getBody().get(), false);
    }

    private static class Person implements Serializable {

        private final String color;

        public Person(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

}
