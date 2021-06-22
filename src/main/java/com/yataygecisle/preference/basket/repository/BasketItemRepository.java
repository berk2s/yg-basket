package com.yataygecisle.preference.basket.repository;

import com.yataygecisle.preference.basket.domain.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketItemRepository extends JpaRepository<BasketItem, UUID> {
}
