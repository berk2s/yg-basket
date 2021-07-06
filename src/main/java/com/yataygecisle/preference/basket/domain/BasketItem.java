package com.yataygecisle.preference.basket.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BasketItem extends BaseEntity {

    @Column(name = "college_id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID collegeId;

    @Column(name = "faculty_id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID facultyId;

    @Column(name = "course_id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID courseId;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "faculty_name")
    private String facultyName;

    @Column(name = "course_name")
    private String courseName;

    @ManyToMany(mappedBy = "basketItems", fetch = FetchType.LAZY)
    private List<Basket> baskets = new ArrayList<>();
}
