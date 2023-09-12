package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.time.Instant;

class AdsCommentMapperTest {

    private AdsCommentMapper adsCommentMapper;
    private User user;

    @BeforeEach
    void init () {
        user = new User();
        user.setId(1);
        user.setFirstName("Ivan");
        user.setImage("/image/test");
        adsCommentMapper = new AdsCommentMapper();
    }

    @Test
    void shouldMapToCommentDtoFromEntity() {

        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("Test text");
        comment.setCreatedAt(Instant.now());
        comment.setAuthor(user);

        AdsCommentDto commentDto = adsCommentMapper.toCommentDtoFromEntity(comment);

        Assertions.assertEquals(commentDto.getPk(), comment.getId());
        Assertions.assertEquals(commentDto.getText(), comment.getText());
        Assertions.assertEquals(commentDto.getCreatedAt(), comment.getCreatedAt().toString());
        Assertions.assertEquals(commentDto.getAuthor(), comment.getAuthor().getId());
        Assertions.assertEquals(commentDto.getAuthorFirstName(), comment.getAuthor().getFirstName());
        Assertions.assertEquals(commentDto.getAuthorImage(), comment.getAuthor().getImage());
    }

    @Test
    void shouldMapToEntityFromCommentDto() {

        AdsCommentDto adsCommentDto = new AdsCommentDto();
        adsCommentDto.setPk(1);
        adsCommentDto.setText("Test text");

        Comment comment = adsCommentMapper.toEntityFromCommentDto(adsCommentDto);

        Assertions.assertEquals(adsCommentDto.getPk(), comment.getId());
        Assertions.assertEquals(adsCommentDto.getText(), comment.getText());
    }
}