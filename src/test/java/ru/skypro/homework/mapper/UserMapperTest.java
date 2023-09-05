package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void init() {
        userMapper = new UserMapper();

    }

    @Test
    void shouldMapToEntityFromUserDto() {

        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setFirstName("Name");
        userDto.setLastName("LastName");
        userDto.setPhone("+79990001122");
        userDto.setImage("image");

        User user = userMapper.toEntityFromUserDto(userDto);

        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getPhone(), user.getPhone());
        assertEquals(userDto.getImage(), user.getImage());
    }

    @Test
    void shouldMapToUserDtoFromEntity() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setFirstName("Name");
        user.setLastName("LastName");
        user.setPhone("+79990001122");
        user.setImage("Image");

        UserDto userDto = userMapper.toUserDtoFromEntity(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getPhone(), userDto.getPhone());
        assertEquals(user.getImage(), userDto.getImage());
    }

}