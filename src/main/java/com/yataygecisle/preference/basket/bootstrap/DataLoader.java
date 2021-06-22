package com.yataygecisle.preference.basket.bootstrap;

import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;

    @Override
    public void run(String... args) throws Exception {

        loadBaskets();

    }

    private void loadBaskets() {
        BasketItem basketItem = BasketItem.builder()
                .collegeName("collegeName")
                .departmentName("departmentName")
                .collegeId(UUID.randomUUID())
                .departmentId(UUID.randomUUID())
                .build();

        basketItemRepository.save(basketItem);

        log.info("\n\n\n\n Created Basket Item Id: "
                + basketItem.getId().toString()
                + "\n\n\n\n");
    }
}
