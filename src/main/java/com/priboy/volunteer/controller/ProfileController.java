package com.priboy.volunteer.controller;

import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.security.UserPrincipal;
import com.priboy.volunteer.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping
public class ProfileController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        // UserPrincipal не обновляется после обновления данных, поэтому получаем так
        UserDto userDto = userService.findByUsernameProfile(userPrincipal.getUsername());
        model.addAttribute(userDto);

        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        UserDto userDto = userService.findByUsernameProfile(userPrincipal.getUsername());
        model.addAttribute(userDto);

        return "editProfile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam Map<String, String> userParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        UserDto userDto = userService.findByUsername(userPrincipal.getUsername());
        userService.updateUser(userDto, userParam);

        return "redirect:/profile";
    }

    @GetMapping("/profile/editPassword")
    public String showEditPassword(){
        return "editPassword";
    }

    @PostMapping("/profile/editPassword")
    public String editPassword(Model model, @RequestParam Map<String, String> userParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        UserDto userDto = userService.findByUsername(userPrincipal.getUsername());

        String password = userParam.get("password");
        String confirm = userParam.get("confirm");
        String oldPassword = userParam.get("oldPassword");

        // валидация полей
        if(!password.equals(confirm)){
            model.addAttribute("errorText", "Ваши пароли не совпали");
            model.addAttribute("error", true);
            return "editPassword";
        }else if (!passwordEncoder.matches(oldPassword, userPrincipal.getPassword())){
            model.addAttribute("errorText", "Неверный пароль");
            model.addAttribute("error", true);
            return "editPassword";
        }

        userService.updatePassword(userDto, password);

        return "redirect:/profile";
    }

    @GetMapping("/profile/editUsername")
    public String showEditUsername(){
        return "editUsername";
    }

    @PostMapping("profile/editUsername")
    public String editUsername(@RequestParam Map<String, String> userParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        UserDto userDto = userService.findByUsername(userPrincipal.getUsername());

        // задаем новый userPrincipal для бесшовной ауентификации (см. userPrincipal) (без токена ауентификации)
        if(userService.updateUsername(userDto, userParam.get("username"))){
            userPrincipal.setUsername(userParam.get("username"));
        }

        return "redirect:/profile";
    }
}
