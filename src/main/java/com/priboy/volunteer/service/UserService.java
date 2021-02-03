package com.priboy.volunteer.service;

import com.priboy.volunteer.dto.UserDto;

public interface UserService {

    boolean addUser(UserDto userDto);
    boolean updateUser(UserDto userDto);
    boolean updatePassword(UserDto userDto);
    boolean updateUsername(UserDto userDto, String oldUsername);
    UserDto findByUsername(String username);
    UserDto findByUsernameProfile(String username);
}
