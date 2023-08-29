package ru.skypro.homework.dto.ads;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ListAdsDto {
    private Integer count;
    private List<AdDto> results;
}
