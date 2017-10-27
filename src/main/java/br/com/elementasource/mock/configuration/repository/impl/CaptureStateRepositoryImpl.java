package br.com.elementasource.mock.configuration.repository.impl;

import br.com.elementasource.mock.configuration.model.CaptureState;
import br.com.elementasource.mock.configuration.repository.CaptureStateRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.empty;

@Component
@CacheConfig(cacheNames = "captureState")
public class CaptureStateRepositoryImpl implements CaptureStateRepository {

    private Optional<CaptureState> captureState;

    public CaptureStateRepositoryImpl() {
        delete();
    }

    @Cacheable
    public Optional<CaptureState> getCurrent() {
        return captureState;
    }

    @CacheEvict(allEntries = true)
    public CaptureState save(CaptureState captureState) {
        this.captureState = Optional.ofNullable(captureState);
        return captureState;
    }

    @CacheEvict(allEntries = true)
    public void delete() {
        this.captureState = empty();
    }

}
