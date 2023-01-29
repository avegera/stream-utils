package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import io.github.avegera.stream.utils.test.PredicateUtils;
import io.github.avegera.stream.utils.test.StreamAssertions;
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

import static io.github.avegera.stream.utils.Sets.*;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetsTest {

    private static final int TEST_FIXED_SIZE = 10;

    @Nested
    @DisplayName("Map collection to set")
    class MapCollectionToSet {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> mapToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable list")
            void forNullableCollection() {
                Set<Object> result = mapToSet(null, identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void emptyStreamForEmptyCollection() {
                Set<Object> result = mapToSet(new ArrayList<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = mapToSet(new HashSet<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set of mapped objects for provided mapper")
        void returnsListOfMappedObjects(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Set<Integer> result = mapToSet(list, Organization::getId);
            Assertions.assertEquals(getNaturalNumbersSet(size), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set with the provided objects for identity() mapper")
        void returnsTheSameListForIdentityMapper(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
            Set<Organization> result = mapToSet(set, identity());
            assertEquals(set, result);
        }
    }

    @Nested
    @DisplayName("Filter collection to set")
    class FilterCollectionToSet {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> filterToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = filterToSet(null, it -> it.hashCode() < 0);
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = filterToSet(new ArrayList<>(), it -> it.hashCode() < 0);
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = filterToSet(new HashSet<>(), it -> it.hashCode() < 0);
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set of filtered objects for provided predicate")
        void returnsListOfMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            Set<Organization> result = filterToSet(collection, PredicateUtils.ORG_WITH_EVEN_ID);

            Set<Organization> expected = getSet(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_EVEN);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Flat map collection to set")
    class FlatMapCollectionToSet {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMapToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = flatMapCollectionsToSet(null, identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = flatMapToSet(new ArrayList<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = flatMapToSet(new HashSet<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set of mapped objects for provided flat mapper")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            Set<Address> addresses = flatMapToSet(collection, e -> Streams.safeStream(e.getAddresses()));
            Assertions.assertEquals(mergeSetsForEachSize(size, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("Flat map collections to set")
    class FlatMapCollectionsToSet {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableMapper() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMapCollectionsToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = flatMapCollectionsToSet(null, identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = flatMapCollectionsToSet(new ArrayList<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = flatMapCollectionsToSet(new HashSet<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set of flat mapped collections")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            Set<Address> result = flatMapCollectionsToSet(collection, Organization::getAddresses);
            Assertions.assertEquals(mergeSetsForEachSize(size, OrgUtils::getAddress), result);
        }
    }

    @Nested
    @DisplayName("Distinct collection to set")
    class DistinctCollectionToSet {

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = distinctToSet(null);
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = distinctToSet(new ArrayList<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = distinctToSet(new HashSet<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a set of distinct values")
        void returnsSetOfDistinctValues(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            collection.addAll(getList(size, OrgUtils::getOrganization));
            Set<Organization> result = distinctToSet(collection);

            Set<Organization> expected = getSet(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Collect a collection to set")
    class CollectToSet {

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = collectToSet(null);
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = collectToSet(new ArrayList<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = collectToSet(new HashSet<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a set of values")
        void returnsSetOfDistinctValues(int size) {
            List<Organization> collection = getList(size, OrgUtils::getOrganization);
            Set<Organization> result = collectToSet(collection);

            Set<Organization> expected = getSet(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Sort a collection to set")
    class SortToSet {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable comparator")
            void forNullableComparator() {
                List<Organization> list = getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> sortToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty set")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Set<Object> result = sortToSet(null, comparingInt(Object::hashCode));
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Set<Object> result = sortToSet(new ArrayList<>(), comparingInt(Object::hashCode));
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Set<Object> result = sortToSet(new HashSet<>(), comparingInt(Object::hashCode));
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a set of sorted values")
        void returnsListOfDistinctValues(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Set<Organization> result = sortToSet(list, comparingInt(Organization::getId).reversed());

            List<Organization> expected = getReversedList(size, OrgUtils::getOrganization);
            StreamAssertions.assertIterableEquals(expected, result);
        }
    }
}