package com.yataygecisle.preference.basket.web.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBasketDto {

    @NotBlank
    private String basketName;

    @NotBlank
    private String ownerId;

    @NotNull
    @NotEmpty
    @NotBlank
    private Set<AddBasketItemDto> newBasketItems = new HashSet<>();

    @NotNull
    @NotEmpty
    @NotBlank
    private Set<AddBasketItemDto> removedBasketItems = new HashSet<>();

}
