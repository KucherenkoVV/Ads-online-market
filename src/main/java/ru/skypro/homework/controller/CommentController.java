package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.dto.adsComment.CreateOrUpdateAdsCommentDto;
import ru.skypro.homework.dto.adsComment.ListAdsCommentsDto;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "Получение комментариев объявления", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @GetMapping("/{id}/comments")
    public ListAdsCommentsDto getComments(@PathVariable("id") Integer id) {

        return commentService.getAllComments(id);
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
                                       @RequestBody AdsCommentDto adsCommentDto, Authentication authentication) {

        return commentService.addCommentToAd(id, adsCommentDto, authentication);
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
        commentService.removeCommentFromAd(adId, commentId);
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
                                        @RequestBody CreateOrUpdateAdsCommentDto adsCommentDto) {
        return commentService.updateComment(adId, commentId, adsCommentDto);
    }

}
