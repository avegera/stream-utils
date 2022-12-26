package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.domain.Address;
import io.github.avegera.stream.utils.test.domain.OrgUtils;
import io.github.avegera.stream.utils.test.domain.Organization;
import io.github.avegera.stream.utils.test.PredicateUtils;
import io.github.avegera.stream.utils.test.StreamAssertions;
import io.github.avegera.stream.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.StreamUtils.*;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertIterableEquals;
import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;

class StreamUtilsTest {

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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
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
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = map(new ArrayList<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = map(new HashSet<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list of mapped objects for provided mapper")
        void returnsListOfMappedObjects(int size) {
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganization);
            List<Integer> result = map(collection, Organization::getId);
            Assertions.assertEquals(TestUtils.getNaturalNumbers(size), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list with the provided objects for identity() mapper")
        void returnsTheSameListForIdentityMapper(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            List<Organization> result = map(list, identity());
            assertEquals(list, result);
        }
    }

    @Nested
    @DisplayName("Map collection to set")
    class MapCollectionToSet {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
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
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Set<Integer> result = mapToSet(list, Organization::getId);
            Assertions.assertEquals(TestUtils.getNaturalNumbersSet(size), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set with the provided objects for identity() mapper")
        void returnsTheSameListForIdentityMapper(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            Set<Organization> result = mapToSet(set, identity());
            assertEquals(set, result);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
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
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = filter(new ArrayList<>(), it -> it.hashCode() < 0);
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = filter(new HashSet<>(), it -> it.hashCode() < 0);
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list of filtered objects for provided predicate")
        void returnsListOfMappedObjects(int size) {
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganization);
            List<Organization> result = filter(collection, PredicateUtils.ORG_WITH_EVEN_ID);

            List<Organization> expected = TestUtils.getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_EVEN);
            assertEquals(expected, result);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
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
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganization);
            Set<Organization> result = filterToSet(collection, PredicateUtils.ORG_WITH_EVEN_ID);

            Set<Organization> expected = TestUtils.getSet(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_EVEN);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
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
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = flatMap(new ArrayList<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = flatMap(new HashSet<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the list of mapped objects for provided flat mapper")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganizationWithAddresses);
            List<Address> addresses = flatMap(collection, e -> SafeStreamUtils.safeStream(e.getAddresses()));
            Assertions.assertEquals(TestUtils.mergeListsForEachSize(size, OrgUtils::getAddress), addresses);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
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
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganizationWithAddresses);
            Set<Address> addresses = flatMapToSet(collection, e -> SafeStreamUtils.safeStream(e.getAddresses()));
            Assertions.assertEquals(TestUtils.mergeSetsForEachSize(size, OrgUtils::getAddress), addresses);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMapCollectionsToSet(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = flatMapCollections(null, identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = flatMapCollections(new ArrayList<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = flatMapCollections(new HashSet<>(), identity());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns the set of flat mapped objects")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganizationWithAddresses);
            List<Address> addresses = flatMapCollections(collection, Organization::getAddresses);
            Assertions.assertEquals(TestUtils.mergeListsForEachSize(size, OrgUtils::getAddress), addresses);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
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
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganizationWithAddresses);
            Set<Address> result = flatMapCollectionsToSet(collection, Organization::getAddresses);
            Assertions.assertEquals(TestUtils.mergeSetsForEachSize(size, OrgUtils::getAddress), result);
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
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = distinct(new ArrayList<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = distinct(new HashSet<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a list of distinct values")
        void returnsListOfDistinctValues(int size) {
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganization);
            collection.addAll(TestUtils.getList(size, OrgUtils::getOrganization));
            List<Organization> result = distinct(collection);

            List<Organization> expected = TestUtils.getList(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
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
            Collection<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganization);
            collection.addAll(TestUtils.getList(size, OrgUtils::getOrganization));
            Set<Organization> result = distinctToSet(collection);

            Set<Organization> expected = TestUtils.getSet(size, OrgUtils::getOrganization);
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
                List<Object> result = collectToList(null);
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = collectToList(new ArrayList<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = collectToList(new HashSet<>());
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a list of values")
        void returnsListOfDistinctValues(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            List<Organization> result = collectToList(set);

            List<Organization> expected = TestUtils.getList(size, OrgUtils::getOrganization);
            StreamAssertions.assertEqualsIgnoringOrder(expected, result);
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
            List<Organization> collection = TestUtils.getList(size, OrgUtils::getOrganization);
            Set<Organization> result = collectToSet(collection);

            Set<Organization> expected = TestUtils.getSet(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
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
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = sort(new ArrayList<>(), comparingInt(Object::hashCode));
                StreamAssertions.assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = sort(new HashSet<>(), comparingInt(Object::hashCode));
                StreamAssertions.assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns a list of sorted values")
        void returnsListOfDistinctValues(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            List<Organization> result = sort(list, comparingInt(Organization::getId).reversed());

            List<Organization> expected = TestUtils.getReversedList(size, OrgUtils::getOrganization);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganizationWithAddresses);
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
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Set<Organization> result = sortToSet(list, comparingInt(Organization::getId).reversed());

            List<Organization> expected = TestUtils.getReversedList(size, OrgUtils::getOrganization);
            StreamAssertions.assertIterableEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("Count of collection")
    class Count {

        @Nested
        @DisplayName("returns zero")
        class ReturnsZero {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                long count = count(null);
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

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns count of collection items")
        void returnsListOfDistinctValues(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            long count = count(list);
            assertEquals(size, count);
        }
    }

    @Nested
    @DisplayName("Find first")
    class FindFirst {

        @Nested
        @DisplayName("returns empty optional object")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Optional<Object> result = findFirst(null);
                assertFalse(result.isPresent());
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Optional<Object> result = findFirst(new ArrayList<>());
                assertFalse(result.isPresent());
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Optional<Object> result = findFirst(new HashSet<>());
                assertFalse(result.isPresent());
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from list")
        void returnsFirstItemFromList(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Optional<Organization> result = findFirst(list);
            assertTrue(result.isPresent());
            assertEquals(list.iterator().next(), result.get());
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            Optional<Organization> result = findFirst(set);
            assertTrue(result.isPresent());
            assertEquals(set.iterator().next(), result.get());
        }
    }

    @Nested
    @DisplayName("Find first or default")
    class FindFirstOrDefault {

        @Nested
        @DisplayName("returns default value")
        class ReturnsDefaultValue {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object defaultValue = new Object();
                Object result = findFirstOrDefault(null, defaultValue);
                assertEquals(defaultValue, result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Object defaultValue = new Object();
                Object result = findFirstOrDefault(new ArrayList<>(), defaultValue);
                assertEquals(defaultValue, result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Object defaultValue = new Object();
                Object result = findFirstOrDefault(new HashSet<>(), defaultValue);
                assertEquals(defaultValue, result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from list")
        void returnsFirstItemFromList(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Organization result = findFirstOrDefault(list, null);
            assertEquals(list.iterator().next(), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            Organization result = findFirstOrDefault(set, null);
            assertEquals(set.iterator().next(), result);
        }
    }

    @Nested
    @DisplayName("Find first or null")
    class FindFirstOrNull {

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object result = findFirstOrNull(null);
                assertNull(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Object result = findFirstOrNull(new ArrayList<>());
                assertNull(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Object result = findFirstOrNull(new HashSet<>());
                assertNull(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from list")
        void returnsFirstItemFromList(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Organization result = findFirstOrNull(list);
            assertEquals(list.iterator().next(), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            Organization result = findFirstOrNull(set);
            assertEquals(set.iterator().next(), result);
        }
    }

    @Nested
    @DisplayName("Find any")
    class FindAny {

        @Nested
        @DisplayName("returns empty optional object")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Optional<Object> result = findAny(null);
                assertFalse(result.isPresent());
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Optional<Object> result = findAny(new ArrayList<>());
                assertFalse(result.isPresent());
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Optional<Object> result = findAny(new HashSet<>());
                assertFalse(result.isPresent());
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from list")
        void returnsFirstItemFromList(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Optional<Organization> result = findAny(list);
            assertTrue(result.isPresent());
            assertTrue(list.contains(result.get()));
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            Optional<Organization> result = findAny(set);
            assertTrue(result.isPresent());
            assertTrue(set.contains(result.get()));
        }
    }

    @Nested
    @DisplayName("Find any or default")
    class FindAnyOrDefault {

        @Nested
        @DisplayName("returns default value")
        class ReturnsDefaultValue {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object defaultValue = new Object();
                Object result = findAnyOrDefault(null, defaultValue);
                assertEquals(defaultValue, result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Object defaultValue = new Object();
                Object result = findAnyOrDefault(new ArrayList<>(), defaultValue);
                assertEquals(defaultValue, result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Object defaultValue = new Object();
                Object result = findAnyOrDefault(new HashSet<>(), defaultValue);
                assertEquals(defaultValue, result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from list")
        void returnsFirstItemFromList(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Organization result = findAnyOrDefault(list, null);
            assertTrue(list.contains(result));
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            Organization result = findAnyOrDefault(set, null);
            assertTrue(set.contains(result));
        }
    }

    @Nested
    @DisplayName("Find any or null")
    class FindAnyOrNull {

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object result = findAnyOrNull(null);
                assertNull(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Object result = findAnyOrNull(new ArrayList<>());
                assertNull(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Object result = findAnyOrNull(new HashSet<>());
                assertNull(result);
            }
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from list")
        void returnsFirstItemFromList(int size) {
            List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
            Organization result = findAnyOrNull(list);
            assertTrue(list.contains(result));
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
            Organization result = findAnyOrNull(set);
            assertTrue(set.contains(result));
        }
    }

    @Nested
    @DisplayName("Any match")
    class AnyMatch {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> anyMatch(list, null));
            }
        }

        @Nested
        @DisplayName("returns false")
        class ReturnsFalse {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                boolean result = anyMatch(null, PredicateUtils.ALWAYS_TRUE);
                assertFalse(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                boolean result = anyMatch(new ArrayList<>(), PredicateUtils.ALWAYS_TRUE);
                assertFalse(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                boolean result = anyMatch(new HashSet<>(), PredicateUtils.ALWAYS_TRUE);
                assertFalse(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if no matches in list")
            void ifNoMatchesInList(int size) {
                List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_ODD);
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
                List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
                boolean result = anyMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from set")
            void ifMatchedAtLeastOneItemFromSet(int size) {
                Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
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
                List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_ODD);
                boolean result = allMatch(list, PredicateUtils.ORG_WITH_NEGATIVE_ID);
                assertFalse(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not all items matched in list")
            void ifNotAllItemsMatchedInList(int size) {
                List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization);
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
                boolean result = allMatch(null, PredicateUtils.ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                boolean result = allMatch(new ArrayList<>(), PredicateUtils.ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                boolean result = allMatch(new HashSet<>(), PredicateUtils.ALWAYS_TRUE);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched all items from list")
            void ifMatchedAtLeastOneItemFromList(int size) {
                List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_ODD);
                boolean result = allMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched all items from set")
            void ifMatchedAtLeastOneItemFromSet(int size) {
                Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_ODD);
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
                List<Organization> list = TestUtils.getList(TEST_FIXED_SIZE, OrgUtils::getOrganization);
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
                List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_ODD);
                boolean result = noneMatch(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertFalse(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if matched at least one item from set")
            void ifMatchedAtLeastOneItemFromSet(int size) {
                Set<Organization> set = TestUtils.getSet(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_ODD);
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
                boolean result = noneMatch(null, PredicateUtils.ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                boolean result = noneMatch(new ArrayList<>(), PredicateUtils.ALWAYS_TRUE);
                assertTrue(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                boolean result = noneMatch(new HashSet<>(), PredicateUtils.ALWAYS_TRUE);
                assertTrue(result);
            }

            @ParameterizedTest(name = "for size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("if not one item matched in list")
            void ifNoOneItemMatchedInList(int size) {
                List<Organization> list = TestUtils.getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_ODD);
                boolean result = noneMatch(list, PredicateUtils.ORG_WITH_NEGATIVE_ID);
                assertTrue(result);
            }
        }
    }

    static class CollectionSizeProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(of(1), of(3), of(10), of(25), of(100));
        }
    }
}