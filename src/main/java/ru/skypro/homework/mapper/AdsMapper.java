package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.model.Ads;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsMapper INSTANCE = Mappers.getMapper(AdsMapper.class);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.username")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "price", source = "price")
    ExtendedAdDto toExtendedAdDto(Ads ads);

    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "price", source = "price")
    CreateOrUpdateAdDto toCreatedOrUpdateAdDto (Ads ads);


    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "price", source = "price")
    AdDto toAdDto(Ads ads);


    Ads toAdsEntityFromCreateOrUpdateDto(CreateOrUpdateAdDto createOrUpdateAdDto);

    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "price", source = "price")
    Ads toAdsEntityFromAdDto(AdDto adDto);
}
