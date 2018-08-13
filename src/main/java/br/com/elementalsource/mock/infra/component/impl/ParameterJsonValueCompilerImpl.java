package br.com.elementalsource.mock.infra.component.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.elementalsource.mock.infra.component.JsonValueCompiler;
import reactor.ipc.netty.http.server.HttpServerRequest;

@Component
public class ParameterJsonValueCompilerImpl implements JsonValueCompiler {

    @Autowired
    private HttpServletRequest request;

    //https://regexr.com/3sj41
    private static final Pattern PATTERN = Pattern.compile("\\#\\{\\{param:(\\w+)\\}\\}", Pattern.CASE_INSENSITIVE);

    @Override
    public String compile(String json) {

        final Matcher m = PATTERN.matcher(json);

        final StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String paramName = m.group(1);
            String paramValue = getValue(paramName);

            if(paramValue != null){
                m.appendReplacement(sb, paramValue);
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String getValue(String paramName) {
        return Optional.ofNullable(request.getParameter(paramName)).orElse((String) request.getAttribute(paramName));
    }

}
