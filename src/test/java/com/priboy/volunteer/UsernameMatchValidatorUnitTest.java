package com.priboy.volunteer;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.mapper.UserMapper;
import com.priboy.volunteer.validation.validator.UsernameMatchValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

// используем заглушку для ЮзерРепозитория, чтобы тест был юнит, мы и так репозиторий проверили до этого
// для Errors делаю базовую реализацию сам
@RunWith(MockitoJUnitRunner.class)
public class UsernameMatchValidatorUnitTest {
    @Mock
    UserRepository mockUserRepository;

    @Test
    public void checkValidateError(){
        UserDto userDto = UserDto.builder()
                .username("anton")
                .email("priboysl@gmail.com")
                .password("1234")
                .build();
        User user = UserMapper.MAPPER.toUser(userDto);
        when(mockUserRepository.findByUsername("anton")).thenReturn(user);
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        UsernameMatchValidator usernameMatchValidator = new UsernameMatchValidator(mockUserRepository);
        usernameMatchValidator.validate(userDto, errors);

        assertTrue(errors.hasErrors());
    }

    @Test
    public void checkValidateNoError(){
        UserDto userDto = UserDto.builder()
                .username("anton")
                .email("priboysl@gmail.com")
                .password("1234")
                .build();
        when(mockUserRepository.findByUsername("anton")).thenReturn(null);
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        UsernameMatchValidator usernameMatchValidator = new UsernameMatchValidator(mockUserRepository);
        usernameMatchValidator.validate(userDto, errors);

        assertFalse(errors.hasErrors());
    }

}