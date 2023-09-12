package ru.skypro.homework.dto.auth;

import lombok.Data;

@Data
public class NewPassword {

    private String currentPassword;
    private String newPassword;

}
