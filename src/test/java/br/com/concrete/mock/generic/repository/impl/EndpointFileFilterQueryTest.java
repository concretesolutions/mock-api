package br.com.concrete.mock.generic.repository.impl;

import br.com.concrete.mock.generic.model.Endpoint;
import br.com.concrete.mock.generic.model.Request;
import br.com.concrete.mock.generic.model.template.EndpointTemplate;
import br.com.concrete.mock.generic.model.template.RequestTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndpointFileFilterQueryTest {

    @Autowired
    private EndpointFileFilterQuery endpointMockFileFilter;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.concrete.mock.generic.model.template");
    }

    @Test
    public void shouldBeEquivalentWhenThereIsNoFields() {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID);
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_EMPTY);

        // when
        final Boolean result = endpointMockFileFilter.apply(endpoint, request.getQuery());

        // then
        assertTrue(result);
    }

    @Test
    public void shouldBeEquivalentWhenFieldsAreEqual() {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_WITH_REQUEST_QUERY_AGE10);
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_QUERY_AGE10);

        // when
        final Boolean result = endpointMockFileFilter.apply(endpoint, request.getQuery());

        // then
        assertTrue(result);
    }

    @Test
    public void shouldNotBeEquivalentWhenOneFieldIsNotEqual() {
        // given
        final Endpoint endpoint = Fixture.from(Endpoint.class).gimme(EndpointTemplate.VALID_WITH_REQUEST_QUERY_AGE10);
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_QUERY_AGE25);

        // when
        final Boolean result = endpointMockFileFilter.apply(endpoint, request.getQuery());

        // then
        assertFalse(result);
    }

}
