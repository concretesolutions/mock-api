package br.com.elementalsource.mock.generic.mapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Configuration
public class QueryDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDecoder.class);

    @FunctionalInterface
    interface DecoderFunction {
        Map<String, String> decode(Map<String, String> map);
    }

    @Bean
    public DecoderFunction decoderFactoryImplementation(@Value("${decoder.characterEncoding:}") final String characterEncoding) {
        if (StringUtils.isBlank(characterEncoding)) {
            return parameters -> parameters;
        } else {
            final Function<Map.Entry<String, String>, String> decodeFunction = value -> {
                try {
                    return URLDecoder.decode(value.getValue(), characterEncoding);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("Cannot decode URL {}", e);
                    return value.getValue();
                }
            };

            return parameters -> parameters.
                    entrySet().
                    stream().
                    collect(toMap(Map.Entry::getKey, decodeFunction));
        }
    }

}
