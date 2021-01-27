package com.priboy.volunteer.service;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // проверка на наличие даты (при пустой строке ошибка)
    static public LocalDate checkLocalDate(String birth){
        LocalDate localDate = null;
        if(!birth.equals("")){
            localDate = LocalDate.parse(birth);
        }
        return localDate;
    }


    @Override
    public boolean addUser(UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(UserMapper.MAPPER.toUser(userDto));

        return true;
    }

    @Override
    public boolean updateUser(UserDto userDto, Map<String, String> userParam) {
        User user = UserMapper.MAPPER.toUser(userDto);

        user.setEmail(userParam.get("email"));
        user.setFullname(userParam.get("fullname"));
        user.setCity(userParam.get("city"));
        user.setPhone(userParam.get("phone"));
        user.setInformation(userParam.get("information"));
        user.setBirth(checkLocalDate(userParam.get("birth")));
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean updatePassword(UserDto userDto, String password) {
        User user = UserMapper.MAPPER.toUser(userDto);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUsername(UserDto userDto, String username) {
        User user = UserMapper.MAPPER.toUser(userDto);
        user.setUsername(username);
        userRepository.save(user);

        return true;
    }

    @Override
    public UserDto findByUsername(String username) {
        return UserMapper.MAPPER.toUserDto(userRepository.findByUsername(username));
    }

    @Override
    public UserDto findByUsernameProfile(String username) {
        return UserMapper.MAPPER.toUserDtoProfile(userRepository.findByUsername(username));
    }
}
