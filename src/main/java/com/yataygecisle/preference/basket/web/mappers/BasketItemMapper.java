package com.yataygecisle.preference.basket.web.mappers;

import com.yataygecisle.preference.basket.domain.BasketItem;
import com.yataygecisle.preference.basket.web.models.AddBasketItemDto;
import com.yataygecisle.preference.basket.web.models.BasketItemDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, uses = {BasketMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BasketItemMapper {

    @Mappings({
            @Mapping(target = "collegeId", expression = "java( UUID.fromString(basketItemDto.getCollegeId()) )"),
            @Mapping(target = "departmentId", expression = "java( UUID.fromString(basketItemDto.getCollegeId()) )"),
    })
    BasketItem basketItemDtoToBasketItem(BasketItemDto basketItemDto);

    @Mappings({
            @Mapping(target = "id", expression = "java( UUID.fromString(addBasketItemDto.getBasketItemId()) )"),
    })
    BasketItem addBasketItemDtoToBasketItem(AddBasketItemDto addBasketItemDto);

    @Mappings({
            @Mapping(target = "basketItemId", expression = "java( basketItem.getId().toString() )"),
            @Mapping(target = "collegeId", expression = "java( basketItem.getCollegeId().toString() )"),
            @Mapping(target = "departmentId", expression = "java( basketItem.getDepartmentId().toString() )"),
    })
    BasketItemDto basketItemDtoToBasketItem(BasketItem basketItem);

    @Mappings({
            @Mapping(target = "basketItemId", expression = "java( basketItem.getId().toString() )"),
            @Mapping(target = "collegeId", expression = "java( basketItem.getCollegeId().toString() )"),
            @Mapping(target = "departmentId", expression = "java( basketItem.getDepartmentId().toString() )"),
    })
    List<BasketItemDto> basketItemDtoToBasketItem(List<BasketItem> basketItem);

}
