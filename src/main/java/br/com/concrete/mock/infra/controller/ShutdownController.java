package br.com.concrete.mock.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ShutdownController {

    @Autowired
    private ApplicationContext appContext;

    @RequestMapping(value = "/shutdown", method = POST)
    public void initiateShutdown() {
        SpringApplication.exit(appContext);
    }
}
