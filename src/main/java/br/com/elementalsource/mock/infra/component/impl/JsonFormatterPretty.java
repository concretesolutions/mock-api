package br.com.elementalsource.mock.infra.component.impl;

import br.com.elementalsource.mock.infra.component.JsonFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

@Component
public class JsonFormatterPretty implements JsonFormatter {

    @Override
    public String format(final String jsonString) {
        final JsonParser parser = new JsonParser();
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final JsonElement el = parser.parse(jsonString);
        return gson.toJson(el).concat("\n");
    }

}
