package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.dto.adsComment.CreateOrUpdateAdsCommentDto;
import ru.skypro.homework.dto.adsComment.ListAdsCommentsDto;
import ru.skypro.homework.mapper.AdsCommentMapper;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.service.CommentService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final AdsServiceImpl adsService;
    private final AdsMapper adsMapper;
    private final AdsCommentMapper adsCommentMapper;
    private final CommentsRepository commentsRepository;
    private final UserServiceImpl userService;

    public CommentServiceImpl(AdsServiceImpl adsService, AdsMapper adsMapper, AdsCommentMapper adsCommentMapper,
                              CommentsRepository commentsRepository, UserServiceImpl userService) {
        this.adsService = adsService;
        this.adsMapper = adsMapper;
        this.adsCommentMapper = adsCommentMapper;
        this.commentsRepository = commentsRepository;
        this.userService = userService;
    }

    @Override
    public AdsCommentDto getCommentById(Integer adsId, Integer commentId) {
        log.info("Get comment by ads id {}, commentId {}.", adsId, commentId);

        Comment comment = commentsRepository.findCommentByAdsIdAndId(adsId, commentId);

        log.info("Comment with ads id {} and commentId {} received.", adsId, commentId);
        return adsCommentMapper.toCommentDtoFromEntity(comment);
    }

    @Override
    public Comment getCommentEntityById(Integer adsId, Integer commentId) {
        log.info("Get comment by ads id {}, commentId {}.", adsId, commentId);

        Comment comment = commentsRepository.findCommentByAdsIdAndId(adsId, commentId);

        log.info("Comment with ads id {} and commentId {} received.", adsId, commentId);
        return comment;
    }

    @Override
    public ListAdsCommentsDto getAllComments(Integer id) {
        log.info("Get all ads comments by ads id: {}.", id);

        List<AdsCommentDto> adsCommentDtoList = commentsRepository.findAllByAdsId(id)
                .stream()
                .map(adsCommentMapper::toCommentDtoFromEntity)
                .collect(Collectors.toList());
        ListAdsCommentsDto listAdsCommentsDto = new ListAdsCommentsDto();
        listAdsCommentsDto.setResults(adsCommentDtoList);
        listAdsCommentsDto.setCount(adsCommentDtoList.size());

        log.info("All ads received by ads id: {} .", id);
        return listAdsCommentsDto;
    }

    @Override
    public AdsCommentDto addCommentToAd(Integer id, AdsCommentDto commentDto, Authentication authentication) {
        log.info("Add comment to ads by id and new comment: {}.", id);

        Comment comment = adsCommentMapper.toEntityFromCommentDto(commentDto);
        User user = userService.getUserByUsername(authentication.getName());
        comment.setAuthor(user);
        Ads ads = adsService.getAdsFromId(id);
        comment.setAds(ads);
        comment.setCreatedAt(Instant.now());
        comment.setText(commentDto.getText());

        commentsRepository.save(comment);

        log.info("Comment add to Ads id {} successful.", id);
        return adsCommentMapper.toCommentDtoFromEntity(comment);
    }

    @Override
    public void removeCommentFromAd(Integer adsId, Integer commentId) {
        log.info("Removing comment id {} , from ads", commentId);

        Comment comment = adsCommentMapper.toEntityFromCommentDto(
                getCommentById(adsId, commentId));

        commentsRepository.delete(comment);

        log.info("Comment removed.");
    }

    @Override
    public AdsCommentDto updateComment(Integer adsId, Integer commentId, CreateOrUpdateAdsCommentDto commentDto) {
        log.info("Updating comment with id {} for ads: ", commentId);

        Comment comment = getCommentEntityById(adsId, commentId);
        comment.setText(commentDto.getText());

        commentsRepository.save(comment);

        log.info("Comment with id {} update successful", commentId);
        return adsCommentMapper.toCommentDtoFromEntity(comment);
    }
}
