package ru.skypro.homework.dto.adsComment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class AdsCommentDto {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private String createdAt;
    private Integer pk;
    private String text;

}
