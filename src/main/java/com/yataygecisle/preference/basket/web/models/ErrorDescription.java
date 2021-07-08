package com.yataygecisle.preference.basket.web.models;

import lombok.Getter;

@Getter
public enum ErrorDescription {

    BASKET_ITEM_NOT_FOUND("Basket Item not found"),
    BASKET_NOT_FOUND("Basket not found");

    String errorDesc;

    ErrorDescription(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
