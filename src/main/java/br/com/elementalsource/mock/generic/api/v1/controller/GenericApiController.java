package br.com.elementalsource.mock.generic.api.v1.controller;

import br.com.elementalsource.mock.generic.service.GenericApiService;
import br.com.elementalsource.mock.infra.component.JsonFormatter;
import br.com.elementalsource.mock.infra.component.RequestFormatter;
import br.com.elementalsource.mock.generic.api.v1.mapper.RequestMapper;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class GenericApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericApiController.class);

    private final GenericApiService genericApiService;
    private final RequestMapper requestMapper;
    private final JsonFormatter jsonFormatter;
    private final RequestFormatter requestFormatter;

    @Autowired
    public GenericApiController(GenericApiService genericApiService, RequestMapper requestMapper,
                                JsonFormatter jsonFormatter, RequestFormatter requestFormatter) {
        this.genericApiService = genericApiService;
        this.requestMapper = requestMapper;
        this.jsonFormatter = jsonFormatter;
        this.requestFormatter = requestFormatter;
    }

    private void logRequest(final HttpServletRequest request, final Optional<Object> requestBody) {
        LOGGER.info(requestFormatter.generateLog(request, requestBody));
        LOGGER.info("Headers: " + genericApiService.getHeaders(request));
        requestBody.ifPresent(body -> logJson(new Gson().toJson(body)));
    }

    private void logJson(final String jsonString) {
        try {
            LOGGER.info(jsonFormatter.format(jsonString));
        } catch (JsonParseException e) {
            LOGGER.warn("cannot print json: " + jsonString);
        }
    }
    private ResponseEntity<?> interceptRequestAnyMedia(final HttpServletRequest request,
                                                       final Optional<Object> requestBody) {
        logRequest(request, requestBody);

        Optional<ResponseEntity<String>> responseEntity = genericApiService
                .genericResponseEntity(requestMapper.mapper(request, requestBody));

        final ResponseEntity<String> result = responseEntity
                .map(r -> {
                    logJson(r.getBody());
                    return r;
                })
                .orElse(null);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            return result;
        }
    }

    @RequestMapping(value = "/*/**")
    public ResponseEntity<?> interceptRequest(final HttpServletRequest request,
                                              final @RequestBody(required = false) Object requestBodyRaw) {
        return interceptRequestAnyMedia(request, Optional.ofNullable(requestBodyRaw));
    }

    @RequestMapping(value = "/*/**", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> interceptRequestForm(final HttpServletRequest request) {
        return interceptRequestAnyMedia(request, Optional.empty());
    }

}
