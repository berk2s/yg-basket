package com.yataygecisle.preference.basket.repository;

import com.yataygecisle.preference.basket.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID> {

    List<Basket> findByOwnerId(UUID ownerId);

    Optional<Basket> findByIdAndOwnerId(UUID id, UUID ownerId);

    boolean existsByIdAndOwnerId(UUID id, UUID ownerId);

    void deleteByOwnerId(UUID ownerId);

}
