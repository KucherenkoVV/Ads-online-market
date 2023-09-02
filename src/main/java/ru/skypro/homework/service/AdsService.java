package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.dto.ads.ListAdsDto;
import ru.skypro.homework.model.Ads;

import java.util.List;


public interface AdsService {

    ListAdsDto getAllAds();

    ListAdsDto getAdsMe(Authentication authentication);

    Ads addNewAd(MultipartFile file, AdDto adDto, Authentication authentication);

    ExtendedAdDto getAdFromId (Integer id);

    Ads getAdsFromId(Integer id);

    void removeAdById (Integer id);

    CreateOrUpdateAdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto);

    void updateAdImage (Integer id, MultipartFile file);


}
