package br.com.elementalsource.mock.generic.service.impl;

import br.com.elementalsource.mock.configuration.service.CaptureExecutor;
import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.generic.model.Request;
import br.com.elementalsource.mock.generic.repository.EndpointRepository;
import br.com.elementalsource.mock.generic.service.GenericApiService;
import br.com.elementalsource.mock.infra.component.ExternalApi;
import br.com.elementalsource.mock.infra.component.JsonValueCompiler;
import br.com.elementalsource.mock.infra.property.ApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GenericApiServiceImpl implements GenericApiService {

    private final EndpointRepository endpointRepository;
    private final ExternalApi externalApi;
    private final CaptureExecutor captureExecutor;
    private final JsonValueCompiler combinedJsonValueCompiler;
    private final ApiProperty apiProperty;

    @Autowired
    public GenericApiServiceImpl(@Qualifier("EndpointRepositoryModel") EndpointRepository endpointRepository,
                                 ExternalApi externalApi, CaptureExecutor captureExecutor, List<JsonValueCompiler> jsonValueCompilers, ApiProperty apiProperty) {
        this.endpointRepository = endpointRepository;
        this.externalApi = externalApi;
        this.captureExecutor = captureExecutor;
        this.combinedJsonValueCompiler = combineFunctions(jsonValueCompilers);
        this.apiProperty = apiProperty;
    }

    private Optional<Endpoint> getEndpoint(Request request) {
        return endpointRepository.getByMethodAndRequest(request);
    }

    // TODO talvez extrair para uma classe
    private JsonValueCompiler combineFunctions(List<JsonValueCompiler> list){
        return list.stream().reduce((a,b) -> (String t) -> a.apply(b.apply(t))).orElse((String t) -> t);
    }

    // TODO refatorar esse método
    @Override
    public Optional<ResponseEntity<String>> genericResponseEntity(Request request) {

        final Optional<ResponseEntity<String>> apiResult = getEndpoint(request).
                map(endpoint -> new ResponseEntity<>(combinedJsonValueCompiler.compile(endpoint.getResponse().getBody()), endpoint.getResponse().getHttpStatus().orElse(HttpStatus.OK))).
                map(Optional::of).
                orElseGet(() -> externalApi.execute(request).map(r -> {
                    captureExecutor.execute(r, new Endpoint.Builder(request, r.getApiResult()).build());
                    return r.getApiResult();
                }));

        return apiResult.
                map(responseEntity -> {
                    final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(responseEntity.getStatusCode());

                    apiProperty.getDefaultHeaders().forEach(header -> {
                        final String headerName = header.getHeaderName();
                        final String[] headerValues = header.getHeaderValues().toArray(new String[0]);
                        bodyBuilder.header(headerName, headerValues);
                    });

                    return bodyBuilder.body(responseEntity.getBody());
                });
    }

    public Map<String, String> getHeaders(final HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}
