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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static io.github.avegera.stream.utils.Counters.count;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountersTest {

    @Nested
    @DisplayName("Count of collection")
    class CountOfCollection {

        @Nested
        @DisplayName("returns zero")
        class ReturnsZero {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                long count = Counters.count((Collection<Object>) null);
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                long count = count(new ArrayList<>());
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                long count = count(new HashSet<>());
                assertEquals(0L, count);
            }
        }

        @Nested
        @DisplayName("returns count of items")
        class ReturnsCountOfItems {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNulls(int size) {
                List<Object> list = getList(size, i -> null);
                long count = count(list);
                assertEquals(size, count);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfObjects(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                long count = count(list);
                assertEquals(size, count);
            }
        }
    }

    @Nested
    @DisplayName("Count of varargs")
    class CountOfVarArgs {

        @Nested
        @DisplayName("returns zero")
        class ReturnsZero {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                long count = count((Object[]) null);
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                long count = count();
                assertEquals(0L, count);
            }
        }

        @Nested
        @DisplayName("returns count of items")
        class ReturnsCountOfItems {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for array of nulls")
            void forArrayOfNulls(int size) {
                Object[] array = getArrayOfNulls(size);
                long count = count(array);
                assertEquals(size, count);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for array of objects")
            void forArrayOfObjects(int size) {
                Object[] array = getArray(size);
                long count = count(array);
                assertEquals(size, count);
            }
        }
    }

    @Nested
    @DisplayName("Count collection with predicate")
    class CountCollectionWithPredicate {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(5, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> count(list, null));
            }
        }

        @Nested
        @DisplayName("returns zero")
        class ReturnsZero {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                long count = count((Collection<Organization>) null, PredicateUtils.ORG_WITH_EVEN_ID);
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                long count = count(new ArrayList<>(), PredicateUtils.ORG_WITH_EVEN_ID);
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                long count = count(new HashSet<>(), PredicateUtils.ORG_WITH_EVEN_ID);
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("when no elements match predicate")
            void whenNoElementsMatchPredicate() {
                List<Organization> list = getList(5, OrgUtils::getOrganization);
                long count = count(list, PredicateUtils.ORG_WITH_NEGATIVE_ID);
                assertEquals(0L, count);
            }
        }

        @Nested
        @DisplayName("returns count of matching items")
        class ReturnsCountOfMatchingItems {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for even IDs")
            void forEvenIds(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                long count = count(list, PredicateUtils.ORG_WITH_EVEN_ID);
                assertEquals(size / 2, count);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for odd IDs")
            void forOddIds(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                long count = count(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertEquals((size + 1) / 2, count);
            }

            @Test
            @DisplayName("for always true predicate")
            void forAlwaysTruePredicate() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                long count = count(list, org -> true);
                assertEquals(10L, count);
            }
        }
    }

    @Nested
    @DisplayName("Count array with predicate")
    class CountArrayWithPredicate {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> count(array, null));
            }
        }

        @Nested
        @DisplayName("returns zero")
        class ReturnsZero {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                long count = count((Integer[]) null, PredicateUtils.INTEGER_IS_EVEN);
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Integer[] array = {};
                long count = count(array, PredicateUtils.INTEGER_IS_EVEN);
                assertEquals(0L, count);
            }

            @Test
            @DisplayName("when no elements match predicate")
            void whenNoElementsMatchPredicate() {
                Integer[] array = {1, 3, 5, 7, 9};
                long count = count(array, PredicateUtils.INTEGER_IS_EVEN);
                assertEquals(0L, count);
            }
        }

        @Nested
        @DisplayName("returns count of matching items")
        class ReturnsCountOfMatchingItems {

            @Test
            @DisplayName("for even numbers")
            void forEvenNumbers() {
                Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                long count = count(array, PredicateUtils.INTEGER_IS_EVEN);
                assertEquals(5L, count);
            }

            @Test
            @DisplayName("for odd numbers")
            void forOddNumbers() {
                Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                long count = count(array, PredicateUtils.INTEGER_IS_ODD);
                assertEquals(5L, count);
            }

            @Test
            @DisplayName("for always true predicate")
            void forAlwaysTruePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                long count = count(array, n -> true);
                assertEquals(5L, count);
            }
        }
    }
}