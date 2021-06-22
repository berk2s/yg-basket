package com.yataygecisle.preference.basket.web.controllers;

import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Getter
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    BasketItemRepository basketItemRepository;

    UUID itemId1;
    UUID collegeId1;
    UUID departmentId1;


    @Transactional
    public void itemLoader() {
        BasketItem basketItem1 = BasketItem.builder()
                .collegeId(UUID.randomUUID())
                .departmentId(UUID.randomUUID())
                .departmentName("departmentName")
                .collegeName("collegeName")
                .build();

        basketItemRepository.saveAll(Set.of(basketItem1));

        itemId1 = basketItem1.getId();
        collegeId1 = basketItem1.getCollegeId();
        departmentId1 = basketItem1.getDepartmentId();

    }

}
