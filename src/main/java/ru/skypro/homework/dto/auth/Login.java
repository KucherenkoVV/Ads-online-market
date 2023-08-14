package ru.skypro.homework.dto.auth;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class Login {

    private String username;
    private String password;
}
