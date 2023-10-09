package com.yun.room.domain.common.embedded;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class Address {
    private String street;
    private String city;
    private String country;
    private String postalCode;
}
