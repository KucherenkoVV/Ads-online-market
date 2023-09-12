package ru.skypro.homework.dto.adsComment;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdsCommentDto {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private String createdAt;
    private Integer pk;
    @NotNull
    private String text;

}
