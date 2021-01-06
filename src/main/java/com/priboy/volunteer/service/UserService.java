package com.priboy.volunteer.service;

import com.priboy.volunteer.domain.User;

import java.util.Map;

public interface UserService {

    User addUser(User user);
    User updateUser(User user, Map<String, String> userParam);
    User updatePassword(User user, Map<String, String> passwordParam);
}
