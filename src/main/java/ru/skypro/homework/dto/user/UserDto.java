package ru.skypro.homework.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserDto {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;
}
