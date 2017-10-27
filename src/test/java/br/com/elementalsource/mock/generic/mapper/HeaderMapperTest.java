package br.com.elementalsource.mock.generic.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class HeaderMapperTest {

    @InjectMocks
    private HeaderMapper headerMapper;

    @Test
    public void shouldConvertHeader() {
        // given
        final String key = "keyHeader";
        final String value = "valueHeader";
        final MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(key, value);

        // when
        final Optional<HttpHeaders> result = headerMapper.mapper(httpServletRequest);

        // then
        assertTrue(result.isPresent());
        assertTrue(result.get().containsKey(key));
        assertFalse(result.get().get(key).isEmpty());
        assertEquals(value, result.get().get(key).get(0));
    }

}
