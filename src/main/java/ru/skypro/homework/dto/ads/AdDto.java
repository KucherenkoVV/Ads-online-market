package ru.skypro.homework.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdDto {

    private Integer author;
    private String image;
    private Integer pk;
    private int price;
    private String title;
    private String description;

}