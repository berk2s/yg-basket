package com.yataygecisle.preference.basket.web.models;

import com.yataygecisle.commons.annotations.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteBasketDto {

    @UUID(message = "Owner ID must be UUID")
    @NotNull(message = "Owner ID must not be null")
    private String ownerId;

    @UUID(message = "Basket ID must be UUID")
    @NotNull(message = "Basket ID must not be null")
    private String basketId;

}
