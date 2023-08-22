package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ListAdsDto;
import ru.skypro.homework.dto.auth.Role;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static ru.skypro.homework.dto.auth.Role.USER;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    private final AdsServiceImpl adsService;

    public AdsController(AdsServiceImpl adsService) {
        this.adsService = adsService;
    }

    @Operation(
            summary = "Получение всех объявлений", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdDto.class))})
            }
    )
    @GetMapping
    public ResponseEntity<ListAdsDto> getAllAds() {

        List<AdDto> list = adsService.getAllAds();
        ListAdsDto listAdsDto = new ListAdsDto();
        if (list == null) {
            return ResponseEntity.notFound().build();
        } else {
            listAdsDto.setResults(list);
            listAdsDto.setCount(list.size());
            return ResponseEntity.ok(listAdsDto);
        }
    }

    @Operation(
            summary = "Добавление объявления", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAds(@RequestPart("image") MultipartFile multipartFile,
                                        @RequestBody AdDto adsDto,
                                        Authentication authentication) {

        return ResponseEntity.status(HttpStatus.CREATED).body(adsService.addNewAd(multipartFile, adsDto, authentication));
    }

    @Operation(
            summary = "Получить информацию об объявлении", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdDto.class))})
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AdDto> getFullAd(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(adsService.getAdFromId(id));
    }

    @Operation(
            summary = "Удаление объявления", tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable("id") Integer id) {

        adsService.removeAdById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновление информации об объявлении", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity<CreateOrUpdateAdDto> updateAds(@PathVariable("id") Integer id,
                                                         @RequestBody CreateOrUpdateAdDto createOrUpdateAdDto) {

        return ResponseEntity.ok(adsService.updateAd(id, createOrUpdateAdDto));
    }

    @Operation(
            summary = "Получение объявлений авторизованного пользователя", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ListAdsDto getAdsMe(Authentication authentication) {

        return adsService.getAdsMe(authentication);
    }

    @Operation(
            summary = "Обновление картинки объявления", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/octet-stream")}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateAdsImage(@PathVariable("id") Integer id,
                               @RequestPart("image") MultipartFile multipartFile) {

        adsService.updateAdImage(id, multipartFile);
    }
}
