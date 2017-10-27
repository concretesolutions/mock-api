package br.com.elementalsource.mock.configuration.api.v1.controller;

import br.com.elementalsource.mock.configuration.model.CaptureState;
import br.com.elementalsource.mock.configuration.api.v1.mapper.CaptureStateDto;
import br.com.elementalsource.mock.configuration.service.CaptureStateService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CaptureStateController.class)
public class CaptureStateControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CaptureStateService captureStateService;

    @Test
    public void shouldRequestStateDisabledOfCaptureMode() throws Exception {
        // given
        given(captureStateService.getCurrent())
                .willReturn(Optional.empty());

        // when
        final ResultActions result = this.mvc.perform(get("/configuration/capture-state").accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json("{\"enabled\": false}", false));
    }

    @Test
    public void shouldRequestStateEnabledOfCaptureMode() throws Exception {
        // given
        given(captureStateService.getCurrent())
                .willReturn(Optional.of(new CaptureState(true)));

        // when
        final ResultActions result = this.mvc.perform(get("/configuration/capture-state").accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json("{\"enabled\": true}", false));
    }

    @Test
    public void shouldBeEnableCaptureMode() throws Exception {
        // given
        final CaptureState captureState = new CaptureState(true);
        final String requestBody = new Gson().toJson(new CaptureStateDto(captureState));

        given(captureStateService.enable())
                .willReturn(captureState);

        // when
        final ResultActions result = this.mvc.perform(
                post("/configuration/capture-state/enable")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        // then
        result.andExpect(status()
                .isOk())
                .andExpect(content().json("{\"enabled\": true}", false));
    }

    @Test
    public void shouldBeDisableCaptureMode() throws Exception {
        // given
        given(captureStateService.getCurrent())
                .willReturn(Optional.of(new CaptureState(true)));

        // when
        final ResultActions result = this.mvc.perform(delete("/configuration/capture-state/disable").accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        result.andExpect(status().isNoContent());
    }

}
