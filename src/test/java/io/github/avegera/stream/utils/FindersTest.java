package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
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
import static io.github.avegera.stream.utils.test.TestUtils.getSet;
import static org.junit.jupiter.api.Assertions.*;

class FindersTest {

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
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Optional<Organization> result = findFirst(list);
            assertTrue(result.isPresent());
            assertEquals(list.iterator().next(), result.get());
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
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
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Organization result = findFirstOrDefault(list, null);
            assertEquals(list.iterator().next(), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
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
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Organization result = findFirstOrNull(list);
            assertEquals(list.iterator().next(), result);
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns first item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
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
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Optional<Organization> result = findAny(list);
            assertTrue(result.isPresent());
            assertTrue(list.contains(result.get()));
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
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
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Organization result = findAnyOrDefault(list, null);
            assertTrue(list.contains(result));
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
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
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            Organization result = findAnyOrNull(list);
            assertTrue(list.contains(result));
        }

        @ParameterizedTest(name = "for size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("returns any item from set")
        void returnsFirstItemFromSet(int size) {
            Set<Organization> set = getSet(size, OrgUtils::getOrganization);
            Organization result = findAnyOrNull(set);
            assertTrue(set.contains(result));
        }
    }
}