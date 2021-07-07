package com.yataygecisle.preference.basket.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteBasketDto {

    private String ownerId;

    private String basketId;

}
