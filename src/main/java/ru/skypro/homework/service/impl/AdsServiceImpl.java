package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.dto.ads.ListAdsDto;
import ru.skypro.homework.exception.EmptyArgumentException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@Service
public class AdsServiceImpl implements AdsService {

    private final UserService userService;
    private final ImageService imageService;
    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;

    @Autowired
    public AdsServiceImpl(UserService userService, ImageService imageService, AdsRepository adsRepository, AdsMapper adsMapper) {
        this.userService = userService;
        this.imageService = imageService;
        this.adsRepository = adsRepository;
        this.adsMapper = adsMapper;
    }

    @Override
    public ListAdsDto getAllAds() {
        log.info("Geting all ads.");

        List<AdDto> list = adsRepository.findAll().stream().
                map(adsMapper::toAdDtoFromEntity)
                .collect(Collectors.toList());

        ListAdsDto listAdsDto = new ListAdsDto();
        listAdsDto.setResults(list);
        listAdsDto.setCount(list.size());

        if (listAdsDto.getCount() != 0) {
            log.info("All ads get successful.");

            return listAdsDto;
        } else {
            throw new EmptyArgumentException("List ads is empty.");
        }
    }

    @Override
    public ListAdsDto getAdsMe(Authentication authentication) {
        log.info("Geting Ads for selected user.");

        List<AdDto> list = adsRepository.findAllByAuthorUsername(authentication.getName())
                .stream()
                .map(adsMapper::toAdDtoFromEntity)
                .collect(Collectors.toList());

        ListAdsDto listAdsDto = new ListAdsDto();
        listAdsDto.setCount(list.size());
        listAdsDto.setResults(list);

        log.info("Received all ads for selected user.");

        if (listAdsDto.getCount() != null) {
            return listAdsDto;
        } else {
            throw new EmptyArgumentException("List ads for selected user is empty.");
        }
    }

    @Override
    public Ads addNewAd(MultipartFile image, AdDto adDto, Authentication authentication) {
        log.info("Adding new ads.");

        Ads ads = adsMapper.toAdsFromDto(adDto);
        ads.setAuthor(userService.getUserByUsername(authentication.getName()));
        imageService.uploadImage(ads, image);

        log.info("New ads add success.");
        return adsRepository.save(ads);
    }

    @Override
    public ExtendedAdDto getAdFromId(Integer id) {
        log.info("Getting ad from id.");

        Ads ads = adsRepository.findById(id).orElseThrow();

        log.info("Ad from id received.");
        return adsMapper.toExtendedAdFromEntity(ads);
    }

    @Override
    public Ads getAdsFromId(Integer id) {
        log.info("Get Ads from id");

        Ads ads = adsRepository.findById(id).orElseThrow();

        log.info("Ads from id received.");
        return ads;
    }

    @Override
    public void removeAdById(Integer id) {
        log.info("Removing ad from id.");

        adsRepository.delete(adsRepository.findById(id).orElseThrow());

        log.info("Ad removed.");
    }

    @Override
    public CreateOrUpdateAdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        log.info("Updating Ad from id {} and createOrUpdateAdDto.", id);
        Ads ads = adsRepository.findById(id).orElseThrow();

        ads.setTitle(createOrUpdateAdDto.getTitle());
        ads.setDescription(createOrUpdateAdDto.getDescription());
        ads.setPrice(createOrUpdateAdDto.getPrice());

        adsRepository.save(ads);

        log.info("Ad updated.");
        return adsMapper.toCreateDtoFromEntity(ads);
    }

    @Override
    public void updateAdImage(Integer id, MultipartFile file) {
        log.info("Update uploaded ad image from image id {} and new file.", id);

        Ads ads = adsRepository.findById(id).orElseThrow();

        imageService.uploadImage(ads, file);

        log.info("Image for ad updated.");
    }
}
