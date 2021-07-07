package com.yataygecisle.preference.basket.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yataygecisle.preference.basket.web.models.AddBasketItemDto;
import com.yataygecisle.preference.basket.web.models.CreateBasketDto;
import com.yataygecisle.preference.basket.web.models.UpdateBasketDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BasketControllerTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    String accessToken;

    Map<String, Object> map;

    String userId;


    @Nested
    class TestEndpoints {

        @BeforeEach
        void setUp() throws JsonProcessingException {
            accessToken = createAccessToken();
            String[] chunks = accessToken.split("\\.");
            Base64.Decoder decoder = Base64.getDecoder();

            String payload = new String(decoder.decode(chunks[1]));
            JsonNode root = objectMapper.readTree(payload);
            JsonNode sub = root.path("sub");

            userId = sub.asText();

            map = basketLoader(UUID.fromString(userId));
        }

        @DisplayName("User Creates Preference Basket Successfully")
        @Test
        void userCreatesPreferenceBasketSuccessfully() throws Exception {

            AddBasketItemDto addBasketItem1 = AddBasketItemDto.builder()
                    .basketItemId(map.get("basketItemId").toString())
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
                    .andExpect(jsonPath("$.basketItems.[0].basketItemId", is(map.get("basketItemId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].collegeId", is(map.get("collegeId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].facultyId", is(map.get("facultyId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].courseId", is(map.get("courseId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].collegeName", is(map.get("collegeName"))))
                    .andExpect(jsonPath("$.basketItems.[0].facultyName", is(map.get("facultyName"))))
                    .andExpect(jsonPath("$.basketItems.[0].courseName", is(map.get("courseName"))))
                    .andExpect(jsonPath("$.createdAt").isNotEmpty())
                    .andExpect(jsonPath("$.lastModifiedAt").isNotEmpty());
        }


        @DisplayName("Get Basket By Basket Id Successfully")
        @Test
        void getBasketByBasketIdSuccessfully() throws Exception {

            mockMvc.perform(get(BasketController.ENDPOINT + "/" + map.get("basketId"))
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.basketId", is(map.get("basketId").toString())))
                    .andExpect(jsonPath("$.basketName", is(map.get("basketName"))))
                    .andExpect(jsonPath("$.ownerId", is(userId)))
                    .andExpect(jsonPath("$.basketItems.length()", is(1)))
                    .andExpect(jsonPath("$.basketItems.[0].basketItemId", is(map.get("basketItemId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].collegeId", is(map.get("collegeId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].facultyId", is(map.get("facultyId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].courseId", is(map.get("courseId").toString())))
                    .andExpect(jsonPath("$.basketItems.[0].collegeName", is(map.get("collegeName"))))
                    .andExpect(jsonPath("$.basketItems.[0].facultyName", is(map.get("facultyName"))))
                    .andExpect(jsonPath("$.basketItems.[0].courseName", is(map.get("courseName"))))
                    .andExpect(jsonPath("$.createdAt").isNotEmpty())
                    .andExpect(jsonPath("$.lastModifiedAt").isNotEmpty());

        }

        @DisplayName("Delete Basket Successfully")
        @Test
        void deleteBasketSuccessfully() throws Exception {

            mockMvc.perform(delete(BasketController.ENDPOINT + "/" + map.get("ownerId").toString() + "/" + map.get("basketId").toString())
                    .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().isNoContent());

        }

        @DisplayName("Update Basket Successfully")
        @Test
        void updateBasketSuccessfully() throws Exception {

            UpdateBasketDto updateBasket = UpdateBasketDto.builder()
                    .basketName("newBasketName")
                    .ownerId(userId)
                    .removedBasketItems(Set.of(AddBasketItemDto.builder().basketItemId(map.get("basketItemId").toString()).build()))
                    .newBasketItems(Set.of())
                    .build();

            mockMvc.perform(put(BasketController.ENDPOINT + "/" + map.get("basketId").toString())
                    .content(objectMapper.writeValueAsString(updateBasket))
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(BasketController.ENDPOINT + "/" + map.get("basketId").toString()));
        }


        @DisplayName("Get User's Baskets Successfully")
        @Test
        void getUsersBasketSuccessfully() throws Exception {
            Map<String, Object> map = basketLoader(UUID.fromString(userId));

            mockMvc.perform(get(BasketController.ENDPOINT + "/owner/" + userId)
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$..basketName", everyItem(anyOf(is(map.get("basketName"))))))
                    .andExpect(jsonPath("$..ownerId", everyItem(anyOf(is(userId)))))
                    .andExpect(jsonPath("$..basketItems[*].basketItemId", anyOf(hasItem(map.get("basketItemId").toString()))))
                    .andExpect(jsonPath("$..basketItems[*].collegeId", anyOf(hasItem(map.get("collegeId").toString()))))
                    .andExpect(jsonPath("$..basketItems[*].facultyId", anyOf(hasItem(map.get("facultyId").toString()))))
                    .andExpect(jsonPath("$..basketItems[*].courseId", anyOf(hasItem(map.get("courseId").toString()))))
                    .andExpect(jsonPath("$..basketItems[*].collegeName", anyOf(hasItem(map.get("collegeName")))))
                    .andExpect(jsonPath("$..basketItems[*].facultyName", anyOf(hasItem(map.get("facultyName")))))
                    .andExpect(jsonPath("$..basketItems[*].courseName", anyOf(hasItem(map.get("courseName")))))
                    .andExpect(jsonPath("$..createdAt").isNotEmpty())
                    .andExpect(jsonPath("$..lastModifiedAt").isNotEmpty());
        }

    }
}
