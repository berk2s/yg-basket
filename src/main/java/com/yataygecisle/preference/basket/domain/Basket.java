package com.yataygecisle.preference.basket.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Basket extends BaseEntity {

    @Column(name = "basket_name")
    private String basketName;

    @Column(name = "owner_id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID ownerId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.REMOVE
    })
    @JoinTable(name = "basket_items",
            joinColumns = {
                @JoinColumn(name = "basket_id", referencedColumnName = "id")
            }, inverseJoinColumns = {
                @JoinColumn(name = "basket_item_id", referencedColumnName = "id")
            })
    private List<BasketItem> basketItems = new ArrayList<>();

    public void addBasketItem(BasketItem basketItem) {
        basketItem.getBaskets().add(this);
        basketItems.add(basketItem);
    }

    public void removeBasketItem(BasketItem basketItem) {
        basketItem.getBaskets().remove(basketItem);
        basketItems.remove(basketItem);
    }
}
