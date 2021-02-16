package com.priboy.volunteer.service;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean addUser(UserDto userDto){
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(UserMapper.MAPPER.toUser(userDto));

        return true;
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setInformation(userDto.getInformation());
        user.setFullname(userDto.getFullname());
        user.setPhone(userDto.getPhone());
        user.setBirth(userDto.getBirth());
        user.setCity(userDto.getCity());
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean updatePassword(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean updateUsername(UserDto userDto, String oldUsername) {
        User user = userRepository.findByUsername(oldUsername);
        user.setUsername(userDto.getUsername());
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
