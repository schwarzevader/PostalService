package com.example.postservice.domain;

import lombok.Getter;

@Getter
public enum MethodOfReceiving {

    PICKUP("Самовывоз"),
    COURIER("Курьер");

    private final String description;

    MethodOfReceiving(String description) {
        this.description = description;
    }

}
