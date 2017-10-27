package br.com.elementalsource.mock.infra.component.impl;

import br.com.elementalsource.mock.infra.component.JsonValueCompiler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JsonValueCompilerImpl implements JsonValueCompiler {

    @Override
    public String compile(String json) {
        // http://regexr.com/3f7lq
        final Pattern p = Pattern.compile("\\#\\{\\{(\\d)+daysAgo:([a-z]+?.*?[a-z]+?)\\}\\}", Pattern.CASE_INSENSITIVE);
        final Matcher m = p.matcher(json);

        final StringBuffer sb = new StringBuffer();
        while (m.find()) {
            final Integer daysAgo = Integer.valueOf(m.group(1));
            final String format = m.group(2);

            final String dateFormatted = LocalDate.now().minusDays(daysAgo).format(DateTimeFormatter.ofPattern(format));

            m.appendReplacement(sb, dateFormatted);
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
