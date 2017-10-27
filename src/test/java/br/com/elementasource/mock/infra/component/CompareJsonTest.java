package br.com.elementasource.mock.infra.component;

import br.com.elementasource.mock.infra.component.impl.ConvertJsonImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CompareJsonTest {

    @InjectMocks
    private CompareJson compareJson;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ConvertJsonImpl convertJson;

    @Test
    public void shouldBeEquivalentWhenIsEqual() throws IOException {
        // given
        final String jsonKey = "{\"id\": 10}";
        final String jsonToCompare = "{ \"id\" :  10 } ";

        // when
        final Boolean equivalent = compareJson.isEquivalent(jsonKey, jsonToCompare);

        // then
        assertTrue(equivalent);
    }

    @Test
    public void shouldBeEquivalentWhenExistsFieldsIgnoreds() throws IOException {
        // given
        final String jsonKey = "{\"id\": 10}";
        final String jsonToCompare = "{ \"id\" :  10, \"age\" : 19 } ";

        // when
        final Boolean equivalent = compareJson.isEquivalent(jsonKey, jsonToCompare);

        // then
        assertTrue(equivalent);
    }

    @Test
    public void shouldBeEquivalentWhenExistsSubObjects() throws IOException {
        // given
        final String jsonKey = "{\"id\": 10, \"anotherObject\": { \"number\" : 2}}";
        final String jsonToCompare = "{\"id\": 10, \"anotherObject\":    {   \"number\" :   2}}";

        // when
        final Boolean equivalent = compareJson.isEquivalent(jsonKey, jsonToCompare);

        // then
        assertTrue(equivalent);
    }

    @Test
    public void shouldNotBeEquivalentWhenValuesAreDifferents() throws IOException {
        // given
        final String jsonKey = "{ \"id\" :  10, \"age\" : 19 } ";
        final String jsonToCompare = "{\"id\": 10}";

        // when
        final Boolean equivalent = compareJson.isEquivalent(jsonKey, jsonToCompare);

        // then
        assertFalse(equivalent);
    }

    @Test
    public void shouldNotBeEquivalentWhenThereIsNoAllFields() throws IOException {
        // given
        final String jsonKey = "{\"id\": 10}";
        final String jsonToCompare = "{ \"id\" :  12 } ";

        // when
        final Boolean equivalent = compareJson.isEquivalent(jsonKey, jsonToCompare);

        // then
        assertFalse(equivalent);
    }

    @Ignore
    @Test
    public void shouldNotBeEquivalentWhenExistsDifferentSubObjects() throws IOException {
        // given
        final String jsonKey = "{\"id\": 10, \"anotherObject\": { \"number\" : 2}}";
        final String jsonToCompare = "{\"id\": 10, \"anotherObject\":    {   \"number\" :   3}}";

        // when
        final Boolean equivalent = compareJson.isEquivalent(jsonKey, jsonToCompare);

        // then
        assertFalse(equivalent);
    }

}
