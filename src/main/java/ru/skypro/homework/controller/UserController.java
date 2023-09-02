package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.auth.NewPassword;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@Tag(name = "Пользователи", description = "UserController")
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final ImageServiceImpl imageService;

    public UserController(UserServiceImpl userService, UserMapper userMapper, ImageServiceImpl imageService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.imageService = imageService;
    }

    @Operation(
            summary = "Обновление пароля", tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NewPassword.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword, Authentication authentication) {
        userService.updateUserPassword(newPassword, authentication);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить информацию об авторизованном пользователе", tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content)
            }
    )
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());
        UserDto userDto = userMapper.toUserDtoFromEntity(user);
        return ResponseEntity.ok(userDto);

    }

    @Operation(
            summary = "Обновить информацию об авторизованном пользователе", tags = "Пользователи",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))}),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content)
            }
    )
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto updateUserDto, Authentication authentication) {
        UserDto userDto = userService.updateUser(updateUserDto, authentication);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Обновить аватар авторизованного пользователя", tags = "Пользователи",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorised", content = @Content)
            }
    )
//    @PreAuthorize("isAuthenticated()")
    @PreAuthorize("hasRole('ROLE_ADMIN, ROLE_USER')")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserAvatar(@RequestPart("image") MultipartFile multipartFile, Authentication authentication)  {
        userService.updateUserAvatar(authentication, multipartFile);
        return ResponseEntity.ok().build();
    }

    @Operation(hidden = true)
    @GetMapping(value = "/avatar/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getAvatar (@PathVariable("id") Integer id) {
        return id.toString();
    }
}

