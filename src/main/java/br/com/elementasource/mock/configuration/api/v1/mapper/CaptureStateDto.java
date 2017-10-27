package br.com.elementasource.mock.configuration.api.v1.mapper;

import br.com.elementasource.mock.configuration.model.CaptureState;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Optional;

public class CaptureStateDto implements Serializable {

    private final Boolean enabled;

    @JsonCreator
    public CaptureStateDto(@JsonProperty("enabled") Boolean enabled) {
        this.enabled = enabled;
    }

    public CaptureStateDto(Optional<CaptureState> captureMode) {
        this(captureMode.map(o -> o.isEnabled()).orElse(false));
    }

    public CaptureStateDto(CaptureState captureState) {
        this(Optional.ofNullable(captureState));
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public CaptureState toModel() {
        return new CaptureState(enabled);
    }

}
