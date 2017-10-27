package br.com.elementalsource.mock.generic.model.template;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.generic.model.Response;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import static br.com.six2six.fixturefactory.Fixture.of;

import java.util.Optional;

public class EndpointTemplate implements TemplateLoader {

    private static final String VALID_WITHOUT_HTTPSTATUS = "validFull";
    private static final String NOT_VALID = "notValid";
    public static final String VALID = "valid";
    public static final String VALID_FULL = "validFull";
    public static final String VALID_WITH_LIST = "validWithList";
    public static final String VALID_WITH_REQUEST_QUERY_AGE10 = "validWithRequestQueryAge10";
    public static final String VALID_WITH_REQUEST_BODY_ID6 = "validWithRequestBodyId6";


    @Override
    public void load() {
        of(Endpoint.class)
                .addTemplate(VALID, new Rule() {{
                    add("id", Optional.empty());
                    add("request", one(Request.class, RequestTemplate.VALID_EMPTY));
                    add("response", one(Response.class, ResponseTemplate.VALID));
                }})
                .addTemplate(VALID_FULL)
                .inherits(VALID, new Rule() {{
                    add("request", one(Request.class, RequestTemplate.VALID_FULL));
                    add("response", one(Response.class, ResponseTemplate.VALID_FULL));
                }})
                .addTemplate(VALID_WITHOUT_HTTPSTATUS)
                .inherits(VALID_FULL, new Rule() {{
                    add("response", one(Response.class, ResponseTemplate.VALID));
                }})
                .addTemplate(VALID_WITH_LIST)
                .inherits(VALID_FULL, new Rule() {{
                    add("request", one(Request.class, RequestTemplate.VALID_WITH_LIST));
                    add("response", one(Response.class, ResponseTemplate.VALID_WITH_LIST));
                }})
                .addTemplate(VALID_WITH_REQUEST_QUERY_AGE10)
                .inherits(VALID, new Rule() {{
                    add("request", one(Request.class, RequestTemplate.VALID_QUERY_AGE10));
                }})
                .addTemplate(VALID_WITH_REQUEST_BODY_ID6)
                .inherits(VALID, new Rule() {{
                    add("request", one(Request.class, RequestTemplate.VALID_BODY_ID6));
                }})
                .addTemplate(NOT_VALID)
                .inherits(VALID, new Rule() {{
                    add("response", one(Response.class, ResponseTemplate.NOT_VALID));
                }});
    }

}
