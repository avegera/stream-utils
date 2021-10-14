package com.github.avegera.stream.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.avegera.stream.utils.SafeStreamUtils.safeStream;
import static com.github.avegera.stream.utils.test.StreamAssertions.assertEmptyStream;
import static com.github.avegera.stream.utils.test.StreamAssertions.assertIterableEquals;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.params.provider.Arguments.of;

@DisplayName("SafeStreamUtils Test")
class SafeStreamUtilsTest {

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
            assertIterableEquals(collection, safeStream(collection));
        }
    }

    static class CollectionSizeProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(of(1), of(3), of(10), of(25), of(100));
        }
    }

    public static Collection<Object> getCollection(int size) {
        return IntStream.range(1, size + 1)
                .boxed()
                .map((i) -> new Object())
                .collect(toList());
    }
}