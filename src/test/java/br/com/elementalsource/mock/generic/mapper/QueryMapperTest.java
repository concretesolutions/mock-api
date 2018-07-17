package br.com.elementalsource.mock.generic.mapper;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryMapperTest {

    private QueryMapper queryMapper;

    @Before
    public void init() {
        this.queryMapper = new QueryMapper(new QueryDecoder().decoderFactoryImplementation(""));
    }

    @Test
    public void shouldConvertNullQuery() {
        // given
        final String queryRequest = null;

        // when
        final Optional<Map<String, String>> result = queryMapper.mapper(queryRequest);

        // then
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldConvertEmptyQuery() {
        // given
        final String queryRequest = "";

        // when
        final Optional<Map<String, String>> result = queryMapper.mapper(queryRequest);

        // then
        assertFalse(result.isPresent());
    }

    @Test
    public void shouldConvertQueryWith1Parameter() {
        // given
        final String queryRequest = "name=Paul";
        final Map expectedMap = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .build();

        // when
        final Optional<Map<String, String>> result = queryMapper.mapper(queryRequest);

        // then
        assertTrue(result.isPresent());
        assertEquals(expectedMap, result.get());
    }

    @Test
    public void shouldConvertQueryWith2Parameter() {
        // given
        final String queryRequest = "name=Paul&age=10";
        final Map expectedMap = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .put("age", "10")
                .build();

        // when
        final Optional<Map<String, String>> result = queryMapper.mapper(queryRequest);

        // then
        assertTrue(result.isPresent());
        assertEquals(expectedMap, result.get());
    }

}
