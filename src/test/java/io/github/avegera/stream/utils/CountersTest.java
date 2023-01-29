package io.github.avegera.stream.utils;

import io.github.avegera.stream.utils.test.CollectionSizeProvider;
import io.github.avegera.stream.utils.test.domain.OrgUtils;
import io.github.avegera.stream.utils.test.domain.Organization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static io.github.avegera.stream.utils.Counters.count;
import static io.github.avegera.stream.utils.test.TestUtils.getList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountersTest {

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
            List<Organization> list = getList(size, OrgUtils::getOrganization);
            long count = count(list);
            assertEquals(size, count);
        }
    }
}