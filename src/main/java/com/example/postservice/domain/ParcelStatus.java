package com.example.postservice.domain;

import lombok.Getter;

@Getter
public enum ParcelStatus {
    RECEIVED("получена"),
    REFUSAL_TO_RECEIVED("отказ в получении"),
    ARRIVED_AT_THE_POST_OFFICE("прибыла в почтовое отделение"),
    ON_THE_WAY("в пути");

    private final String description;

    ParcelStatus(String description) {
        this.description = description;
    }

}
