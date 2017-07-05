package br.com.concrete.mock.infra.exception;

import br.com.concrete.mock.infra.exception.impl.ApiApplicationException;
import br.com.concrete.mock.infra.exception.impl.ApplicationExceptionImpl;
import br.com.concrete.mock.infra.exception.impl.ApplicationExceptionMessageImpl;
import br.com.concrete.mock.infra.exception.impl.ErrorApplicationException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
    private static final Gson GSON = new Gson();

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationExceptionImpl.class)
    @ResponseBody
    public ApplicationExceptionMessage handleBadRequest(ApplicationException e) {
        final ApplicationExceptionMessage applicationExceptionMessage = e.buildApplicationExceptionMessage();
        LOGGER.error("handleBadRequest", e);
        return applicationExceptionMessage;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ErrorApplicationException.class)
    @ResponseBody
    public ApplicationExceptionMessage handleMockError(ErrorApplicationException e) {
        final ApplicationExceptionMessage applicationExceptionMessage = e.buildApplicationExceptionMessage();
        LOGGER.error("handleMockApiError", e);
        return e.buildApplicationExceptionMessage();
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(ApiApplicationException.class)
    @ResponseBody
    public ApplicationExceptionMessage handleApiException(ApiApplicationException e) {
        final ApplicationExceptionMessage applicationExceptionMessage = e.buildApplicationExceptionMessage();
        LOGGER.error("handleApiException", e);
        return e.buildApplicationExceptionMessage();
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ResourceAccessException.class)
    @ResponseBody
    public ApplicationExceptionMessage handleResourceAccessException(ResourceAccessException e) {
        final ApplicationExceptionMessage applicationExceptionMessage = new ApplicationExceptionMessageImpl(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), e.getMessage());
        LOGGER.error("handleResourceAccessException", e);
        return applicationExceptionMessage;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ApplicationExceptionMessage handleHttpClientErrorException(HttpClientErrorException e) {
        final ApplicationExceptionMessageImpl applicationExceptionMessage = GSON.fromJson(e.getResponseBodyAsString(), ApplicationExceptionMessageImpl.class);
        LOGGER.error("handleHttpClientErrorException", e);
        return applicationExceptionMessage;
    }

}
