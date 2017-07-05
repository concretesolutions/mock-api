package br.com.concrete.mock.infra.component;

import br.com.concrete.mock.infra.property.ApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class HeaderFilter {
    private final ApiProperty apiProperty;

    @Autowired
    public HeaderFilter(ApiProperty apiProperty) {
        this.apiProperty = apiProperty;
    }

    public Optional<HttpHeaders> execute(Optional<HttpHeaders> headers) {
        return headers
                .map(h -> {
                            final HttpHeaders httpHeaders = new HttpHeaders();

                            h
                                    .entrySet()
                                    .stream()
                                    .filter(value -> apiProperty.isAcceptedHeader(value.getKey()))
                                    .forEach(entry -> {
                                        Optional
                                                .ofNullable(entry.getValue())
                                                .filter(Objects::nonNull)
                                                .filter(value -> !value.isEmpty())
                                                .map(value -> value.stream().findFirst().get())
                                                .ifPresent(value -> httpHeaders.add(entry.getKey(), value));
                                    });

                            return httpHeaders;
                        }
                );
    }


}
