package com.yataygecisle.preference.basket.web.models;

import com.yataygecisle.commons.annotations.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketDto {

    @UUID
    private String basketId;

    @UUID
    private String ownerId;

    private String basketName;

    private List<BasketItemDto> basketItems = new ArrayList<>();

    private Timestamp createdAt;

    private Timestamp lastModifiedAt;

}
