package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.model.User;

@Component
public class UserMapper {

    public User toEntityFromUserDto (UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setImage(userDto.getImage());
        return user;
    }

    public UserDto toUserDtoFromEntity (User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setImage(user.getImage());
        return userDto;
    }

    public UpdateUserDto toUpdateDtoFromEntity (User user) {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName(user.getFirstName());
        updateUserDto.setLastName(user.getLastName());
        updateUserDto.setPhone(user.getPhone());
        return updateUserDto;
    }

    public User toEntityFromUpdateUserDto (UpdateUserDto updateUserDto) {
        User user = new User();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        return user;
    }

}
