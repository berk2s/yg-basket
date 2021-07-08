package com.yataygecisle.preference.basket.web.models;

import com.yataygecisle.commons.annotations.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketItemDto {

    @UUID
    private String basketItemId;

    @UUID
    private String basketId;

    @UUID
    private String collegeId;

    @UUID
    private String facultyId;

    @UUID
    private String courseId;

    private String collegeName;

    private String facultyName;

    private String courseName;

    private Timestamp createdAt;

    private Timestamp lastModifiedAt;

}
