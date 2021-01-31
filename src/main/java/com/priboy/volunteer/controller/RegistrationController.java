package com.priboy.volunteer.controller;

import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.service.UserService;
import com.priboy.volunteer.validation.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

// внедрение полей происходит через конструктор (его не видно из-за аннотации lombok)
@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationValidator registrationValidator;
    private final UserService userService;

    @GetMapping
    public String registerForm(Model model){
        model.addAttribute("userDto", new UserDto());

        return "registrationPage";
    }

    @PostMapping
    public String processRegistration(@Valid UserDto userDto, Errors errors){
        // проверяем на уникальность почту и имя
        registrationValidator.validate(userDto, errors);
        if(errors.hasErrors()){
            return "registrationPage";
        }

        userService.addUser(userDto);

        return "redirect:/login";
    }

}
