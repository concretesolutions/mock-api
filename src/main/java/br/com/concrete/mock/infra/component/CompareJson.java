package br.com.concrete.mock.infra.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CompareJson {

    private final ConvertJson convertJson;

    @Autowired
    public CompareJson(ConvertJson convertJson) {
        this.convertJson = convertJson;
    }

    public Boolean isEquivalent(String jsonKey, String jsonToCompare) {
        final HashMap<String, Object> keys = convertJson.apply(jsonKey);
        final HashMap<String, Object> toCompare = convertJson.apply(jsonToCompare);

        for (Object entrySet : keys.entrySet()) {
            Map.Entry entry = (Map.Entry) entrySet;
            final Object valueCompare = toCompare.get(entry.getKey());

            if(valueCompare instanceof LinkedHashMap && entry.getValue() instanceof LinkedHashMap){
                return ((LinkedHashMap)valueCompare).size() == ((LinkedHashMap) entry.getValue()).size();
            }

            if (!entry.getValue().equals(valueCompare)) {
                return false;
            }
        }

        return true;
    }

}
