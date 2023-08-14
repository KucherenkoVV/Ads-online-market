package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentController {

    //Первоначально стоят загрушки в методах, потом будет изменено на ResponseEntity

@Operation(
        summary = "Получение комментариев объявления", tags = "Комментарии",
        responses = {
                @ApiResponse(responseCode = "200", description = "OK"),
                @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
        }
)
    @GetMapping("/{id}/comments")
    public List<AdsCommentDto> getComments(@PathVariable("id") Integer id) {
    System.out.println("Выведен список объявлений");
        return new ArrayList<AdsCommentDto>();
    }

    @Operation(
            summary = "Добавление комментария к объявлению", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AdsCommentDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @PostMapping("/{id}/comments")
    public AdsCommentDto addAdsComment(@PathVariable("id") Integer id,
                                       @RequestBody AdsCommentDto adsCommentDto) {
        System.out.println("Комментарий добавлен");
        return new AdsCommentDto();
    }

    @Operation(
            summary = "Удаление комментария", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteAdsComment(@PathVariable("adId") Integer adId,
                                              @PathVariable("commentId") Integer commentId) {
        System.out.println("Комментарий удален");
    }

    @Operation(
            summary = "Обновление комментария", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = AdsCommentDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public AdsCommentDto updateComments(@PathVariable("adId") Integer adId,
                                        @PathVariable("commentId") Integer commentId,
                                        @RequestBody AdsCommentDto adsCommentDto) {
        System.out.println("Комментарий обновлен");
        return new AdsCommentDto();
    }

}
