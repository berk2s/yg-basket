package com.yataygecisle.preference.basket.domain;

import lombok.*;

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

    @Column(name = "department_id", unique = true)
    private UUID departmentId;

    @Column(name = "college_id", unique = true)
    private UUID collegeId;

    @Column(name = "college_name")
    private String collegeName;

    @Column(name = "department_name")
    private String departmentName;

    @ManyToMany(mappedBy = "basketItems", fetch = FetchType.LAZY)
    private List<Basket> baskets = new ArrayList<>();
}
