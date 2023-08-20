package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.adsComment.AdsCommentDto;
import ru.skypro.homework.model.Comment;


@Mapper(componentModel = "spring")
public interface AdsCommentMapper {

    AdsCommentMapper INSTANSE = Mappers.getMapper(AdsCommentMapper.class);


    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    Comment toEntity(AdsCommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    AdsCommentDto toDto(Comment entity);



}