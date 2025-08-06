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

import java.util.*;
import java.util.function.Predicate;

import static io.github.avegera.stream.utils.Matchers.*;
import static io.github.avegera.stream.utils.test.PredicateUtils.*;
import static io.github.avegera.stream.utils.test.TestUtils.getList;
import static io.github.avegera.stream.utils.test.TestUtils.getSet;
import static org.junit.jupiter.api.Assertions.*;

class MatchersTest {

    @Nested
    @DisplayName("anyMatch(Collection<T>, Predicate<T>)")
    class AnyMatchCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> anyMatch(list, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                boolean result = anyMatch((Collection<Object>) null, (Predicate<Object>) ALWAYS_TRUE);
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

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if no matches in list")
            void ifNoMatchesInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = anyMatch(list, ORG_WITH_EVEN_ID);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from list")
            void ifMatchedAtLeastOneItemFromList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                boolean result = anyMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }

            @ParameterizedTest(name = "size = {0}")
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
    @DisplayName("anyMatch(T[], Predicate<T>)")
    class AnyMatchArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> anyMatch(array, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                boolean result = anyMatch((Integer[]) null, (Predicate<Integer>) ALWAYS_TRUE);
                assertFalse(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                boolean result = anyMatch(new Integer[]{}, (Predicate<Integer>) ALWAYS_TRUE);
                assertFalse(result);
            }

            @Test
            @DisplayName("if no matches in array")
            void ifNoMatchesInArray() {
                Integer[] array = {1, 3, 5, 7, 9};
                boolean result = anyMatch(array, n -> n % 2 == 0);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @Test
            @DisplayName("if matched at least one item from array")
            void ifMatchedAtLeastOneItemFromArray() {
                Integer[] array = {1, 2, 3, 4, 5};
                boolean result = anyMatch(array, n -> n % 2 == 0);
                assertTrue(result);
            }

            @Test
            @DisplayName("if matched all items from array")
            void ifMatchedAllItemsFromArray() {
                Integer[] array = {2, 4, 6, 8, 10};
                boolean result = anyMatch(array, n -> n % 2 == 0);
                assertTrue(result);
            }
        }
    }

    @Nested
    @DisplayName("allMatch(Collection<T>, Predicate<T>)")
    class AllMatchCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> allMatch(list, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not one item matched in list")
            void ifNoOneItemMatchedInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = allMatch(list, ORG_WITH_NEGATIVE_ID);
                assertFalse(result);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not all items matched in list")
            void ifNotAllItemsMatchedInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                boolean result = allMatch(list, ORG_WITH_EVEN_ID);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                boolean result = allMatch((Collection<Object>) null, (Predicate<Object>) ALWAYS_TRUE);
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

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched all items from list")
            void ifMatchedAllItemsFromList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = allMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched all items from set")
            void ifMatchedAllItemsFromSet(int size) {
                Set<Organization> set = getSet(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = allMatch(set, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }
        }
    }

    @Nested
    @DisplayName("allMatch(T[], Predicate<T>)")
    class AllMatchArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> allMatch(array, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @Test
            @DisplayName("if not all items matched in array")
            void ifNotAllItemsMatchedInArray() {
                Integer[] array = {1, 2, 3, 4, 5};
                boolean result = allMatch(array, n -> n % 2 == 0);
                assertFalse(result);
            }

            @Test
            @DisplayName("if no items matched in array")
            void ifNoItemsMatchedInArray() {
                Integer[] array = {1, 3, 5, 7, 9};
                boolean result = allMatch(array, n -> n % 2 == 0);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                boolean result = allMatch((Integer[]) null, (Predicate<Integer>) ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                boolean result = allMatch(new Integer[]{}, (Predicate<Integer>) ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("if matched all items from array")
            void ifMatchedAllItemsFromArray() {
                Integer[] array = {2, 4, 6, 8, 10};
                boolean result = allMatch(array, n -> n % 2 == 0);
                assertTrue(result);
            }
        }
    }

    @Nested
    @DisplayName("noneMatch(Collection<T>, Predicate<T>)")
    class NoneMatchCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> noneMatch(list, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from list")
            void ifMatchedAtLeastOneItemFromList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = noneMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertFalse(result);
            }

            @ParameterizedTest(name = "size = {0}")
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
                boolean result = noneMatch((Collection<Object>) null, (Predicate<Object>) ALWAYS_TRUE);
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

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not one item matched in list")
            void ifNoOneItemMatchedInList(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization, INTEGER_IS_ODD);
                boolean result = noneMatch(list, ORG_WITH_NEGATIVE_ID);
                assertTrue(result);
            }
        }
    }

    @Nested
    @DisplayName("noneMatch(T[], Predicate<T>)")
    class NoneMatchArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> noneMatch(array, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @Test
            @DisplayName("if matched at least one item from array")
            void ifMatchedAtLeastOneItemFromArray() {
                Integer[] array = {1, 2, 3, 4, 5};
                boolean result = noneMatch(array, n -> n % 2 == 0);
                assertFalse(result);
            }

            @Test
            @DisplayName("if matched all items from array")
            void ifMatchedAllItemsFromArray() {
                Integer[] array = {2, 4, 6, 8, 10};
                boolean result = noneMatch(array, n -> n % 2 == 0);
                assertFalse(result);
            }
        }

        @Nested
        @DisplayName("returns true")
        class ReturnsTrue {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                boolean result = noneMatch((Integer[]) null, (Predicate<Integer>) ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                boolean result = noneMatch(new Integer[]{}, (Predicate<Integer>) ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("if not one item matched in array")
            void ifNoOneItemMatchedInArray() {
                Integer[] array = {1, 3, 5, 7, 9};
                boolean result = noneMatch(array, n -> n % 2 == 0);
                assertTrue(result);
            }
        }
    }
}