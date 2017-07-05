package br.com.concrete.mock.infra.component;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CompareMap {

    public <K, V> Boolean isEquivalent(Map<K, V> value, Map<K, V> valueToCompare) {
        for (Map.Entry<K, V> entrySet : value.entrySet()) {
            final K key = entrySet.getKey();
            if (!valueToCompare.containsKey(key)) {
                return false;
            }

            final V valueCompare = valueToCompare.get(key);
            if (!entrySet.getValue().equals(valueCompare)) {
                return false;
            }
        }

        return true;
    }

}
