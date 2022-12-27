package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.StreamAssertions;
import io.github.avegera.stream.utils.test.TestUtils;
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
import java.util.stream.Stream;

import static io.github.avegera.stream.utils.test.StreamAssertions.assertIterableEquals;
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
                Stream<?> stream = SafeStreamUtils.safeStream(null);
                StreamAssertions.assertEmptyStream(stream);
            }

            @Test
            @DisplayName("for empty collection")
            void forEmptyCollection() {
                Stream<Object> stream = SafeStreamUtils.safeStream(new ArrayList<>());
                StreamAssertions.assertEmptyStream(stream);
            }
        }

        @ParameterizedTest(name = "size = {0}")
        @ArgumentsSource(CollectionSizeProvider.class)
        @DisplayName("equals collection of fixed size")
        void equalsCollectionOfFixedSize(int collectionSize) {
            Collection<Object> collection = TestUtils.getCollection(collectionSize);
            StreamAssertions.assertIterableEquals(collection, SafeStreamUtils.safeStream(collection));
        }
    }

    static class CollectionSizeProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(of(1), of(3), of(10), of(25), of(100));
        }
    }
}