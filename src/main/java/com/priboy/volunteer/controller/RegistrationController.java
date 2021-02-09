package com.priboy.volunteer.controller;

import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.service.UserService;
import com.priboy.volunteer.validation.groups.PasswordInfo;
import com.priboy.volunteer.validation.groups.ProfileInfo;
import com.priboy.volunteer.validation.groups.UsernameInfo;
import com.priboy.volunteer.validation.validator.EmailMatchValidator;
import com.priboy.volunteer.validation.validator.UsernameMatchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// внедрение полей происходит через конструктор (его не видно из-за аннотации lombok)
@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UsernameMatchValidator usernameMatchValidator;
    private final EmailMatchValidator emailMatchValidator;
    private final UserService userService;

    @GetMapping
    public String registerForm(Model model){
        model.addAttribute(new UserDto());

        return "registrationPage";
    }

    @PostMapping
    public String processRegistration(@Validated({ProfileInfo.class, PasswordInfo.class, UsernameInfo.class}) UserDto userDto, Errors errors, Model model){
        // проверяем на уникальность почту и имя
        usernameMatchValidator.validate(userDto, errors);
        emailMatchValidator.validate(userDto, errors);
        if(errors.hasErrors()){
            model.addAttribute(userDto);
            return "registrationPage";
        }

        userService.addUser(userDto);

        return "redirect:/login";
    }

}
