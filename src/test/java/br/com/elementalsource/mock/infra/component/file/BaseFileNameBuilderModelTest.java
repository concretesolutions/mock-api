package br.com.elementalsource.mock.infra.component.file;

import br.com.elementalsource.mock.infra.property.FileProperty;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URL;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseFileNameBuilderModelTest {

    private URL resource;

    @Autowired
    @Qualifier("FilePropertyModel")
    private FileProperty fileProperty;

    @Autowired
    @Qualifier("BaseFileNameBuilderModel")
    private BaseFileNameBuilder baseFileNameBuilder;

    @Before
    public void init() {
        this.resource = getClass().getClassLoader().getResource(fileProperty.getFileBase());
    }

    @Test
    public void shouldHavePathToTest() {
        assertNotNull(baseFileNameBuilder);
        assertNotNull(fileProperty.getFileBase());
        assertFalse(fileProperty.getFileBase().isEmpty());
        assertNotNull(resource);
        assertNotNull(resource.getFile());
    }

    @Test
    public void shouldBuildPath() {
        // given
        final RequestMethod requestMethod = RequestMethod.GET;
        final String pathUri = "/person";

        // when
        final String path = baseFileNameBuilder.buildPath(requestMethod, pathUri);

        // then
        assertNotNull(path);
        assertThat(path, CoreMatchers.endsWith(fileProperty.getFileBase() + "/get/person"));
    }

}
