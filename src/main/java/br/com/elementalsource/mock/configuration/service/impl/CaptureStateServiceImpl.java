package br.com.elementalsource.mock.configuration.service.impl;

import br.com.elementalsource.mock.configuration.model.CaptureState;
import br.com.elementalsource.mock.configuration.service.CaptureStateService;
import br.com.elementalsource.mock.infra.exception.impl.ApiApplicationException;
import br.com.elementalsource.mock.configuration.repository.CaptureStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CaptureStateServiceImpl implements CaptureStateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureStateServiceImpl.class);

    private final CaptureStateRepository repository;

    @Autowired
    public CaptureStateServiceImpl(CaptureStateRepository repository) {
        this.repository = repository;
    }

    public Optional<CaptureState> getCurrent() {
        return repository.getCurrent();
    }

    public CaptureState enable() {
        return repository.save(Optional
                .of(new CaptureState(true))
                .map(old -> {
                    LOGGER.info("Backup activated");
                    return new CaptureState(true);
                })
                .orElseThrow(() -> new ApiApplicationException("cannot have an invalid capture state"))
        );
    }

    public void delete() {
        LOGGER.info("Backup deactivated");
        repository.delete();
    }

}
