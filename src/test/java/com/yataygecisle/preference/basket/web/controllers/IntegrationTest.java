package com.yataygecisle.preference.basket.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yataygecisle.preference.basket.domain.Basket;
import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.repository.BasketItemRepository;
import com.yataygecisle.preference.basket.repository.BasketRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    BasketItemRepository basketItemRepository;

    @Autowired
    BasketRepository basketRepository;

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

    @Transactional
    public Map<String, Object> basketLoader(UUID ownerId) {
        BasketItem basketItem = new BasketItem();
        basketItem.setDepartmentName("departmentName");
        basketItem.setCollegeName("collegeName");
        basketItem.setCollegeId(UUID.randomUUID());
        basketItem.setDepartmentId(UUID.randomUUID());

        basketItemRepository.saveAll(Set.of(basketItem));

        Basket basket = new Basket();
        basket.setBasketName("basketName");
        basket.setOwnerId(ownerId);
        basket.addBasketItem(basketItem);

        basketRepository.save(basket);

        Map<String, Object> map = new HashMap<>();
        map.put("basketId", basket.getId());
        map.put("basketName", basket.getBasketName());
        map.put("basketItemId", basketItem.getId());
        map.put("collegeName", basketItem.getCollegeName());
        map.put("departmentName", basketItem.getDepartmentName());
        map.put("collegeId", basketItem.getCollegeId());
        map.put("departmentId", basketItem.getDepartmentId());

        return map;
    }

    public String createAccessToken() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/spring-rest/foos";
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("client_id", "clientWithSecret");
        map.add("client_secret", "clientSecret");
        map.add("grant_type", "password");
        map.add("username", "username");
        map.add("password", "password");
        map.add("scope", "openid");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);


        ResponseEntity<String> response
                = restTemplate.postForEntity("http://localhost:8080/token",
                request,
                String.class);

        JsonNode root = mapper.readTree(response.getBody());
        JsonNode token = root.path("access_token");
        return token.asText();
    }

}
