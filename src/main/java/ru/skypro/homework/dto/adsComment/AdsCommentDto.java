package ru.skypro.homework.dto.adsComment;

import lombok.Data;

@Data
public class AdsCommentDto {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private String createdAt;
    private Integer pk;
    private String text;

}
