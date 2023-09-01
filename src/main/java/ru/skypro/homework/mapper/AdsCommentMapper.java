package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.model.Comment;

@Component
public class AdsCommentMapper {

    public AdsCommentDto toCommentDtoFromEntity (Comment comment){
        AdsCommentDto commentDto = new AdsCommentDto();
        commentDto.setPk(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setCreatedAt(comment.getCreatedAt().toString());
        commentDto.setAuthorFirstName(comment.getAuthor().getFirstName());
        commentDto.setAuthor(comment.getAuthor().getId());
        commentDto.setAuthorImage(comment.getAuthor().getImage());
        return commentDto;
    }

    public Comment toEntityFromCommentDto (AdsCommentDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setId(commentDto.getPk());
        return comment;

    }

}
