package com.priboy.volunteer.validation.validator;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class EmailMatchValidator implements Validator {
    UserRepository userRepository;

    public EmailMatchValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;
        if(userRepository.findByEmail(userDto.getEmail()) != null){
            errors.rejectValue("email", "emailExistErr", "Такая почта уже существует");
        }
    }
}

