package ru.skypro.homework.dto.ads;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdDto {

    private Integer author;
    private String image;
    private Integer pk;
    @NotNull
    private int price;
    @NotNull
    private String title;
    private String description;

}
