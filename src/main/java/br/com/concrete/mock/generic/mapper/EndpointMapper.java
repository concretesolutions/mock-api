package br.com.concrete.mock.generic.mapper;

import br.com.concrete.mock.generic.model.Endpoint;
import br.com.concrete.mock.infra.component.file.FileJsonReader;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.Optional;

@Component
public class EndpointMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointMapper.class);

    private final FileJsonReader fileJsonReader;

    @Autowired
    public EndpointMapper(FileJsonReader fileJsonReader) {
        this.fileJsonReader = fileJsonReader;
    }

    public Optional<Endpoint> mapper(RequestMethod requestMethod, String requestUrl, String fileName) {
        try {
            return fileJsonReader
                    .getJsonByFileName(fileName)
                    .map(endpointDtoJson -> new Gson().fromJson(endpointDtoJson, EndpointDto.class))
                    .map(endpointDto -> endpointDto.toModel(requestMethod, requestUrl))
                    .map(endpoint -> new Endpoint.Builder(endpoint).withId(fileName).build());
        } catch (IOException e) {
            LOGGER.error("Cannot to map endpoint from file", e);
            return Optional.empty();
        }
    }

}
