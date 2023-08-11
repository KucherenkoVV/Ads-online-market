package ru.skypro.homework.dto.ads;

import lombok.Data;

import java.util.List;

@Data
public class ListAdsDTO {
    private Integer count;
    private List<AdDTO> results;
}
