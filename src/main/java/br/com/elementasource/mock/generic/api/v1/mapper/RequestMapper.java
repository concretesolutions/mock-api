package br.com.elementasource.mock.generic.api.v1.mapper;

import br.com.elementasource.mock.generic.mapper.HeaderMapper;
import br.com.elementasource.mock.generic.mapper.QueryMapper;
import br.com.elementasource.mock.generic.model.Request;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class RequestMapper {

    private static final Gson GSON = new Gson();

    private final QueryMapper queryMapper;
    private final HeaderMapper headerMapper;

    @Autowired
    public RequestMapper(QueryMapper queryMapper, HeaderMapper headerMapper) {
        this.queryMapper = queryMapper;
        this.headerMapper = headerMapper;
    }

    private Request.Builder mapperBuilder(final HttpServletRequest request) {
        return new Request
                .Builder(RequestMethod.valueOf(request.getMethod().toUpperCase()), request.getRequestURI())
                .withQuery(queryMapper.mapper(request.getQueryString()))
                .withHeader(headerMapper.mapper(request));
    }

    public Request mapper(final HttpServletRequest request) {
        return mapperBuilder(request).build();
    }

    public Request mapper(final HttpServletRequest request, final Optional<Object> requestBody) {
        final Request.Builder requestMockBuilder = mapperBuilder(request);

        return requestBody
                .map(requestBodyJson -> requestMockBuilder.withBody(Optional.ofNullable(GSON.toJson(requestBodyJson))))
                .orElse(requestMockBuilder)
                .build();
    }

}
