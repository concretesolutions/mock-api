package br.com.concrete.mock.generic.model.template;

import br.com.concrete.mock.generic.model.Response;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class ResponseTemplate implements TemplateLoader {

    public static final String VALID = "valid";
    public static final String VALID_FULL = "valid";
    public static final String VALID_WITH_LIST = "validWithList";
    public static final String NOT_VALID = "notValid";

    @Override
    public void load() {
        Fixture.of(Response.class)
                .addTemplate(VALID, new Rule() {{
                    add("body", "{\"name\": \"Paul\"}");
                    add("httpStatus", Optional.empty());
                }})
                .addTemplate(VALID_FULL)
                .inherits(VALID, new Rule() {{
                    add("body", "{\"name\": \"Paul\"}");
                    add("httpStatus", Optional.of(HttpStatus.CREATED));
                }})
                .addTemplate(VALID_WITH_LIST)
                .inherits(VALID_FULL, new Rule() {{
                    add("body", " [ {\"name\": \"Paul\"}, {\"name\": \"Peter\"} ] ");
                    add("httpStatus", Optional.empty());
                }})
                .addTemplate(NOT_VALID, new Rule() {{
                    add("body", "{\"codigo\": \"422\", \"mensagem\": \"Erro :(\"}");
                    add("httpStatus", Optional.empty());
                }});
    }
}
