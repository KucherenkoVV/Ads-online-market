package ru.skypro.homework.dto.ads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CreateOrUpdateAdDto {
    private String title;
    private int price;
    private String description;
}
