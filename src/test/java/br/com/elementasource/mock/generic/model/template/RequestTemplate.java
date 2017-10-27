package br.com.elementasource.mock.generic.model.template;

import br.com.elementasource.mock.generic.model.Request;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

public class RequestTemplate implements TemplateLoader {

    public static final String VALID_EMPTY = "validEmpty";
    public static final String VALID_WITH_HEADERS = "validWithHeaders";
    public static final String VALID_FULL = "validFull";
    public static final String VALID_WITH_LIST = "validWithList";
    public static final String VALID_QUERY_AGE10 = "validQueryAge10";
    public static final String VALID_QUERY_AGE25 = "validQueryAge25";
    public static final String VALID_BODY_ID6 = "validBodyId6";
    public static final String VALID_BODY_ID7 = "validBodyId7";

    @Override
    public void load() {
        Fixture.of(Request.class)
                .addTemplate(VALID_EMPTY, new Rule() {{
                    add("method", RequestMethod.GET);
                    add("uri", "/person/11");
                    add("headers", Optional.empty());
                    add("query", Optional.empty());
                    add("body", Optional.empty());
                }})
                .addTemplate(VALID_WITH_HEADERS, new Rule() {{
                    final HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setAccept(ImmutableList.<MediaType>builder().add(MediaType.APPLICATION_JSON).build());

                    add("headers", Optional.of(httpHeaders));
                }})
                .addTemplate(VALID_FULL)
                .inherits(VALID_WITH_HEADERS, new Rule() {{
                    add("method", RequestMethod.POST);
                    add("uri", "/person/11");
                    add("query", Optional.of(ImmutableMap.<String, Object>builder().put("text", "abc").put("age", 10).build()));
                    add("body", Optional.of("{\"id\": 7, \"name\": \"Paul\" }"));
                }})
                .addTemplate(VALID_WITH_LIST)
                .inherits(VALID_FULL, new Rule() {{
                    add("body", Optional.of(" [{\"id\": 7, \"name\": \"Paul\" },{\"id\": 8, \"name\": \"Peter\" }] "));
                }})
                .addTemplate(VALID_QUERY_AGE10)
                .inherits(VALID_EMPTY, new Rule() {{
                    add("query", Optional.of(ImmutableMap.<String, String>builder().put("age", "10").build()));
                }})
                .addTemplate(VALID_QUERY_AGE25)
                .inherits(VALID_EMPTY, new Rule() {{
                    add("query", Optional.of(ImmutableMap.<String, String>builder().put("age", "25").build()));
                }})
                .addTemplate(VALID_BODY_ID6)
                .inherits(VALID_EMPTY, new Rule() {{
                    add("body", Optional.of("{\"id\": 6 }"));
                }})
                .addTemplate(VALID_BODY_ID7)
                .inherits(VALID_EMPTY, new Rule() {{
                    add("body", Optional.of("{\"id\": 7 }"));
                }});
    }

}
