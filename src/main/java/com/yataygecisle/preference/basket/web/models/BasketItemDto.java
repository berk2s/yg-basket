package com.yataygecisle.preference.basket.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketItemDto {

    private String basketItemId;

    private String basketId;

    private String collegeId;

    private String departmentId;

    private String collegeName;

    private String departmentName;

}
