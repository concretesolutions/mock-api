package br.com.elementasource.mock.infra.component.impl;

import br.com.elementasource.mock.infra.component.RequestFormatter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class RequestFormatterImpl implements RequestFormatter {

    public String generateLog(HttpServletRequest request, Optional<Object> requestBody) {
        return new StringBuilder()
                .append(request.getMethod())
                .append(": ")
                .append(request.getRequestURL())
                .append(request.getQueryString() == null ? "" : "?" + request.getQueryString())
                .toString();
    }

}
