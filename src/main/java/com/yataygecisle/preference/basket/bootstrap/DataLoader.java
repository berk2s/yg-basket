package com.yataygecisle.preference.basket.bootstrap;

import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Profile("local")
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
        if (basketItemRepository.findAll().size() == 0) {
            BasketItem basketItem = BasketItem.builder()
                    .collegeName("collegeName")
                    .facultyName("departmentName")
                    .courseName("courseName")
                    .collegeId(UUID.randomUUID())
                    .facultyId(UUID.randomUUID())
                    .courseId(UUID.randomUUID())
                    .build();

            basketItemRepository.save(basketItem);

            log.info("\n\n\n\n Created Basket Item Id: "
                    + basketItem.getId().toString()
                    + "\n\n\n\n");
        }
    }
}
