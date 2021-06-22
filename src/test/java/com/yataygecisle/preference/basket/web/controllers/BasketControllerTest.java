package com.yataygecisle.preference.basket.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yataygecisle.preference.basket.web.models.AddBasketItemDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

public class BasketControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    String accessToken;
    String userId;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        itemLoader();
        accessToken = createAccessToken();
        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();

        String payload = new String(decoder.decode(chunks[1]));
        JsonNode root = objectMapper.readTree(payload);
        JsonNode sub = root.path("sub");

        userId = sub.asText();
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
                .ownerId(userId)
                .build();

        mockMvc.perform(post(BasketController.ENDPOINT)
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createBasketDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.basketName", is(createBasketDto.getBasketName())))
                .andExpect(jsonPath("$.ownerId", is(createBasketDto.getOwnerId())))
                .andExpect(jsonPath("$.basketItems.length()", is(1)))
                .andExpect(jsonPath("$.basketItems.[0].basketItemId", is(getItemId1().toString())))
                .andExpect(jsonPath("$.basketItems.[0].departmentId", is(getDepartmentId1().toString())))
                .andExpect(jsonPath("$.basketItems.[0].collegeId", is(getCollegeId1().toString())))
                .andExpect(jsonPath("$.basketItems.[0].departmentName", is("departmentName")))
                .andExpect(jsonPath("$.basketItems.[0].collegeName", is("collegeName")))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.lastModifiedAt").isNotEmpty());
    }

    @DisplayName("Get User's Baskets Successfully")
    @Test
    void getUsersBasketSuccessfully() throws Exception {

        Map<String, Object> map = basketLoader(UUID.fromString(userId));

        mockMvc.perform(get(BasketController.ENDPOINT + "/" + userId)
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].basketName", is(map.get("basketName"))))
                .andExpect(jsonPath("$.[0].ownerId", is(userId)))
                .andExpect(jsonPath("$.[0].basketItems.length()", is(1)))
                .andExpect(jsonPath("$.[0].basketItems.[0].basketItemId", is(map.get("basketItemId").toString())))
                .andExpect(jsonPath("$.[0].basketItems.[0].departmentId", is(map.get("departmentId").toString())))
                .andExpect(jsonPath("$.[0].basketItems.[0].collegeId", is(map.get("collegeId").toString())))
                .andExpect(jsonPath("$.[0].basketItems.[0].departmentName", is(map.get("departmentName"))))
                .andExpect(jsonPath("$.[0].basketItems.[0].collegeName", is(map.get("collegeName"))))
                .andExpect(jsonPath("$.[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.[0].lastModifiedAt").isNotEmpty());
    }

}
