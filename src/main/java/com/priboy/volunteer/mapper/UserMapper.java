package com.priboy.volunteer.mapper;

import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

// реализацию за нас создаст сам mapstruct, но поля должны совпадать
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDto);
    UserDto toUserDto(User user);
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "confirm", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "active", ignore = true)
    UserDto toUserDtoProfile(User user);
}
