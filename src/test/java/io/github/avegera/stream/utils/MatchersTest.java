package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import io.github.avegera.stream.utils.test.PredicateUtils;
import io.github.avegera.stream.utils.test.domain.OrgUtils;
import io.github.avegera.stream.utils.test.domain.Organization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.github.avegera.stream.utils.Matchers.*;
import static io.github.avegera.stream.utils.Matchers.noneMatch;
import static io.github.avegera.stream.utils.test.PredicateUtils.*;
import static io.github.avegera.stream.utils.test.TestUtils.getList;
import static io.github.avegera.stream.utils.test.TestUtils.getSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatchersTest {

    private static final int TEST_FIXED_SIZE = 10;

    @Nested
    @DisplayName("Any match")
    class AnyMatch {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> anyMatch(list, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                boolean result = anyMatch(null, ALWAYS_TRUE);
                assertFalse(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                boolean result = anyMatch(new ArrayList<>(), ALWAYS_TRUE);
                assertFalse(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                boolean result = anyMatch(new HashSet<>(), ALWAYS_TRUE);
                assertFalse(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if no matches in list")
            void ifNoMatchesInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = anyMatch(list, PredicateUtils.ORG_WITH_EVEN_ID);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from list")
            void ifMatchedAtLeastOneItemFromList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                boolean result = anyMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from set")
            void ifMatchedAtLeastOneItemFromSet(int size) {
                Set<Organization> set = getSet(size, OrgUtils::getOrganization);
                boolean result = anyMatch(set, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }
        }
    }

    @Nested
    @DisplayName("All match")
    class AllMatch {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> allMatch(list, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not one item matched in list")
            void ifNoOneItemMatchedInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = allMatch(list, ORG_WITH_NEGATIVE_ID);
                assertFalse(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not all items matched in list")
            void ifNotAllItemsMatchedInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                boolean result = allMatch(list, PredicateUtils.ORG_WITH_EVEN_ID);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                boolean result = allMatch(null, ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                boolean result = allMatch(new ArrayList<>(), ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                boolean result = allMatch(new HashSet<>(), ALWAYS_TRUE);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched all items from list")
            void ifMatchedAtLeastOneItemFromList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = allMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched all items from set")
            void ifMatchedAtLeastOneItemFromSet(int size) {
                Set<Organization> set = getSet(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = allMatch(set, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }
        }
    }

    @Nested
    @DisplayName("None match")
    class NoneMatch {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> noneMatch(list, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from list")
            void ifMatchedAtLeastOneItemFromList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = noneMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertFalse(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from set")
            void ifMatchedAtLeastOneItemFromSet(int size) {
                Set<Organization> set = getSet(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = noneMatch(set, PredicateUtils.ORG_WITH_ODD_ID);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                boolean result = noneMatch(null, ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                boolean result = noneMatch(new ArrayList<>(), ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                boolean result = noneMatch(new HashSet<>(), ALWAYS_TRUE);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not one item matched in list")
            void ifNoOneItemMatchedInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = noneMatch(list, ORG_WITH_NEGATIVE_ID);
                assertTrue(result);
            }
        }
    }
}