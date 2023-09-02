package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.dto.ads.ListAdsDto;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Объявления", description = "AdsController")
public class AdsController {

    private final AdsServiceImpl adsService;

    @Autowired
    public AdsController(AdsServiceImpl adsService) {
        this.adsService = adsService;
    }

    @Operation(
            summary = "Получить все объявления", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdDto.class)))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<ListAdsDto> getAllAds() {

        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Operation(
            summary = "Получить информацию об объявлении", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExtendedAdDto.class))})
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getFullAd(@PathVariable Integer id) {

        return ResponseEntity.ok(adsService.getAdFromId(id));
    }

    @Operation(
            summary = "Добавить объявление", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @PreAuthorize("isAuthenticated")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
    public ResponseEntity<Ads> addAds(@RequestPart("image") MultipartFile image,
                                      @RequestPart("properties") AdDto ads,
                                      Authentication authentication) {
        return ResponseEntity.ok(adsService.addNewAd(image, ads, authentication));
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
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
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
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
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
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
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
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateAdsImage(@PathVariable("id") Integer id,
                               @RequestPart("image") MultipartFile multipartFile) {

        adsService.updateAdImage(id, multipartFile);
    }
}

