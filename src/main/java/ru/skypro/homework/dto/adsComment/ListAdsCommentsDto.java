package ru.skypro.homework.dto.adsComment;

import lombok.Data;

import java.util.List;

@Data
public class ListAdsCommentsDto {

        private Integer count;

        private List<AdsCommentDto> results;
}
