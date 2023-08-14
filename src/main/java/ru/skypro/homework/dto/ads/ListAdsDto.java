package ru.skypro.homework.dto.ads;

import lombok.Data;

import java.util.List;

@Data
public class ListAdsDto {
    private Integer count;
    private List<AdDto> results;
}
