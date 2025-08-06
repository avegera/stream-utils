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

import static io.github.avegera.stream.utils.Lists.*;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertCollectionIsEmpty;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertEqualsIgnoringOrder;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Stream.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListsTest {

    @Nested
    @DisplayName("collect(Collection<T>)")
    class CollectCollection {

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = collect((Collection<Object>) null);
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

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns list of values")
        void returnsListOfValues(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
            List<Organization> result = collect(set);

            List<Organization> expected = getList(size, OrgUtils::getOrganization);
            assertEqualsIgnoringOrder(expected, result);
        }
    }

    @Nested
    @DisplayName("collect(T[])")
    class CollectArray {

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Object> result = collect((Object[]) null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Object> result = collect(new Object[]{});
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns list of values")
        void returnsListOfValues() {
            Integer[] array = {1, 2, 3, 4, 5};
            List<Integer> result = collect(array);

            List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("distinct(Collection<T>)")
    class DistinctCollection {

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = distinct((Collection<Object>) null);
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

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns list of distinct values")
        void returnsListOfDistinctValues(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            collection.addAll(getList(size, OrgUtils::getOrganization));
            List<Organization> result = distinct(collection);

            List<Organization> expected = getList(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("distinct(T[])")
    class DistinctArray {

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Object> result = distinct((Object[]) null);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Object> result = distinct(new Object[]{});
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns list of distinct values")
        void returnsListOfDistinctValues() {
            Integer[] array = {1, 2, 2, 3, 3, 4, 5};
            List<Integer> result = distinct(array);

            List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("filter(Collection<T>, Predicate<T>)")
    class FilterCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> filter(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = filter((Collection<Object>) null, it -> it.hashCode() < 0);
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

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns list of filtered objects")
        void returnsListOfFilteredObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            List<Organization> result = filter(collection, PredicateUtils.ORG_WITH_EVEN_ID);

            List<Organization> expected = getList(size, OrgUtils::getOrganization, PredicateUtils.INTEGER_IS_EVEN);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("filter(T[], Predicate<T>)")
    class FilterArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> filter(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Object> result = filter((Object[]) null, it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Object> result = filter(new Object[]{}, it -> it.hashCode() < 0);
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns list of filtered objects")
        void returnsListOfFilteredObjects() {
            Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            List<Integer> result = filter(array, n -> n % 2 == 0);

            List<Integer> expected = Arrays.asList(2, 4, 6, 8, 10);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("map(Collection<T>, Function<T, R>)")
    class MapCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> map(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = map((Collection<Object>) null, identity());
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

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns list of mapped objects")
        void returnsListOfMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganization);
            List<Integer> result = map(collection, Organization::getId);
            assertEquals(getNaturalNumbers(size), result);
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns same list for identity mapper")
        void returnsSameListForIdentityMapper(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            List<Organization> result = map(list, identity());
            assertEquals(list, result);
        }
    }

    @Nested
    @DisplayName("map(T[], Function<T, R>)")
    class MapArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable mapper")
            void forNullableMapper() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> map(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Object> result = map((Object[]) null, identity());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Object> result = map(new Object[]{}, identity());
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns list of mapped objects")
        void returnsListOfMappedObjects() {
            Integer[] array = {1, 2, 3, 4, 5};
            List<String> result = map(array, Object::toString);

            List<String> expected = Arrays.asList("1", "2", "3", "4", "5");
            assertEquals(expected, result);
        }

        @Test
        @DisplayName("returns same list for identity mapper")
        void returnsSameListForIdentityMapper() {
            Integer[] array = {1, 2, 3, 4, 5};
            List<Integer> result = map(array, identity());

            List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("flatMap(Collection<T>, Function<T, Stream<R>>)")
    class FlatMapCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                List<Organization> list = getList(10, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMap(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = flatMap((Collection<Object>) null, e -> empty());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = flatMap(new ArrayList<>(), e -> empty());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = flatMap(new HashSet<>(), e -> empty());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns list of flat mapped objects")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            List<Address> addresses = flatMap(collection, e -> e.getAddresses().stream());
            assertEquals(mergeListsForEachSize(size, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("flatMap(T[], Function<T, Stream<R>>)")
    class FlatMapArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                Organization[] array = getList(10, OrgUtils::getOrganizationWithAddresses).toArray(new Organization[0]);
                assertThrows(NullPointerException.class, () -> flatMap(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Object> result = flatMap((Object[]) null, e -> empty());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Object> result = flatMap(new Object[]{}, e -> empty());
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns list of flat mapped objects")
        void returnsListOfFlatMappedObjects() {
            Organization[] array = getList(5, OrgUtils::getOrganizationWithAddresses).toArray(new Organization[0]);
            List<Address> addresses = flatMap(array, e -> e.getAddresses().stream());
            assertEquals(mergeListsForEachSize(5, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("flatMapCollections(Collection<T>, Function<T, Collection<R>>)")
    class FlatMapCollections {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                List<Organization> list = getList(10, OrgUtils::getOrganizationWithAddresses);
                assertThrows(NullPointerException.class, () -> flatMapCollections(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = flatMapCollections((Collection<Object>) null, e -> emptyList());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                List<Object> result = flatMapCollections(new ArrayList<>(), e -> emptyList());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                List<Object> result = flatMapCollections(new HashSet<>(), e -> emptyList());
                assertCollectionIsEmpty(result);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns list of flat mapped objects")
        void returnsListOfFlatMappedObjects(int size) {
            Collection<Organization> collection = getList(size, OrgUtils::getOrganizationWithAddresses);
            List<Address> addresses = flatMapCollections(collection, Organization::getAddresses);
            assertEquals(mergeListsForEachSize(size, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("flatMapCollections(T[], Function<T, Collection<R>>)")
    class FlatMapCollectionsArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable flat mapper")
            void forNullableFlatMapper() {
                Organization[] array = getList(10, OrgUtils::getOrganizationWithAddresses).toArray(new Organization[0]);
                assertThrows(NullPointerException.class, () -> flatMapCollections(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Object> result = flatMapCollections((Object[]) null, e -> emptyList());
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Object> result = flatMapCollections(new Object[]{}, e -> emptyList());
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns list of flat mapped objects")
        void returnsListOfFlatMappedObjects() {
            Organization[] array = getList(5, OrgUtils::getOrganizationWithAddresses).toArray(new Organization[0]);
            List<Address> addresses = flatMapCollections(array, Organization::getAddresses);
            assertEquals(mergeListsForEachSize(5, OrgUtils::getAddress), addresses);
        }
    }

    @Nested
    @DisplayName("sort(Collection<T>, Comparator<T>)")
    class SortCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable comparator")
            void forNullableComparator() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> sort(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                List<Object> result = sort((Collection<Object>) null, comparingInt(Object::hashCode));
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

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns list of sorted values")
        void returnsListOfSortedValues(int size) {
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            List<Organization> result = sort(list, comparingInt(Organization::getId).reversed());

            List<Organization> expected = getReversedList(size, OrgUtils::getOrganization);
            assertEquals(expected, result);
        }
    }

    @Nested
    @DisplayName("sort(T[], Comparator<T>)")
    class SortArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable comparator")
            void forNullableComparator() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> sort(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty list")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                List<Object> result = sort((Object[]) null, comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                List<Object> result = sort(new Object[]{}, comparingInt(Object::hashCode));
                assertCollectionIsEmpty(result);
            }
        }

        @Test
        @DisplayName("returns list of sorted values")
        void returnsListOfSortedValues() {
            Integer[] array = {5, 3, 1, 4, 2};
            List<Integer> result = sort(array, Comparator.reverseOrder());

            List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);
            assertEquals(expected, result);
        }
    }
}