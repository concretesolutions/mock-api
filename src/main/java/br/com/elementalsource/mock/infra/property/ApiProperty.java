package br.com.elementalsource.mock.infra.property;

import br.com.elementalsource.mock.infra.model.DefaultHeader;
import br.com.elementalsource.mock.infra.model.UriConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperty {

    private List<String> acceptedHeaders = new ArrayList<>();
    private String host;
    private List<UriConfiguration> uriConfigurations;
    private List<DefaultHeader> defaultHeaders;

    public List<String> getAcceptedHeaders() {
        return acceptedHeaders;
    }

    public void setAcceptedHeaders(List<String> acceptedHeaders) {
        this.acceptedHeaders = getAcceptedHeaders().stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public Boolean isAcceptedHeader(final String headerValue) {
        return getAcceptedHeaders().isEmpty() || getAcceptedHeaders().contains(headerValue.toLowerCase());
    }

    public void setUriConfigurations(List<UriConfiguration> uriConfigurations) {
        this.uriConfigurations = uriConfigurations;
    }

    public List<UriConfiguration> getUriConfigurations() {
        return this.uriConfigurations;
    }

    public Optional<UriConfiguration> getConfiguration(String uri) {
        return Optional
                .ofNullable(uriConfigurations)
                .orElse(emptyList())
                .stream()
                .filter(config -> config.getPattern().matcher(uri).find()).findAny();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<DefaultHeader> getDefaultHeaders() {
        if (defaultHeaders == null) {
            defaultHeaders = new ArrayList<>();
        }
        return defaultHeaders;
    }

    public void setDefaultHeaders(List<DefaultHeader> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

}
