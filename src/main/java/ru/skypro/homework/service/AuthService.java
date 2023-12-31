package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.auth.Login;
import ru.skypro.homework.dto.auth.Register;


public interface AuthService {
    boolean login(String userName, String password);

    boolean register(Register register);
}
