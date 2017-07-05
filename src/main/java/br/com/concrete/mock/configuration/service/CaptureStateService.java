package br.com.concrete.mock.configuration.service;

import br.com.concrete.mock.configuration.model.CaptureState;

import java.util.Optional;

public interface CaptureStateService {
    Optional<CaptureState> getCurrent();

    CaptureState enable();

    void delete();

}
