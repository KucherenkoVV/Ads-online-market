package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.auth.NewPassword;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.exception.AuthentificationException;
import ru.skypro.homework.exception.EmptyArgumentException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // todo допилить логирование, сохранение ссылок на картинки
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Override
    public UserDto getUserById(Integer id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username).orElseThrow();
    }

    @Override
    public List<UserDto> getAllUser() {
        return userMapper.toListUsersDto(userRepository.findAll());
    }

    @Override
    public void updateUserPassword(NewPassword newPassword, Authentication authentication) {
        User user = getUserByUsername(authentication.getName());
        if (passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
            log.debug("Password updated for user: {}", authentication.getName());
        } else {
            throw new AuthentificationException("Password incorrect.");
        }
    }

    @Override
    public UserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication) {
        if (!updateUserDto.getFirstName().isBlank() || !updateUserDto.getLastName().isBlank() || !updateUserDto.getPhone().isBlank()) {
            User user = getUserByUsername(authentication.getName());
            if (updateUserDto.getFirstName() != null) {
                user.setFirstName(updateUserDto.getFirstName());
            }
            if (updateUserDto.getLastName() != null) {
                user.setLastName(updateUserDto.getLastName());
            }
            if (updateUserDto.getPhone() != null){
                user.setPhone(updateUserDto.getPhone());
            }
            userRepository.save(user);
            log.debug("User details updated for user: {}", authentication.getName());
            return userMapper.toUserDto(user);
        } else {
            throw new EmptyArgumentException("Information for update User is not enough.");
        }

    }

    @Override
    public void updateUserAvatar(Authentication authentication, MultipartFile file) {
        User user = getUserByUsername(authentication.getName());
        imageService.uploadImage(file);
        // доделать сохранение ссылки пользователю после создания эндпоинта
    }
}
