package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.auth.Register;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.model.User;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    User toEntityFromRegisterDto(Register register);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "image", source = "image")
    UserDto toUserDto(User user);

    List<UserDto> toListUsersDto (List<User> list);

}
