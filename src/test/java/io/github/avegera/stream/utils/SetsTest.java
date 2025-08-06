package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import io.github.avegera.stream.utils.test.PredicateUtils;
import io.github.avegera.stream.utils.test.domain.Address;
import io.github.avegera.stream.utils.test.domain.OrgUtils;
import io.github.avegera.stream.utils.test.domain.Organization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;

import static io.github.avegera.stream.utils.Sets.*;
import static io.github.avegera.stream.utils.Streams.safeStream;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertCollectionIsEmpty;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertIterableEquals;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetsTest {

    @Nested
    @DisplayName("Collect collection to set")
    class CollectCollection {

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = collectToSet((Collection<Object>) null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = collectToSet(new ArrayList<>());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = collectToSet(new HashSet<>());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns set of values")
        void returnsSetOfValues(int size) {
            List<Organization> collection = getList(size, OrgUtils::getOrganization);
            Set<Organization> result = collectToSet(collection);

            Set<Organization> expected = getSet(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Collect array to set")
    class CollectArray {

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Set<Object> result = collectToSet((Object[]) null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Set<Object> result = collectToSet(new Object[]{});
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns set of values")
        void returnsSetOfValues() {
            Integer[] array = {1, 2, 3, 4, 5};
            Set<Integer> result = collectToSet(array);
            Set<Integer> expected = new HashSet<>(asList(1, 2, 3, 4, 5));
            assertEquals(expected, result);
        }

        @Test
        @DisplayName("returns set with unique values")
        void returnsSetWithUniqueValues() {
            String[] array = {"a", "b", "a", "c", "b", "d"};
            Set<String> result = collectToSet(array);
            Set<String> expected = new HashSet<>(asList("a", "b", "c", "d"));
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Distinct collection to set")
    class DistinctCollection {

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = distinctToSet((Collection<Object>) null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = distinctToSet(new ArrayList<>());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = distinctToSet(new HashSet<>());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns set of distinct values")
        void returnsSetOfDistinctValues(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            collection.addAll(getList(size, OrgUtils::getOrganization));
            Set<Organization> result = distinctToSet(collection);

            Set<Organization> expected = getSet(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Distinct array to set")
    class DistinctArray {

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Set<Object> result = distinctToSet((Object[]) null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Set<Object> result = distinctToSet(new Object[]{});
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns set of distinct values")
        void returnsSetOfDistinctValues() {
            Integer[] array = {1, 2, 1, 3, 2, 4, 1};
            Set<Integer> result = distinctToSet(array);
            Set<Integer> expected = new HashSet<>(asList(1, 2, 3, 4));
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Filter collection to set")
    class FilterCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> filterToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = filterToSet((Collection<Object>) null, it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = filterToSet(new ArrayList<>(), it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = filterToSet(new HashSet<>(), it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns set of filtered objects")
        void returnsSetOfFilteredObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            Set<Organization> result = filterToSet(collection, PredicateUtils.ORG_WITH_EVEN_ID);

            Set<Organization> expected = getSet(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_EVEN);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Filter array to set")
    class FilterArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> filterToSet(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Set<Object> result = filterToSet((Object[]) null, it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Set<Object> result = filterToSet(new Object[]{}, it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns set of filtered objects")
        void returnsSetOfFilteredObjects() {
            Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            Set<Integer> result = filterToSet(array, n -> n % 2 == 0);
            Set<Integer> expected = new HashSet<>(asList(2, 4, 6, 8, 10));
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Map collection to set")
    class MapCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> mapToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = mapToSet((Collection<Object>) null, identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = mapToSet(new ArrayList<>(), identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = mapToSet(new HashSet<>(), identity());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns set of mapped objects")
        void returnsSetOfMappedObjects(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Set<Integer> result = mapToSet(list, Organization::getId);
            assertEquals(getNaturalNumbersSet(size), result);
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns same set for identity mapper")
        void returnsSameSetForIdentityMapper(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
            Set<Organization> result = mapToSet(set, identity());
            assertEquals(set, result);
        }
    }

    @Nested
    @DisplayName("Map array to set")
    class MapArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> mapToSet(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Set<Object> result = mapToSet((Object[]) null, identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Set<Object> result = mapToSet(new Object[]{}, identity());
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns set of mapped objects")
        void returnsSetOfMappedObjects() {
            Integer[] array = {1, 2, 3, 4, 5};
            Set<String> result = mapToSet(array, Object::toString);
            Set<String> expected = new HashSet<>(asList("1", "2", "3", "4", "5"));
            assertEquals(expected, result);
        }

        @Test
        @DisplayName("returns same set for identity mapper")
        void returnsSameSetForIdentityMapper() {
            Integer[] array = {1, 2, 3, 4, 5};
            Set<Integer> result = mapToSet(array, identity());
            Set<Integer> expected = new HashSet<>(asList(1, 2, 3, 4, 5));
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Flat map collection to set")
    class FlatMapCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                List<Organization> list = getList(10, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMapToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = flatMapToSet((Collection<Object>) null, e -> safeStream(emptyList()));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = flatMapToSet(new ArrayList<>(), e -> safeStream(emptyList()));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = flatMapToSet(new HashSet<>(), e -> safeStream(emptyList()));
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns set of flat mapped objects")
        void returnsSetOfFlatMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            Set<Address> addresses = flatMapToSet(collection, e -> safeStream(e.getAddresses()));
            assertEquals(mergeSetsForEachSize(size, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("Flat map array to set")
    class FlatMapArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                String[] array = {"a", "b", "c"};
                assertThrows(NullPointerException.class, () -> flatMapToSet(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Set<Object> result = flatMapToSet((Object[]) null, e -> safeStream(emptyList()));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Set<Object> result = flatMapToSet(new Object[]{}, e -> safeStream(emptyList()));
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns set of flat mapped objects")
        void returnsSetOfFlatMappedObjects() {
            String[] array = {"hello", "world"};
            Set<Character> result = flatMapToSet(array, s -> s.chars().mapToObj(c -> (char) c));
            Set<Character> expected = new HashSet<>(asList('h', 'e', 'l', 'o', 'w', 'r', 'd'));
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Flat map collections to set")
    class FlatMapCollections {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                List<Organization> list = getList(10, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMapCollectionsToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = flatMapCollectionsToSet((Collection<Object>) null, e -> emptyList());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = flatMapCollectionsToSet(new ArrayList<>(), e -> emptyList());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = flatMapCollectionsToSet(new HashSet<>(), e -> emptyList());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns set of flat mapped collections")
        void returnsSetOfFlatMappedCollections(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            Set<Address> result = flatMapCollectionsToSet(collection, Organization::getAddresses);
            assertEquals(mergeSetsForEachSize(size, OrgUtils::getAddress), result);
        }
    }

    @Nested
    @DisplayName("Flat map collections array to set")
    class FlatMapCollectionsArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                String[] array = {"a", "b", "c"};
                assertThrows(NullPointerException.class, () -> flatMapCollectionsToSet(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Set<Object> result = flatMapCollectionsToSet((Object[]) null, e -> emptyList());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Set<Object> result = flatMapCollectionsToSet(new Object[]{}, e -> emptyList());
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns set of flat mapped collections")
        void returnsSetOfFlatMappedCollections() {
            String[] array = {"hello", "world"};
            Set<String> result = flatMapCollectionsToSet(array, s -> asList(s.split("")));
            Set<String> expected = new HashSet<>(asList("h", "e", "l", "o", "w", "r", "d"));
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Sort collection to set")
    class SortCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable comparator")
            void forNullableComparator() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> sortToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = sortToSet((Collection<Object>) null, comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = sortToSet(new ArrayList<>(), comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = sortToSet(new HashSet<>(), comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns set of sorted values")
        void returnsSetOfSortedValues(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Set<Organization> result = sortToSet(list, comparingInt(Organization::getId).reversed());

            List<Organization> expected = getReversedList(size, OrgUtils::getOrganization);
            assertIterableEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Sort array to set")
    class SortArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable comparator")
            void forNullableComparator() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> sortToSet(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Set<Object> result = sortToSet((Object[]) null, comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Set<Object> result = sortToSet(new Object[]{}, comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns set of sorted values")
        void returnsSetOfSortedValues() {
            Integer[] array = {5, 2, 8, 1, 9, 3};
            Set<Integer> result = sortToSet(array, Comparator.reverseOrder());
            List<Integer> expected = asList(9, 8, 5, 3, 2, 1);
            assertIterableEquals(expected, result);
        }
    }
}