package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import io.github.avegera.stream.utils.test.StreamAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.Streams.safeStream;
import static io.github.avegera.stream.utils.test.StreamAssertions.assertEmptyStream;
import static io.github.avegera.stream.utils.test.TestUtils.getCollection;

class StreamsTest {

    @Nested
    @DisplayName("Safe stream")
    class SafeStream {

        @Nested
        @DisplayName("is empty")
        class IsEmpty {

            @Test
            @DisplayName("for nullable collection")
            void forNullableCollection() {
                Stream<?> stream = safeStream(null);
                assertEmptyStream(stream);
            }

            @Test
            @DisplayName("for empty collection")
            void forEmptyCollection() {
                Stream<Object> stream = safeStream(new ArrayList<>());
                assertEmptyStream(stream);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("equals collection of fixed size")
        void equalsCollectionOfFixedSize(int collectionSize) {
            Collection<Object> collection = getCollection(collectionSize);
            StreamAssertions.assertIterableEquals(collection, safeStream(collection));
        }
    }
}