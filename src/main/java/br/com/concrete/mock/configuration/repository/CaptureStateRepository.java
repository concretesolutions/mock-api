package br.com.concrete.mock.configuration.repository;

import br.com.concrete.mock.configuration.model.CaptureState;

import java.util.Optional;

public interface CaptureStateRepository {

    Optional<CaptureState> getCurrent();

    CaptureState save(CaptureState captureState);

    void delete();
}
