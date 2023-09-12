package ru.skypro.homework.dto.ads;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateOrUpdateAdDto {

    @NotNull
    private String title;
    @NotNull
    private int price;
    @NotNull
    private String description;
}
