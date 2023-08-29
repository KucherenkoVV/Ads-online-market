package ru.skypro.homework.dto.ads;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ExtendedAdDto {

    private Integer pk;
    private String authorLastName;
    private String authorFirstName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private int price;
    private String title;

}
