package br.com.elementalsource.mock.infra.component.impl;


import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FromJsonStringToObjectConverterImplTest {

    @InjectMocks
    private FromJsonStringToObjectConverterImpl converter;

    @Test
    public void shouldConvertObject() throws JSONException {
        // given
        final String expectedJson = "{ \"name\": \"Peter\", \"age\": 99}";
        final Optional<String> jsonString = Optional.of(expectedJson);

        // when
        final Object jsonObject = converter.apply(jsonString);
        final String json = new Gson().toJson(jsonObject);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

    @Test
    public void shouldConvertArray() throws JSONException {
        // given
        final String expectedJson = " [ { \"name\": \"Peter\", \"age\": 99}, { \"name\": \"Paul\", \"age\": 98} ]";
        final Optional<String> jsonString = Optional.of(expectedJson);

        // when
        final Object jsonObject = converter.apply(jsonString);
        final String json = new Gson().toJson(jsonObject);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

    @Test
    public void shouldConvertNull() throws JSONException {
        // given
        final String expectedJson = "{ }";
        final Optional<String> jsonString = Optional.of(expectedJson);

        // when
        final Object jsonObject = converter.apply(jsonString);
        final String json = new Gson().toJson(jsonObject);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

}
