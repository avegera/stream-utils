package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import io.github.avegera.stream.utils.test.PredicateUtils;
import io.github.avegera.stream.utils.test.domain.Address;
import io.github.avegera.stream.utils.test.domain.OrgUtils;
import io.github.avegera.stream.utils.test.domain.Organization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;

import static io.github.avegera.stream.utils.Lists.*;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertCollectionIsEmpty;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertEqualsIgnoringOrder;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListsTest {

    private static final int TEST_FIXED_SIZE = 10;

    @Nested
    @DisplayName("Map collection to list")
    class MapCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> map(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void emptyStreamForNullableCollection() {
                List<Object> result = map(null, identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = map(new ArrayList<>(), identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = map(new HashSet<>(), identity());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list of mapped objects for provided mapper")
        void returnsListOfMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            List<Integer> result = map(collection, Organization::getId);
            Assertions.assertEquals(getNaturalNumbers(size), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list with the provided objects for identity() mapper")
        void returnsTheSameListForIdentityMapper(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            List<Organization> result = map(list, identity());
            assertEquals(list, result);
        }
    }

    @Nested
    @DisplayName("Filter collection to list")
    class FilterCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> filter(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = filter(null, it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = filter(new ArrayList<>(), it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = filter(new HashSet<>(), it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list of filtered objects for provided predicate")
        void returnsListOfMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            List<Organization> result = filter(collection, PredicateUtils.ORG_WITH_EVEN_ID);

            List<Organization> expected = getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_EVEN);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Flat map collection to list")
    class FlatMapCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMap(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = flatMap(null, identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = flatMap(new ArrayList<>(), identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = flatMap(new HashSet<>(), identity());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list of mapped objects for provided flat mapper")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            List<Address> addresses = flatMap(collection, e -> Streams.safeStream(e.getAddresses()));
            Assertions.assertEquals(mergeListsForEachSize(size, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("Flat map collections to list")
    class FlatMapCollections {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMapCollections(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = flatMapCollections(null, identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = flatMapCollections(new ArrayList<>(), identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = flatMapCollections(new HashSet<>(), identity());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set of flat mapped objects")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            List<Address> addresses = flatMapCollections(collection, Organization::getAddresses);
            Assertions.assertEquals(mergeListsForEachSize(size, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("Distinct collection to list")
    class DistinctCollection {

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = distinct(null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = distinct(new ArrayList<>());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = distinct(new HashSet<>());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a list of distinct values")
        void returnsListOfDistinctValues(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            collection.addAll(getList(size, OrgUtils::getOrganization));
            List<Organization> result = distinct(collection);

            List<Organization> expected = getList(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Collect a collection to list")
    class CollectToList {

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = collect(null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = collect(new ArrayList<>());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = collect(new HashSet<>());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a list of values")
        void returnsListOfDistinctValues(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
            List<Organization> result = collect(set);

            List<Organization> expected = getList(size, OrgUtils::getOrganization);
            assertEqualsIgnoringOrder(expected, result);
        }
    }

    @Nested
    @DisplayName("Sort a collection to list")
    class SortToList {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable comparator")
            void forNullableComparator() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> sort(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = sort(null, comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = sort(new ArrayList<>(), comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = sort(new HashSet<>(), comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a list of sorted values")
        void returnsListOfDistinctValues(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            List<Organization> result = sort(list, comparingInt(Organization::getId).reversed());

            List<Organization> expected = getReversedList(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }
}