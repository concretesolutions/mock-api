package br.com.elementasource.mock.configuration.service;

import br.com.elementasource.mock.configuration.model.CaptureState;

import java.util.Optional;

public interface CaptureStateService {
    Optional<CaptureState> getCurrent();

    CaptureState enable();

    void delete();

}
