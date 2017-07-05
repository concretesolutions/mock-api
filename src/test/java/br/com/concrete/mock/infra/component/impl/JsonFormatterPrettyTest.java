package br.com.concrete.mock.infra.component.impl;

import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonFormatterPrettyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    private JsonFormatterPretty jsonFormatterPretty;

    @Test
    public void shouldFormatValidJson() {
        // given
        final String jsonRaw = "{\"age\":10}";
        final String jsonExpected = "{\n" +
                "  \"age\": 10\n" +
                "}\n";

        // when
        final String jsonPretty = jsonFormatterPretty.format(jsonRaw);

        // then
        Assert.assertEquals(jsonExpected, jsonPretty);
    }

    @Test
    public void shouldThrowExceptionWhenIsInvalidJsonFormat() {
        // given
        final String jsonRaw = "invalid json format";

        // excpected exception
        expectedException.expect(JsonParseException.class);

        // when
        jsonFormatterPretty.format(jsonRaw);
    }

}
