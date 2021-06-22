package com.yataygecisle.preference.basket.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yataygecisle.preference.basket.web.models.AddBasketItemDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

public class BasketControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        itemLoader();
    }

    @DisplayName("User Creates Preference Basket Successfully")
    @Test
    void userCreatesPreferenceBasketSuccessfully() throws Exception {

        AddBasketItemDto addBasketItem1 = AddBasketItemDto.builder()
                .basketItemId(getItemId1().toString())
                .build();


        CreateBasketDto createBasketDto = CreateBasketDto.builder()
                .basketName("basketName")
                .basketItems(Set.of(addBasketItem1))
                .ownerId(UUID.randomUUID().toString())
                .build();

        mockMvc.perform(post(BasketController.ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createBasketDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.basketName", is(createBasketDto.getBasketName())))
                .andExpect(jsonPath("$.ownerId", is(createBasketDto.getOwnerId().toString())))
                .andExpect(jsonPath("$.basketItems.length()", is(1)))
                .andExpect(jsonPath("$.basketItems.[0].basketItemId", is(getItemId1().toString())))
                .andExpect(jsonPath("$.basketItems.[0].departmentId", is(getDepartmentId1().toString())))
                .andExpect(jsonPath("$.basketItems.[0].collegeId", is(getCollegeId1().toString())))
                .andExpect(jsonPath("$.basketItems.[0].departmentName", is("departmentName")))
                .andExpect(jsonPath("$.basketItems.[0].collegeName", is("collegeName")))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedAt").isNotEmpty());
    }
}
