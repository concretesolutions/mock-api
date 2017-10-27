package br.com.elementalsource.mock.infra.component.impl;

import br.com.elementalsource.mock.infra.component.ConvertJson;
import br.com.elementalsource.mock.infra.exception.impl.JsonApiApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class ConvertJsonImpl implements ConvertJson {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertJson.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public HashMap<String, Object> apply(String jsonKey) {
        try {
            return OBJECT_MAPPER.readValue(jsonKey, HashMap.class);
        } catch (IOException e) {
            LOGGER.error("Cannot to convert json to HashMap", e);
            throw new JsonApiApplicationException(e.getMessage());
        }
    }

}
