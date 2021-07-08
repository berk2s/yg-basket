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
public class AddBasketItemDto {

    @UUID
    @NotNull
    private String basketItemId;

}
