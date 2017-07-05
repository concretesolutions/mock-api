package br.com.concrete.mock.infra.component.impl;

import br.com.concrete.mock.infra.component.FromJsonStringToObjectConverter;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FromJsonStringToObjectConverterImpl implements FromJsonStringToObjectConverter {

    private static final JsonParser JSON_PARSER = new JsonParser();

    @Override
    public Object apply(final Optional<String> json) {
        return json
                .map(JSON_PARSER::parse)
                .filter(jsonElement -> !jsonElement.isJsonNull())
                .map(jsonElement -> jsonElement.isJsonObject() ?
                        jsonElement.getAsJsonObject() :
                        jsonElement.getAsJsonArray()
                )
                .orElse(JSON_PARSER.parse("").getAsJsonNull());
    }

}
