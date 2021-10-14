package com.github.avegera.stream.utils.test.domain;

import lombok.experimental.UtilityClass;

import java.util.List;

import static com.github.avegera.stream.utils.test.TestUtils.getList;

@UtilityClass
public class OrgUtils {

    public static Organization getOrganization(int i) {
        return getOrganization(i, null);
    }

    public static Organization getOrganizationWithAddresses(int i) {
        return getOrganization(i, getList(i, OrgUtils::getAddress));
    }

    private static Organization getOrganization(int i, List<Address> list) {
        return Organization.builder()
                .id(i)
                .name("orgName" + i)
                .addresses(list)
                .build();
    }

    public static Address getAddress(int i) {
        return Address.builder()
                .id(i)
                .name("addressName" + i)
                .build();
    }
}