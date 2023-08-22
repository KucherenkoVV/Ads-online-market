package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.auth.NewPassword;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.model.User;

import java.util.List;

public interface UserService {

    UserDto getUserById (Integer id);

    User getUserByUsername(String username);

    List<UserDto> getAllUser ();

    void updateUserPassword(NewPassword newPassword, Authentication authentication);

    UserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication);

    void updateUserAvatar(Authentication authentication, MultipartFile file);
}
