package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.model.Ads;

@Component
public class AdsMapper {

    public Ads toAdsFromDto (AdDto adDto) {
        Ads ads = new Ads();
        ads.setTitle(adDto.getTitle());
        ads.setDescription(adDto.getDescription());
        ads.setPrice(adDto.getPrice());
        return ads;
    }

    public AdDto toAdDtoFromEntity (Ads ads) {
        AdDto adDto = new AdDto();
        adDto.setPk(ads.getId());
        adDto.setTitle(ads.getTitle());
        adDto.setPrice(ads.getPrice());
        adDto.setDescription(ads.getDescription());
        adDto.setImage(ads.getImage());
        return adDto;
    }

    public CreateOrUpdateAdDto toCreateDtoFromEntity (Ads ads) {
        CreateOrUpdateAdDto createDto = new CreateOrUpdateAdDto();
        createDto.setTitle(ads.getTitle());
        createDto.setDescription(ads.getDescription());
        createDto.setPrice(ads.getPrice());
        return createDto;
    }

    public ExtendedAdDto toExtendedAdFromEntity (Ads ads) {
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        extendedAdDto.setPk(ads.getId());
        extendedAdDto.setAuthorFirstName(ads.getAuthor().getFirstName());
        extendedAdDto.setAuthorLastName(ads.getAuthor().getLastName());
        extendedAdDto.setDescription(ads.getDescription());
        extendedAdDto.setEmail(ads.getAuthor().getUsername());
        extendedAdDto.setPhone(ads.getAuthor().getPhone());
        extendedAdDto.setImage(ads.getImage());
        extendedAdDto.setTitle(ads.getTitle());
        extendedAdDto.setPrice(ads.getPrice());
        return extendedAdDto;
    }

    public Ads toEntityFromExtendedAd (ExtendedAdDto extend) {
        Ads ads = new Ads();
        ads.setTitle(extend.getTitle());
        ads.setDescription(extend.getDescription());
        ads.setTitle(extend.getTitle());
        ads.setPrice(extend.getPrice());
        return ads;
    }
}
