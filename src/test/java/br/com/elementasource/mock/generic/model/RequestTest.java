package br.com.elementasource.mock.generic.model;

import br.com.elementasource.mock.generic.model.template.RequestTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestTest {

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("br.com.concrete.mock.generic.model.template");
    }

    @Test
    public void shouldBeValidWhenAllFieldsAreFilled() {
        // given
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_FULL);

        // when
        final Boolean valid = request.isValid();

        // then
        assertTrue(valid);
    }

    @Test
    public void shouldBeValidWhenAllFieldsAreBody() {
        // given
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_WITH_LIST);

        // when
        final Boolean valid = request.isValid();

        // then
        assertTrue(valid);
    }

    @Test
    public void shouldBeValidWhenAllFieldsAreQuery() {
        // given
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_QUERY_AGE25);

        // when
        final Boolean valid = request.isValid();

        // then
        assertTrue(valid);
    }

    @Test
    public void shouldBeValidWhenAllFieldsAreHeaders() {
        // given
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_WITH_HEADERS);

        // when
        final Boolean valid = request.isValid();

        // then
        assertTrue(valid);
    }

    @Test
    public void shouldNotValid() {
        // given
        final Request request = Fixture.from(Request.class).gimme(RequestTemplate.VALID_EMPTY);

        // when
        final Boolean valid = request.isValid();

        // then
        assertFalse(valid);
    }

}
