package com.kodit.application.mapper;

import com.kodit.application.dto.UserDto;
import com.kodit.application.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto convertToUserDto(User user);
    List<UserDto> convertToUserDtoList(List<User> users);
}
