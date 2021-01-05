package com.priboy.volunteer.controller;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.security.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    public RegistrationController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String registerForm(Model model){
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registrationPage";
    }

    @PostMapping
    public String processRegistration(RegistrationForm registrationForm){
        userRepository.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
