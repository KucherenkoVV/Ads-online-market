package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.dto.adsComment.CreateOrUpdateAdsCommentDto;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public AdsCommentDto getCommentById(Integer id) {
        return null;
    }

    @Override
    public List<AdsCommentDto> getAllComments(Integer id) {
        return null;
    }

    @Override
    public AdsCommentDto addCommentToAd(Integer id, AdsCommentDto commentDto) {
        return null;
    }

    @Override
    public void removeCommentFromAd(Integer id, Integer commentId) {

    }

    @Override
    public AdsCommentDto updateComment(Integer id, Integer commentId, CreateOrUpdateAdsCommentDto commentDto) {
        return null;
    }
}
