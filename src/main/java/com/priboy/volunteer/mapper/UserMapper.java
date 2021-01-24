package com.priboy.volunteer.mapper;

import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// реализацию за нас создаст сам mapstruct, но поля должны совпадать
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);
    UserDto userDto(User user);
}
