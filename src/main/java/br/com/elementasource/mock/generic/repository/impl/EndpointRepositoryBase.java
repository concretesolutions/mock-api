package br.com.elementasource.mock.generic.repository.impl;

import br.com.elementasource.mock.generic.mapper.EndpointMapper;
import br.com.elementasource.mock.generic.model.Endpoint;
import br.com.elementasource.mock.generic.model.Request;
import br.com.elementasource.mock.generic.repository.EndpointRepository;
import br.com.elementasource.mock.infra.component.file.BaseFileNameBuilder;
import br.com.elementasource.mock.infra.property.FileExtensionProperty;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndpointRepositoryBase implements EndpointRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointRepositoryBase.class);

    private final FileExtensionProperty fileExtensionProperty;
    private final EndpointMapper endpointMapper;
    private final BaseFileNameBuilder baseFileNameBuilder;
    private final EndpointFileFilterRequest endpointFileFilterRequest;

    public EndpointRepositoryBase(FileExtensionProperty fileExtensionProperty, EndpointMapper endpointMapper, BaseFileNameBuilder baseFileNameBuilder, EndpointFileFilterRequest endpointFileFilterRequest) {
        this.fileExtensionProperty = fileExtensionProperty;
        this.endpointMapper = endpointMapper;
        this.baseFileNameBuilder = baseFileNameBuilder;
        this.endpointFileFilterRequest = endpointFileFilterRequest;
    }

    @Override
    public Collection<Endpoint> getByMethodAndUri(RequestMethod requestMethod, String requestUrl) {
        return ImmutableList.copyOf(getByMethodAndUriMutable(requestMethod, requestUrl));
    }

    @Override
    public Optional<Endpoint> getByMethodAndRequest(Request request) {
        return getByMethodAndUri(request.getMethod(), request.getUri())
                .stream()
                .sorted()
                .filter(endpointMock -> endpointFileFilterRequest.apply(endpointMock, request))
                .findFirst();
    }

    private List<Endpoint> getByMethodAndUriMutable(RequestMethod requestMethod, String requestUrl) {
        final String basePath = baseFileNameBuilder.buildPath(requestMethod, requestUrl);

        final Path start = Paths.get(basePath);
        if (!Files.exists(start)) {
            return new ArrayList<>();
        }

        final Integer maxDepth = 1;

        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            return stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(fileExtensionProperty.getFileExtension()))
                    .map(fileName -> endpointMapper.mapper(requestMethod, requestUrl, fileName))
                    .filter(o -> o != null)
                    .filter(Optional::isPresent)
                    .filter(a -> a.get() != null)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            final String message = new StringBuilder()
                    .append("Cannot read file from repository: ")
                    .append(basePath)
                    .toString();
            LOGGER.error(message, e);
            return new ArrayList<>();
        }
    }

}
