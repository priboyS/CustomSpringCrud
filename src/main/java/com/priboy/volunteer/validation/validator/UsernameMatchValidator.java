package com.priboy.volunteer.validation.validator;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
@RequiredArgsConstructor
public class UsernameMatchValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;
        if(userRepository.findByUsername(userDto.getUsername()) != null){
            errors.rejectValue("username", "usernameExistErr", "Такое имя пользователя уже есть");
        }
    }
}




