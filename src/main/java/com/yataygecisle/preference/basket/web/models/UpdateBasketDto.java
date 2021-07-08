package com.yataygecisle.preference.basket.web.models;


import com.yataygecisle.commons.annotations.UUID;
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

    @UUID
    @NotBlank
    private String ownerId;

    @NotNull
    private Set<AddBasketItemDto> newBasketItems = new HashSet<>();

    @NotNull
    private Set<AddBasketItemDto> removedBasketItems = new HashSet<>();

}
