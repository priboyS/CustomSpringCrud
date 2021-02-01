package com.priboy.volunteer.validation;

import com.priboy.volunteer.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPasswordMatchImpl implements ConstraintValidator<ValidPasswordMatch, Object> {
    @Override
    public void initialize(ValidPasswordMatch constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserDto userDto = (UserDto) object;
        return userDto.getPassword().equals(userDto.getConfirm());
    }
}
