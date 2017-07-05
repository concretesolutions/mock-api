package br.com.concrete.mock.generic.mapper;

import br.com.concrete.mock.generic.model.Endpoint;
import br.com.concrete.mock.generic.model.template.EndpointTemplate;
import br.com.concrete.mock.infra.component.impl.FromJsonStringToObjectConverterImpl;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.junit.Assert.*;

public class EndpointDtoTest {

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.concrete.mock.generic.model.template");
    }

    @Test
    public void shouldConvertRequest() {
        // given
        final String json = "{\"request\":{\"body\":[{\"run\":\"7\"}]},\"response\":{\"body\":[{\"age\":8}]}}";

        // when
        final EndpointDto endpointDto = new Gson().fromJson(json, EndpointDto.class);
        final Endpoint endpoint = endpointDto.toModel(RequestMethod.GET, "/product");

        // then
        assertNotNull(endpoint);
        assertNotNull(endpoint.getRequest());
        assertNotNull(endpoint.getRequest().getBody());
        assertTrue(endpoint.getRequest().getBody().isPresent());
        assertEquals("[{\"run\":\"7\"}]", endpoint.getRequest().getBody().get());
    }

    @Test
    public void shouldConvertResponse() {
        // given
        final String json = "{\"request\":{\"body\":[{\"run\":\"7\"}]},\"response\":{\"body\":[{\"age\":8}]}}";

        // when
        final EndpointDto endpointDto = new Gson().fromJson(json, EndpointDto.class);
        final Endpoint endpoint = endpointDto.toModel(RequestMethod.GET, "/product");

        // then
        assertNotNull(endpoint);
        assertNotNull(endpoint.getResponse());
        assertNotNull(endpoint.getResponse().getBody());
        assertEquals("[{\"age\":8.0}]", endpoint.getResponse().getBody());
    }

    @Test
    public void shouldConvertFromModel() {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_FULL);
        final String expectedJson = "{\n" +
                "    \"request\": {\n" +
                "        \"headers\": {\n" +
                "            \"Accept\": [\"application/json\"]\n" +
                "        },\n" +
                "        \"query\": {\n" +
                "            \"age\": 10,\n" +
                "            \"text\": \"abc\"\n" +
                "        },\n" +
                "        \"body\": {\n" +
                "            \"id\": 7,\n" +
                "            \"name\": \"Paul\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"body\": {\n" +
                "            \"name\": \"Paul\"\n" +
                "        },\n" +
                "        \"httpStatus\": 201\n" +
                "    }\n" +
                "}";

        // when
        final EndpointDto endpointDto = new EndpointDto(endpoint, new FromJsonStringToObjectConverterImpl());
        final String json = new Gson().toJson(endpointDto);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

    @Test
    public void shouldConvertFromModelWithoutHttpStatus() {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_FULL);
        final String expectedJson = "{\n" +
                "    \"request\": {\n" +
                "        \"headers\": {\n" +
                "            \"Accept\": [\"application/json\"]\n" +
                "        },\n" +
                "        \"query\": {\n" +
                "            \"age\": 10,\n" +
                "            \"text\": \"abc\"\n" +
                "        },\n" +
                "        \"body\": {\n" +
                "            \"name\": \"Paul\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"body\": {\n" +
                "            \"name\": \"Paul\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        // when
        final EndpointDto endpointDto = new EndpointDto(endpoint, new FromJsonStringToObjectConverterImpl());
        final String json = new Gson().toJson(endpointDto);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

    @Test
    public void shouldConvertFromModelWithList() {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_WITH_LIST);
        final String expectedJson = "{\n" +
                "    \"request\": {\n" +
                "        \"headers\": {\n" +
                "            \"Accept\": [\n" +
                "                \"application/json\"\n" +
                "            ]\n" +
                "        },\n" +
                "        \"query\": {\n" +
                "            \"age\": 10,\n" +
                "            \"text\": \"abc\"\n" +
                "        },\n" +
                "        \"body\": [{\n" +
                "            \"id\": 7,\n" +
                "            \"name\": \"Paul\"\n" +
                "        }, {\n" +
                "            \"id\": 8,\n" +
                "            \"name\": \"Peter\"\n" +
                "        }]\n" +
                "    },\n" +
                "    \"response\": {\n" +
                "        \"body\": [{\n" +
                "            \"name\": \"Paul\"\n" +
                "        }, {\n" +
                "            \"name\": \"Peter\"\n" +
                "        }]\n" +
                "    }\n" +
                "}";

        // when
        final EndpointDto endpointDto = new EndpointDto(endpoint, new FromJsonStringToObjectConverterImpl());
        final String json = new Gson().toJson(endpointDto);

        // then
        JSONAssert.assertEquals(expectedJson, json, false);
    }

}
