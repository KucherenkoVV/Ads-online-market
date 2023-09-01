package ru.skypro.homework.service.impl;

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
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class UserServiceImpl implements UserService {

    // todo допилить сохранение ссылок на картинки
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @Override
    public UserDto getUserById(Integer id) {
        log.info("Getting user by id {}.", id);
        UserDto userDto = userMapper.toUserDtoFromEntity(userRepository.findById(id).orElseThrow());
        log.info("User received.");
        return userDto;
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("Getting user by username {}", username);
        User user = userRepository.findByUsernameIgnoreCase(username).orElseThrow();
        log.info("User received by username {}", username);
        return user;
    }

    @Override
    public List<UserDto> getAllUser() {
        log.info("Getting all users.");
        List<UserDto> userDtoList = userRepository.findAll()
                .stream()
                .map(userMapper::toUserDtoFromEntity)
                .collect(Collectors.toList());
        log.info("All user received.");
        return userDtoList;
    }

    @Override
    public void updateUserPassword(NewPassword newPassword, Authentication authentication) {
        log.info("Updating user password by new password.");
        User user = getUserByUsername(authentication.getName());
        if (passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
            log.info("Password updated for user: {}", authentication.getName());
        } else {
            throw new AuthentificationException("Password incorrect.");
        }
    }

    @Override
    public UserDto updateUser(UpdateUserDto updateUserDto, Authentication authentication) {
        log.info("Updating user from UpdateUserDto.");
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
            log.info("User details updated for user: {}", authentication.getName());
            return userMapper.toUserDtoFromEntity(user);
        } else {
            throw new EmptyArgumentException("Information for update User is not enough.");
        }

    }

    @Override
    public void updateUserAvatar(Authentication authentication, MultipartFile file) {
        log.info("Updating user avatar by name {} from new file.", authentication.getName());
        User user = getUserByUsername(authentication.getName());
        imageService.uploadImage(file);
        log.info("Avatar updated for user with name {}", authentication.getName());
        // доделать сохранение ссылки пользователю после создания эндпоинта
    }
}
