package br.com.elementalsource.mock.infra.component.impl;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParameterJsonValueCompilerImplTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ParameterJsonValueCompilerImpl subject;

    @Test
    public void shouldReplaceManyPatternsAtSameTimeInAJson(){
        String json = "{\"value\":\"xxx\", \"name\":\"#{{param:name}}\", \"other\":\"#{{param:other}}\"}";

        Mockito.when(request.getParameter("name")).thenReturn("John");
        Mockito.when(request.getParameter("other")).thenReturn("Snow");

        String result = subject.compile(json);
        assertEquals("{\"value\":\"xxx\", \"name\":\"John\", \"other\":\"Snow\"}" , result);

    }

}