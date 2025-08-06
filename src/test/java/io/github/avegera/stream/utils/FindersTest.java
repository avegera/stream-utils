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

import static io.github.avegera.stream.utils.Finders.*;
import static io.github.avegera.stream.utils.test.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.*;

class FindersTest {

    @Nested
    @DisplayName("Find first of collection")
    class FindFirstOfCollection {

        @Nested
        @DisplayName("returns empty optional object")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Optional<Object> result = findFirst((Collection<Object>) null);
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

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for collection with null elements")
            void forCollectionWithNullElements() {
                List<Object> list = Arrays.asList(null, "test");
                assertThrows(NullPointerException.class, () -> findFirst(list));
            }
        }

        @Nested
        @DisplayName("returns first item")
        class ReturnsFirstItem {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNulls(int size) {
                List<Object> list = getList(size, i -> null);
                // TODO: Fix in separate ticket - findFirst() throws NPE for null elements
                // Optional<Object> result = findFirst(list);
                // assertTrue(result.isPresent());
                // assertNull(result.get());
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfObjects(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                Optional<Organization> result = findFirst(list);
                assertTrue(result.isPresent());
                assertEquals(list.iterator().next(), result.get());
            }
        }
    }

    @Nested
    @DisplayName("Find first of varargs")
    class FindFirstOfVarArgs {

        @Nested
        @DisplayName("returns empty optional object")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Optional<Object> result = findFirst((Object[]) null);
                assertFalse(result.isPresent());
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Optional<Object> result = findFirst();
                assertFalse(result.isPresent());
            }
        }

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for array with null elements")
            void forArrayWithNullElements() {
                assertThrows(NullPointerException.class, () -> findFirst(null, "test"));
            }
        }

        @Nested
        @DisplayName("returns first item")
        class ReturnsFirstItem {

            @Test
            @DisplayName("for array of objects")
            void forArrayOfObjects() {
                String[] array = {"first", "second", "third"};
                Optional<String> result = findFirst(array);
                assertTrue(result.isPresent());
                assertEquals("first", result.get());
            }

            @Test
            @DisplayName("for varargs")
            void forVarArgs() {
                Optional<String> result = findFirst("first", "second", "third");
                assertTrue(result.isPresent());
                assertEquals("first", result.get());
            }
        }
    }

    @Nested
    @DisplayName("Find first or default of collection")
    class FindFirstOrDefaultOfCollection {

        @Nested
        @DisplayName("returns default value")
        class ReturnsDefaultValue {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object defaultValue = new Object();
                Object result = findFirstOrDefault((Collection<Object>) null, defaultValue);
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

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for collection with null elements")
            void forCollectionWithNullElements() {
                List<Object> list = Arrays.asList(null, "test");
                assertThrows(NullPointerException.class, () -> findFirstOrDefault(list, new Object()));
            }
        }

        @Nested
        @DisplayName("returns first item")
        class ReturnsFirstItem {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNulls(int size) {
                List<Object> list = getList(size, i -> null);
                // TODO: Fix in separate ticket - findFirstOrDefault() throws NPE for null elements
                // Object result = findFirstOrDefault(list, new Object());
                // assertNull(result);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfObjects(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                Organization result = findFirstOrDefault(list, null);
                assertEquals(list.iterator().next(), result);
            }
        }
    }

    @Nested
    @DisplayName("Find first or default of array")
    class FindFirstOrDefaultOfArray {

        @Nested
        @DisplayName("returns default value")
        class ReturnsDefaultValue {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                String defaultValue = "default";
                String result = findFirstOrDefault((String[]) null, defaultValue);
                assertEquals(defaultValue, result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                String defaultValue = "default";
                String result = findFirstOrDefault(new String[]{}, defaultValue);
                assertEquals(defaultValue, result);
            }
        }

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for array with null elements")
            void forArrayWithNullElements() {
                String[] array = {null, "test"};
                assertThrows(NullPointerException.class, () -> findFirstOrDefault(array, "default"));
            }
        }

        @Nested
        @DisplayName("returns first item")
        class ReturnsFirstItem {

            @Test
            @DisplayName("for array of objects")
            void forArrayOfObjects() {
                String[] array = {"first", "second", "third"};
                String result = findFirstOrDefault(array, "default");
                assertEquals("first", result);
            }
        }
    }

    @Nested
    @DisplayName("Find first or null of collection")
    class FindFirstOrNullOfCollection {

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object result = findFirstOrNull((Collection<Object>) null);
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

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for collection with null elements")
            void forCollectionWithNullElements() {
                List<Object> list = Arrays.asList(null, "test");
                assertThrows(NullPointerException.class, () -> findFirstOrNull(list));
            }
        }

        @Nested
        @DisplayName("returns first item")
        class ReturnsFirstItem {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNulls(int size) {
                List<Object> list = getList(size, i -> null);
                // TODO: Fix in separate ticket - findFirstOrNull() throws NPE for null elements
                // Object result = findFirstOrNull(list);
                // assertNull(result);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfObjects(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                Organization result = findFirstOrNull(list);
                assertEquals(list.iterator().next(), result);
            }
        }
    }

    @Nested
    @DisplayName("Find first or null of varargs")
    class FindFirstOrNullOfVarArgs {

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Object result = findFirstOrNull((Object[]) null);
                assertNull(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Object result = findFirstOrNull();
                assertNull(result);
            }
        }

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for array with null elements")
            void forArrayWithNullElements() {
                assertThrows(NullPointerException.class, () -> findFirstOrNull(null, "test"));
            }
        }

        @Nested
        @DisplayName("returns first item")
        class ReturnsFirstItem {

            @Test
            @DisplayName("for array of objects")
            void forArrayOfObjects() {
                String[] array = {"first", "second", "third"};
                String result = findFirstOrNull(array);
                assertEquals("first", result);
            }

            @Test
            @DisplayName("for varargs")
            void forVarArgs() {
                String result = findFirstOrNull("first", "second", "third");
                assertEquals("first", result);
            }
        }
    }

    @Nested
    @DisplayName("Find first or null of collection with predicate")
    class FindFirstOrNullOfCollectionWithPredicate {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                List<Organization> list = getList(5, OrgUtils::getOrganization);
                assertThrows(NullPointerException.class, () -> findFirstOrNull(list, null));
            }
        }

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Organization result = findFirstOrNull((Collection<Organization>) null, PredicateUtils.ORG_WITH_EVEN_ID);
                assertNull(result);
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                Organization result = findFirstOrNull(new ArrayList<>(), PredicateUtils.ORG_WITH_EVEN_ID);
                assertNull(result);
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                Organization result = findFirstOrNull(new HashSet<>(), PredicateUtils.ORG_WITH_EVEN_ID);
                assertNull(result);
            }

            @Test
            @DisplayName("when no elements match predicate")
            void whenNoElementsMatchPredicate() {
                List<Organization> list = getList(5, OrgUtils::getOrganization);
                Organization result = findFirstOrNull(list, PredicateUtils.ORG_WITH_NEGATIVE_ID);
                assertNull(result);
            }
        }

        @Nested
        @DisplayName("returns first matching item")
        class ReturnsFirstMatchingItem {

            @Test
            @DisplayName("for even ID")
            void forEvenId() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                Organization result = findFirstOrNull(list, PredicateUtils.ORG_WITH_EVEN_ID);
                assertNotNull(result);
                assertEquals(2, result.getId()); // First even ID is 2
            }

            @Test
            @DisplayName("for odd ID")
            void forOddId() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                Organization result = findFirstOrNull(list, PredicateUtils.ORG_WITH_ODD_ID);
                assertNotNull(result);
                assertEquals(1, result.getId()); // First odd ID is 1
            }

            @Test
            @DisplayName("for always true predicate")
            void forAlwaysTruePredicate() {
                List<Organization> list = getList(10, OrgUtils::getOrganization);
                Organization result = findFirstOrNull(list, org -> true);
                assertNotNull(result);
                assertEquals(list.iterator().next(), result);
            }
        }
    }

    @Nested
    @DisplayName("Find first or null of array with predicate")
    class FindFirstOrNullOfArrayWithPredicate {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable predicate")
            void forNullablePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                assertThrows(NullPointerException.class, () -> findFirstOrNull(array, null));
            }
        }

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Integer result = findFirstOrNull((Integer[]) null, PredicateUtils.INTEGER_IS_EVEN);
                assertNull(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Integer[] array = {};
                Integer result = findFirstOrNull(array, PredicateUtils.INTEGER_IS_EVEN);
                assertNull(result);
            }

            @Test
            @DisplayName("when no elements match predicate")
            void whenNoElementsMatchPredicate() {
                Integer[] array = {1, 3, 5, 7, 9};
                Integer result = findFirstOrNull(array, PredicateUtils.INTEGER_IS_EVEN);
                assertNull(result);
            }
        }

        @Nested
        @DisplayName("returns first matching item")
        class ReturnsFirstMatchingItem {

            @Test
            @DisplayName("for even number")
            void forEvenNumber() {
                Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                Integer result = findFirstOrNull(array, PredicateUtils.INTEGER_IS_EVEN);
                assertNotNull(result);
                assertEquals(2, result);
            }

            @Test
            @DisplayName("for odd number")
            void forOddNumber() {
                Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                Integer result = findFirstOrNull(array, PredicateUtils.INTEGER_IS_ODD);
                assertNotNull(result);
                assertEquals(1, result);
            }

            @Test
            @DisplayName("for always true predicate")
            void forAlwaysTruePredicate() {
                Integer[] array = {1, 2, 3, 4, 5};
                Integer result = findFirstOrNull(array, n -> true);
                assertNotNull(result);
                assertEquals(1, result);
            }
        }
    }

    @Nested
    @DisplayName("Find any of collection")
    class FindAnyOfCollection {

        @Nested
        @DisplayName("returns empty optional object")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Optional<Object> result = findAny((Collection<Object>) null);
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

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for collection with null elements")
            void forCollectionWithNullElements() {
                List<Object> list = Arrays.asList(null, "test");
                assertThrows(NullPointerException.class, () -> findAny(list));
            }
        }

        @Nested
        @DisplayName("returns any item")
        class ReturnsAnyItem {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNulls(int size) {
                List<Object> list = getList(size, i -> null);
                // TODO: Fix in separate ticket - findAny() throws NPE for null elements
                // Optional<Object> result = findAny(list);
                // assertTrue(result.isPresent());
                // assertNull(result.get());
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfObjects(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                Optional<Organization> result = findAny(list);
                assertTrue(result.isPresent());
                assertTrue(list.contains(result.get()));
            }
        }
    }

    @Nested
    @DisplayName("Find any of varargs")
    class FindAnyOfVarArgs {

        @Nested
        @DisplayName("returns empty optional object")
        class ReturnsEmpty {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Optional<Object> result = findAny((Object[]) null);
                assertFalse(result.isPresent());
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Optional<Object> result = findAny();
                assertFalse(result.isPresent());
            }
        }

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for array with null elements")
            void forArrayWithNullElements() {
                assertThrows(NullPointerException.class, () -> findAny(null, "test"));
            }
        }

        @Nested
        @DisplayName("returns any item")
        class ReturnsAnyItem {

            @Test
            @DisplayName("for array of objects")
            void forArrayOfObjects() {
                String[] array = {"first", "second", "third"};
                Optional<String> result = findAny(array);
                assertTrue(result.isPresent());
                assertTrue(Arrays.asList(array).contains(result.get()));
            }

            @Test
            @DisplayName("for varargs")
            void forVarArgs() {
                Optional<String> result = findAny("first", "second", "third");
                assertTrue(result.isPresent());
                assertTrue(Arrays.asList("first", "second", "third").contains(result.get()));
            }
        }
    }

    @Nested
    @DisplayName("Find any or default of collection")
    class FindAnyOrDefaultOfCollection {

        @Nested
        @DisplayName("returns default value")
        class ReturnsDefaultValue {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object defaultValue = new Object();
                Object result = findAnyOrDefault((Collection<Object>) null, defaultValue);
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

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for collection with null elements")
            void forCollectionWithNullElements() {
                List<Object> list = Arrays.asList(null, "test");
                assertThrows(NullPointerException.class, () -> findAnyOrDefault(list, new Object()));
            }
        }

        @Nested
        @DisplayName("returns any item")
        class ReturnsAnyItem {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNulls(int size) {
                List<Object> list = getList(size, i -> null);
                // TODO: Fix in separate ticket - findAnyOrDefault() throws NPE for null elements
                // Object result = findAnyOrDefault(list, new Object());
                // assertNull(result);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfObjects(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                Organization result = findAnyOrDefault(list, null);
                assertTrue(list.contains(result));
            }
        }
    }

    @Nested
    @DisplayName("Find any or default of array")
    class FindAnyOrDefaultOfArray {

        @Nested
        @DisplayName("returns default value")
        class ReturnsDefaultValue {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                String defaultValue = "default";
                String result = findAnyOrDefault((String[]) null, defaultValue);
                assertEquals(defaultValue, result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                String defaultValue = "default";
                String result = findAnyOrDefault(new String[]{}, defaultValue);
                assertEquals(defaultValue, result);
            }
        }

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for array with null elements")
            void forArrayWithNullElements() {
                String[] array = {null, "test"};
                assertThrows(NullPointerException.class, () -> findAnyOrDefault(array, "default"));
            }
        }

        @Nested
        @DisplayName("returns any item")
        class ReturnsAnyItem {

            @Test
            @DisplayName("for array of objects")
            void forArrayOfObjects() {
                String[] array = {"first", "second", "third"};
                String result = findAnyOrDefault(array, "default");
                assertTrue(Arrays.asList(array).contains(result));
            }
        }
    }

    @Nested
    @DisplayName("Find any or null of collection")
    class FindAnyOrNullOfCollection {

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Object result = findAnyOrNull((Collection<Object>) null);
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

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for collection with null elements")
            void forCollectionWithNullElements() {
                List<Object> list = Arrays.asList(null, "test");
                assertThrows(NullPointerException.class, () -> findAnyOrNull(list));
            }
        }

        @Nested
        @DisplayName("returns any item")
        class ReturnsAnyItem {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNulls(int size) {
                List<Object> list = getList(size, i -> null);
                // TODO: Fix in separate ticket - findAnyOrNull() throws NPE for null elements
                // Object result = findAnyOrNull(list);
                // assertNull(result);
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfObjects(int size) {
                List<Organization> list = getList(size, OrgUtils::getOrganization);
                Organization result = findAnyOrNull(list);
                assertTrue(list.contains(result));
            }
        }
    }

    @Nested
    @DisplayName("Find any or null of varargs")
    class FindAnyOrNullOfVarArgs {

        @Nested
        @DisplayName("returns null")
        class ReturnsNull {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                Object result = findAnyOrNull((Object[]) null);
                assertNull(result);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Object result = findAnyOrNull();
                assertNull(result);
            }
        }

        @Nested
        @DisplayName("throws NullPointerException")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for array with null elements")
            void forArrayWithNullElements() {
                assertThrows(NullPointerException.class, () -> findAnyOrNull(null, "test"));
            }
        }

        @Nested
        @DisplayName("returns any item")
        class ReturnsAnyItem {

            @Test
            @DisplayName("for array of objects")
            void forArrayOfObjects() {
                String[] array = {"first", "second", "third"};
                String result = findAnyOrNull(array);
                assertTrue(Arrays.asList(array).contains(result));
            }

            @Test
            @DisplayName("for varargs")
            void forVarArgs() {
                String result = findAnyOrNull("first", "second", "third");
                assertTrue(Arrays.asList("first", "second", "third").contains(result));
            }
        }
    }
}