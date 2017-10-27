package br.com.elementasource.mock.configuration.api.v1.controller;

import br.com.elementasource.mock.configuration.api.v1.mapper.CaptureStateDto;
import br.com.elementasource.mock.configuration.service.CaptureStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuration/capture-state")
public class CaptureStateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureStateController.class);

    private final CaptureStateService service;

    @Value("${captureState}")
    private boolean captureState =true;

    @Autowired
    public CaptureStateController(CaptureStateService service) {
        this.service = service;
    }

    @Bean
    private CommandLineRunner onLoad() {
        return args -> {
            LOGGER.info("Application capture state: " + captureState);
            if(captureState) {
                this.service.enable();
            }
        };
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<CaptureStateDto> getCurrent() {
        return ResponseEntity.ok().body(new CaptureStateDto(service.getCurrent()));
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResponseEntity<CaptureStateDto> enable() {
        return ResponseEntity.ok().body(new CaptureStateDto(service.enable()));
    }

    @RequestMapping(value = "/disable", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable() {
        service.delete();
    }

}
