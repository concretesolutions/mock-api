package br.com.elementalsource.mock.generic.mapper;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Optional;

@Component
public class HeaderMapper {

    public Optional<HttpHeaders> mapper(HttpServletRequest request) {
        final HttpHeaders httpHeaders = new HttpHeaders();

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                final String name = headerNames.nextElement();
                httpHeaders.add(name, request.getHeader(name));
            }
        }

        return Optional.of(httpHeaders);
    }

}
