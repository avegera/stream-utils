package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static io.github.avegera.stream.utils.Strings.joinToString;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringsTest {

    private static final String TEST_DELIMITER_1 = ",";

    private static final String TEST_DELIMITER_2 = "|";

    @Nested
    @DisplayName("joinToString() method")
    class JoinToString {

        @Nested
        @DisplayName("throws null pointer exception")
        class ThrowsNullPointerException {

            @Test
            @DisplayName("for nullable delimiter")
            void forNullableMapper() {
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
                String string = joinToString(null, TEST_DELIMITER_1);
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
        @DisplayName("returns joined elements of")
        class ReturnsJoinedElementsOf {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("string collection")
            void stringCollection(int size) {
                List<String> list = getStrings(size);
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @ParameterizedTest(name = "delimeter = {0}")
            @ValueSource(strings = {"", " ", "/", "| ", "-", "|   |", "     ", "\t", "\n"})
            @DisplayName("string collection with custom delimiter")
            void stringCollectionWithCustomDelimiter(String delimiter) {
                List<Object> list = mapObjects(100, i -> new Object());
                String result = joinToString(list, delimiter);
                assertThat(result).isEqualTo(buildJoinedString(list, delimiter));
            }

            @Test
            @DisplayName("string collection with duplicated elements")
            void stringCollectionWithDuplicatedElements() {
                List<String> list = asList("a", "b", "a", "a", "b", "abc", "a");
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo("a,b,a,a,b,abc,a");
            }

            @Test
            @DisplayName("string collection with different element types")
            void stringCollectionWithNullableAndEmptyElements() {
                List<String> list = asList("", "abc", null, "def", null, "");
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(",abc,null,def,null,");
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("integer collection")
            void integerCollection(int size) {
                List<Integer> list = getIntegers(size);
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("class collection")
            void classCollection(int size) {
                List<Class<?>> list = mapObjects(size, Object::getClass);
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("object collection")
            void objectCollection(int size) {
                List<Object> list = mapObjects(size, i -> new Object());
                String result = joinToString(list, TEST_DELIMITER_1);
                assertThat(result).isEqualTo(buildJoinedString(list, TEST_DELIMITER_1));
            }

            @Test
            @DisplayName("collection with different element types and custom delimiter")
            void collectionWithDifferentElementTypesAndCustomDelimiter() {
                List<Object> list = asList("text", 42, true, (short) 3, 'c', 123456789L, 2.5, 4.9f, " test\n\t my test   ", (byte) 1);
                String result = joinToString(list, TEST_DELIMITER_2);
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