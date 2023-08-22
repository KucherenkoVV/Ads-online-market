package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.dto.adsComment.CreateOrUpdateAdsCommentDto;

import java.util.List;


public interface CommentService {

    AdsCommentDto getCommentById(Integer id);

    List<AdsCommentDto> getAllComments (Integer id);
    AdsCommentDto addCommentToAd (Integer id, AdsCommentDto commentDto);

    void removeCommentFromAd (Integer id, Integer commentId);

    AdsCommentDto updateComment (Integer id, Integer commentId, CreateOrUpdateAdsCommentDto commentDto);

}
