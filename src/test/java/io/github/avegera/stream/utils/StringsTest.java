package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static io.github.avegera.stream.utils.Strings.joinToString;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringsTest {

    private static final String TEST_DELIMITER_1 = ",";
    private static final String TEST_DELIMITER_2 = "|";

    @Nested
    @DisplayName("joinToString(Collection<?>, String)")
    class JoinCollection {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable delimiter")
            void forNullableDelimiter() {
                List<String> list = mapObjects(10, Object::toString);
                assertThrows(NullPointerException.class, () -> joinToString(list, null));
            }
        }

        @Nested
        @DisplayName("returns empty string")
        class ReturnsEmptyString {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                String string = joinToString((Collection<Object>) null, TEST_DELIMITER_1);
                assertThat(string).isEmpty();
            }

            @Test
            @DisplayName("for empty list")
            void forEmptyList() {
                String string = joinToString(new ArrayList<>(), TEST_DELIMITER_1);
                assertThat(string).isEmpty();
            }

            @Test
            @DisplayName("for empty set")
            void forEmptySet() {
                String string = joinToString(new HashSet<>(), TEST_DELIMITER_1);
                assertThat(string).isEmpty();
            }
        }

        @Nested
        @DisplayName("returns joined elements")
        class ReturnsJoinedElements {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("of string collection")
            void ofStringCollection(int size) {
                List<String> list = getStrings(size);
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("of integer collection")
            void ofIntegerCollection(int size) {
                List<Integer> list = getIntegers(size);
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("of class collection")
            void ofClassCollection(int size) {
                List<Class<?>> list = mapObjects(size, Object::getClass);
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("of object collection")
            void ofObjectCollection(int size) {
                List<Object> list = mapObjects(size, i -> new Object());
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @ParameterizedTest(name = "delimiter = {0}")
            @ValueSource(strings = {"", " ", "/", "| ", "-", "|   |", "     ", "\t", "\n"})
            @DisplayName("with custom delimiter")
            void withCustomDelimiter(String delimiter) {
                List<Object> list = mapObjects(100, i -> new Object());
                String result = joinToString(list, delimiter);
                assertThat(result).isEqualTo(buildJoinedString(list, delimiter));
            }

            @Test
            @DisplayName("with duplicated elements")
            void withDuplicatedElements() {
                List<String> list = asList("a", "b", "a", "a", "b", "abc", "a");
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo("a,b,a,a,b,abc,a");
            }

            @Test
            @DisplayName("with nullable and empty elements")
            void withNullableAndEmptyElements() {
                List<String> list = asList("", "abc", null, "def", null, "");
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(",abc,null,def,null,");
            }

            @Test
            @DisplayName("with different element types and custom delimiter")
            void withDifferentElementTypesAndCustomDelimiter() {
                List<Object> list = asList("text", 42, true, (short) 3, 'c', 123456789L, 2.5, 4.9f, " test\n\t my test   ", (byte) 1);
                String result = joinToString(list, TEST_DELIMITER_2);
                assertThat(result).isEqualTo("text|42|true|3|c|123456789|2.5|4.9| test\n\t my test   |1");
            }
        }
    }

    @Nested
    @DisplayName("joinToString(Object[], String)")
    class JoinArray {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable delimiter")
            void forNullableDelimiter() {
                String[] array = {"a", "b", "c"};
                assertThrows(NullPointerException.class, () -> joinToString(array, null));
            }
        }

        @Nested
        @DisplayName("returns empty string")
        class ReturnsEmptyString {

            @Test
            @DisplayName("for nullable array")
            void forNullableArray() {
                String string = joinToString((Object[]) null, TEST_DELIMITER_1);
                assertThat(string).isEmpty();
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                String string = joinToString(new Object[]{}, TEST_DELIMITER_1);
                assertThat(string).isEmpty();
            }
        }

        @Nested
        @DisplayName("returns joined elements")
        class ReturnsJoinedElements {

            @Test
            @DisplayName("of string array")
            void ofStringArray() {
                String[] array = {"a", "b", "c", "d", "e"};
                String result = joinToString(array, TEST_DELIMITER_1);
                assertThat(result).isEqualTo("a,b,c,d,e");
            }

            @Test
            @DisplayName("of integer array")
            void ofIntegerArray() {
                Integer[] array = {1, 2, 3, 4, 5};
                String result = joinToString(array, TEST_DELIMITER_1);
                assertThat(result).isEqualTo("1,2,3,4,5");
            }

            @Test
            @DisplayName("of mixed type array")
            void ofMixedTypeArray() {
                Object[] array = {"text", 42, true, 3.14, 'x'};
                String result = joinToString(array, TEST_DELIMITER_1);
                assertThat(result).isEqualTo("text,42,true,3.14,x");
            }

            @ParameterizedTest(name = "delimiter = {0}")
            @ValueSource(strings = {"", " ", "/", "| ", "-", "|   |", "     ", "\t", "\n"})
            @DisplayName("with custom delimiter")
            void withCustomDelimiter(String delimiter) {
                Object[] array = {"a", "b", "c"};
                String result = joinToString(array, delimiter);
                assertThat(result).isEqualTo("a" + delimiter + "b" + delimiter + "c");
            }

            @Test
            @DisplayName("with duplicated elements")
            void withDuplicatedElements() {
                String[] array = {"a", "b", "a", "a", "b", "abc", "a"};
                String result = joinToString(array, TEST_DELIMITER_1);
                assertThat(result).isEqualTo("a,b,a,a,b,abc,a");
            }

            @Test
            @DisplayName("with nullable and empty elements")
            void withNullableAndEmptyElements() {
                String[] array = {"", "abc", null, "def", null, ""};
                String result = joinToString(array, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(",abc,null,def,null,");
            }

            @Test
            @DisplayName("with different element types and custom delimiter")
            void withDifferentElementTypesAndCustomDelimiter() {
                Object[] array = {"text", 42, true, (short) 3, 'c', 123456789L, 2.5, 4.9f, " test\n\t my test   ", (byte) 1};
                String result = joinToString(array, TEST_DELIMITER_2);
                assertThat(result).isEqualTo("text|42|true|3|c|123456789|2.5|4.9| test\n\t my test   |1");
            }
        }
    }

    private static <T> String buildJoinedString(List<T> list, String delimiter) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < list.size(); index++) {
            builder.append(list.get(index));

            if (index < list.size() - 1) {
                builder.append(delimiter);
            }
        }
        return builder.toString();
    }
}