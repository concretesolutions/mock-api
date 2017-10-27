package br.com.elementalsource.mock.infra.component;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CompareMapTest {

    @InjectMocks
    private CompareMap compareMap;

    @Test
    public void shouldBeEqualsWhenCompareValueMaps() {
        // given
        final ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .build();
        final ImmutableMap<String, String> mapToCompare = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertTrue(isEquivalent);
    }

    @Test
    public void shouldBeEqualsWhenCompareValueMapsWhereHaveMoreAttributesInComparation() {
        // given
        final ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .build();
        final ImmutableMap<String, String> mapToCompare = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .put("age", "15")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertTrue(isEquivalent);
    }

    @Test
    public void shouldNotBeEqualsWhenCompareValueMapsWhereHaveLessAttributesInComparation() {
        // given
        final ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .put("age", "15")
                .build();
        final ImmutableMap<String, String> mapToCompare = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertFalse(isEquivalent);
    }

    @Test
    public void shouldNotBeEqualsWhenThereDifferentValues() {
        // given
        final ImmutableMap<String, String> map = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .put("age", "15")
                .build();
        final ImmutableMap<String, String> mapToCompare = ImmutableMap.<String, String>builder()
                .put("name", "Paul")
                .put("age", "25")
                .build();

        // when
        final Boolean isEquivalent = compareMap.isEquivalent(map, mapToCompare);

        // then
        assertFalse(isEquivalent);
    }

}
