package com.priboy.volunteer.service;

import com.priboy.volunteer.dto.UserDto;

import java.util.Map;

public interface UserService {

    boolean addUser(UserDto userDto);
    boolean updateUser(UserDto userDto, Map<String, String> userParam);
    boolean updatePassword(UserDto userDto, String password);
    boolean updateUsername(UserDto userDto, String username);
    UserDto findByUsername(String username);
}
