package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.Streams.toStream;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertEmptyStream;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertIterableEquals;
import static io.github.avegera.stream.utils.test.TestUtils.*;
import static java.util.Arrays.asList;

class StreamsTest {

    @Nested
    @DisplayName("toStream(Collection<T>)")
    class SafeStreamOfCollection {

        @Nested
        @DisplayName("returns empty stream")
        class ReturnsEmptyStream {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Stream<?> stream = toStream((Collection<Object>) null);
                assertEmptyStream(stream);
            }

            @Test
            @DisplayName("for empty collection")
            void forEmptyCollection() {
                Stream<Object> stream = toStream(new ArrayList<>());
                assertEmptyStream(stream);
            }
        }

        @Nested
        @DisplayName("returns stream with same elements")
        class ReturnsStreamWithSameElements {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of nulls")
            void forCollectionOfNullsOfSize(int collectionSize) {
                Collection<Object> collection = getCollectionOfNulls(collectionSize);
                assertIterableEquals(collection, toStream(collection));
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for collection of objects")
            void forCollectionOfSize(int collectionSize) {
                Collection<Object> collection = getCollection(collectionSize);
                assertIterableEquals(collection, toStream(collection));
            }
        }
    }

    @Nested
    @DisplayName("toStream(T...)")
    class SafeStreamOfVarArgs {

        @Nested
        @DisplayName("returns empty stream")
        class ReturnsEmptyStream {

            @Test
            @DisplayName("for nullable array")
            void forNullArray() {
                Stream<?> stream = toStream((Object[]) null);
                assertEmptyStream(stream);
            }

            @Test
            @DisplayName("for empty array")
            void forEmptyArray() {
                Stream<Object> stream = toStream();
                assertEmptyStream(stream);
            }
        }

        @Nested
        @DisplayName("returns stream with same elements")
        class ReturnsStreamWithSameElements {

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for array of nulls")
            void forArrayWithNullElementsOfSize(int arraySize) {
                Object[] array = getArrayOfNulls(arraySize);
                assertIterableEquals(asList(array), toStream(array));
            }

            @ParameterizedTest(name = "size = {0}")
            @ArgumentsSource(CollectionSizeProvider.class)
            @DisplayName("for array of objects")
            void forArrayOfSize(int arraySize) {
                Object[] array = getArray(arraySize);
                assertIterableEquals(asList(array), toStream(array));
            }
        }
    }
}