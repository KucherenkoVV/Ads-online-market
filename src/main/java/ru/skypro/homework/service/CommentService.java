package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.dto.adsComment.CreateOrUpdateAdsCommentDto;

import java.util.List;


public interface CommentService {

    AdsCommentDto getCommentById(Integer adsId, Integer commentId);

    List<AdsCommentDto> getAllComments (Integer id);
    AdsCommentDto addCommentToAd (Integer id, AdsCommentDto commentDto, Authentication authentication);

    void removeCommentFromAd (Integer adsId, Integer commentId);

    AdsCommentDto updateComment (Integer adsId, Integer commentId, CreateOrUpdateAdsCommentDto commentDto);

}
