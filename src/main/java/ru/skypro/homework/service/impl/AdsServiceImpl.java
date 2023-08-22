package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ListAdsDto;
import ru.skypro.homework.exception.AdsNotFoundException;
import ru.skypro.homework.exception.EmptyArgumentException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    // todo допилить логирование, сохранение ссылок на картинки
    private final UserService userService;
    private final ImageService imageService;
    private final AdsRepository adsRepository;
    private final AdsMapper adsMapper;

    @Override
    public List<AdDto> getAllAds() {
        return adsRepository.findAll().stream().
                map(adsMapper.INSTANCE::toAdDto)
                .collect(Collectors.toList());
    }

    @Override
    public ListAdsDto getAdsMe(Authentication authentication) {
        List<AdDto> list = adsRepository.findAllByAuthor_Username(authentication.getName());
        ListAdsDto listAdsDto = new ListAdsDto();
        if(!list.isEmpty()){
            listAdsDto.setCount(list.size());
            listAdsDto.setResults(list);
            return listAdsDto;
        } else {
            throw new AdsNotFoundException("Ads not found for this user.");
        }

    }

    @Override
    public AdDto addNewAd(MultipartFile file, AdDto adDto, Authentication authentication) {

        if (!adDto.getTitle().isBlank() && adDto.getPrice() != 0) {
            Ads ads = adsMapper.toAdsEntityFromAdDto(adDto);
            User user = userService.getUserByUsername(authentication.getName());
            ads.setAuthor(user);
            imageService.uploadImage(file);
            //todo доделать сохранение ссылки после создания эндпоинта
            adsRepository.save(ads);
            return adsMapper.toAdDto(ads);
        } else {
            throw new EmptyArgumentException("Information for new Ad is not enough");
        }

    }

    @Override
    public AdDto getAdFromId(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow();
        return adsMapper.toAdDto(ads);
    }

    @Override
    public void removeAdById(Integer id) {
        adsRepository.delete(adsRepository.findById(id).orElseThrow());
    }

    @Override
    public CreateOrUpdateAdDto updateAd(Integer id, CreateOrUpdateAdDto createOrUpdateAdDto) {
        if(!createOrUpdateAdDto.getTitle().isBlank() || !createOrUpdateAdDto.getDescription().isBlank() || createOrUpdateAdDto.getPrice() != 0) {
            Ads ads = adsRepository.findById(id).orElseThrow();
            if(createOrUpdateAdDto.getTitle() != null){
                ads.setTitle(createOrUpdateAdDto.getTitle());
            }
            if(createOrUpdateAdDto.getDescription() != null) {
                ads.setDescription(createOrUpdateAdDto.getDescription());
            }
            if(createOrUpdateAdDto.getPrice() != 0) {
                ads.setPrice(createOrUpdateAdDto.getPrice());
            }
            adsRepository.save(ads);
            return adsMapper.toCreatedOrUpdateAdDto(ads);
        } else {
            throw new EmptyArgumentException("Information for update is not enough");
        }
    }

    @Override
    public void updateAdImage(Integer id, MultipartFile file) {
        Ads ads = adsRepository.findById(id).orElseThrow();
        imageService.uploadImage(file);
        //todo доделать сохранение ссылки на картинку после создания эндпоинта
        ads.setImage("");
    }
}
