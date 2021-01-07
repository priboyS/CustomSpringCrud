package com.priboy.volunteer.controller;

import com.priboy.volunteer.security.RegistrationForm;
import com.priboy.volunteer.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    PasswordEncoder passwordEncoder;
    UserService userService;

    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public String registerForm(Model model){
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registrationPage";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm registrationForm, BindingResult bindingResult){
        if(!registrationForm.getConfirm().equals(registrationForm.getPassword())){
            bindingResult.rejectValue("confirm", "error.confirm", "Пароль подтверждения не совпадает");
        }
        if(bindingResult.hasErrors()){
            return "registrationPage";
        }
        userService.addUser(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
