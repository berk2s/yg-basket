package com.yataygecisle.preference.basket.repository;

import com.yataygecisle.preference.basket.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {
}
