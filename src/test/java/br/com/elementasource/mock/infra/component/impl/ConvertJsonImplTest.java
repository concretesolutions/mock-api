package br.com.elementasource.mock.infra.component.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConvertJsonImplTest {

    @InjectMocks
    private ConvertJsonImpl convertJsonImpl;

    @Test
    public void shouldConvertJsonObject() {
        // given
        final String json = "{\"name\": \"Paul\"}";

        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("name", "Paul");

        // when
        final HashMap<String, Object> result = convertJsonImpl.apply(json);

        // then
        assertEquals(hashMap, result);
    }


}
