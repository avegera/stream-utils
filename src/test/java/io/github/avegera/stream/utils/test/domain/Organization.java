package io.github.avegera.stream.utils.test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Organization {

    private final Integer id;

    private final String name;

    private final List<Address> addresses;
}