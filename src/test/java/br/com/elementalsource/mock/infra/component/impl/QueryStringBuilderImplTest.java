package br.com.elementalsource.mock.infra.component.impl;

import br.com.elementalsource.mock.infra.component.ConvertJson;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryStringBuilderImplTest {

    @InjectMocks
    private QueryStringBuilderImpl queryStringBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ConvertJson convertJson;

    @Test
    public void shouldBeEmpty() {
        // given
        final ImmutableMap<String, String> queryMap = ImmutableMap.<String, String>builder().build();

        // when
        final String queryString = queryStringBuilder.fromMap(queryMap);

        // then
        assertNotNull(queryString);
        assertTrue(queryString.isEmpty());
    }

    @Test
    public void shouldHaveOneParameter() {
        // given
        final ImmutableMap<String, String> queryMap = ImmutableMap.<String, String>builder().put("name", "Paul").build();

        // when
        final String queryString = queryStringBuilder.fromMap(queryMap);

        // then
        assertNotNull(queryString);
        assertFalse(queryString.isEmpty());
        assertEquals("?name=Paul", queryString);
    }

    @Test
    public void shouldHaveTwoParameters() {
        // given
        final ImmutableMap<String, String> queryMap = ImmutableMap.<String, String>builder().put("name", "Paul").put("age", "10").build();

        // when
        final String queryString = queryStringBuilder.fromMap(queryMap);

        // then
        assertNotNull(queryString);
        assertFalse(queryString.isEmpty());
        assertEquals("?name=Paul&age=10", queryString);
    }

}
