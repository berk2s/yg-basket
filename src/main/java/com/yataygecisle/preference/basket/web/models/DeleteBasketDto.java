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

    @UUID
    @NotNull
    private String ownerId;

    @UUID
    @NotNull
    private String basketId;

}
