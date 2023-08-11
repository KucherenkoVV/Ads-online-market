package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDTO;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    //Первоначально стоят загрушки в методах, потом будет изменено на ResponseEntity

    @Operation(
            summary = "Полуение всех объявлений", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AdDTO.class))})
                }
    )
    @GetMapping
    public AdDTO getAllAds() {
        System.out.println("Все объявления получены.");
        return new AdDTO();
    }

    @Operation(
            summary = "Добавление объявления", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Created",
                            content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AdDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content)
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AdDTO addAds(@RequestPart("image") MultipartFile multipartFile,
                        @RequestBody AdDTO adsDto) {
        System.out.println("Объявление добавлено");
        return new AdDTO();
    }

    @Operation(
            summary = "Получить информацию об объявлении", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AdDTO.class))})
            }
    )
    @GetMapping("/{id}")
    public AdDTO getFullAd(@PathVariable("id") Integer id) {
        System.out.println("Объявление получено по id");
        return new AdDTO();
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
    @DeleteMapping("/{id}")
    public void removeAds(@PathVariable("id") Integer id) {
        System.out.println("Объявление удалено");
    }

    @Operation(
            summary = "Обновление информации об объявлении",tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AdDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
        }
    )
    @PatchMapping("/{id}")
    public void updateAds(@PathVariable("id") Integer id,
                                            @RequestBody AdDTO adsDto) {

        System.out.println("Информация об объявлении обновлена.");
    }

    @Operation(
            summary = "Получение объявлений авторизованного пользователя", tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AdDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content)
            }
    )
    @GetMapping("/me")
    public List<AdDTO> getAdsMe() {
        System.out.println("Получены объявления авторизованного пользователя");
        return new ArrayList<AdDTO>();
    }

    @Operation(
            summary = "Обновление картинки объявления",tags = "Объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = { @Content(mediaType = "application/octet-stream")}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateAdsImage(@PathVariable("id") Integer id,
                                            @RequestPart("image") MultipartFile multipartFile) {
        System.out.println("Изменена картинка объявления");
    }
}
