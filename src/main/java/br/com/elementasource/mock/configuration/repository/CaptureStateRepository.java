package br.com.elementasource.mock.configuration.repository;

import br.com.elementasource.mock.configuration.model.CaptureState;

import java.util.Optional;

public interface CaptureStateRepository {

    Optional<CaptureState> getCurrent();

    CaptureState save(CaptureState captureState);

    void delete();
}
