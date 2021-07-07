package com.yataygecisle.preference.basket.web.models;

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

    private String basketItemId;

    private String basketId;

    private String collegeId;

    private String facultyId;

    private String courseId;

    private String collegeName;

    private String facultyName;

    private String courseName;


    private Timestamp createdAt;

    private Timestamp lastModifiedAt;

}
