package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

import static org.junit.jupiter.api.Assertions.*;

class AdsMapperTest {

    private AdsMapper adsMapper;
    private User user;
    private Ads ads;

    @BeforeEach
    void init () {
        adsMapper = new AdsMapper();
        user = new User();
        user.setFirstName("Name");
        user.setLastName("LastName");
        user.setUsername("Username");
        user.setPhone("+79990001122");

        ads = new Ads();
        ads.setId(1);
        ads.setTitle("title");
        ads.setPrice(1000);
        ads.setDescription("description");
        ads.setImage("image");
        ads.setAuthor(user);
    }

    @Test
    void shouldMapToAdsFromDto() {
        AdDto adDto = new AdDto();
        adDto.setTitle("title");
        adDto.setDescription("description");
        adDto.setImage("image");
        adDto.setPrice(1000);

        Ads ads = adsMapper.toAdsFromDto(adDto);
        assertEquals(adDto.getPrice(), ads.getPrice());
        assertEquals(adDto.getTitle(), ads.getTitle());
        assertEquals(adDto.getDescription(), ads.getDescription());
        assertEquals(adDto.getImage(), ads.getImage());
    }

    @Test
    void shouldMapToAdDtoFromEntity() {

        AdDto adDto = adsMapper.toAdDtoFromEntity(ads);

        assertEquals(ads.getId(), adDto.getPk());
        assertEquals(ads.getPrice(), adDto.getPrice());
        assertEquals(ads.getTitle(), adDto.getTitle());
        assertEquals(ads.getDescription(), adDto.getDescription());
        assertEquals(ads.getImage(), adDto.getImage());
    }

    @Test
    void shouldMapToCreateDtoFromEntity() {

        CreateOrUpdateAdDto createDto = adsMapper.toCreateDtoFromEntity(ads);

        assertEquals(ads.getTitle(), createDto.getTitle());
        assertEquals(ads.getDescription(), createDto.getDescription());
        assertEquals(ads.getPrice(), createDto.getPrice());
    }

    @Test
    void shouldMapToExtendedAdFromEntity() {

        ExtendedAdDto extendedAdDto = adsMapper.toExtendedAdFromEntity(ads);

        assertEquals(ads.getId(), extendedAdDto.getPk());
        assertEquals(ads.getAuthor().getFirstName(), extendedAdDto.getAuthorFirstName());
        assertEquals(ads.getAuthor().getLastName(), extendedAdDto.getAuthorLastName());
        assertEquals(ads.getDescription(), extendedAdDto.getDescription());
        assertEquals(ads.getAuthor().getUsername(), extendedAdDto.getEmail());
        assertEquals(ads.getAuthor().getPhone(), extendedAdDto.getPhone());
        assertEquals(ads.getImage(), extendedAdDto.getImage());
        assertEquals(ads.getTitle(), extendedAdDto.getTitle());
        assertEquals(ads.getPrice(), extendedAdDto.getPrice());
    }
}