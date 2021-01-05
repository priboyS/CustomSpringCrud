package com.priboy.volunteer.controller;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping
public class ProfileController {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // безопасно ли это  - получать user так из userPrincipal?
    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userPrincipal.getUser();
        model.addAttribute(user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userPrincipal.getUser();
        model.addAttribute(user);
        return "editProfile";
    }

    // толстый контроллер
    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam Map<String, String> userParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userPrincipal.getUser();
        user.setName(userParam.get("name"));
        user.setSecondName(userParam.get("secondName"));
        user.setThirdName(userParam.get("thirdName"));
        user.setMail(userParam.get("mail"));
        user.setCity(userParam.get("city"));
        user.setPhone(userParam.get("phone"));
        user.setBirth(LocalDate.parse(userParam.get("birth")));
        userRepository.save(user);

        return "redirect:/profile";
    }

    @GetMapping("/profile/editPassword")
    public String showEditPassword(Model model){
        return "editPassword";
    }

    // толстый контроллер, какие ошибки выводить
    // как лучше прописывать ошибки
    @PostMapping("/profile/editPassword")
    public String editPassword(Model model, @RequestParam Map<String, String> passwordParam, @AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userPrincipal.getUser();

        String password = passwordParam.get("password");
        String confirm = passwordParam.get("confirm");
        String oldPassword = passwordParam.get("oldPassword");

        // проверяем confirm (в будущем сделать прямо в форме)
        if(!password.equals(confirm)){
            model.addAttribute("errorText", "Ваши пароли не совпали");
            model.addAttribute("error", true);
            return "editPassword";
        }
        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            model.addAttribute("errorText", "Неверный пароль");
            model.addAttribute("error", true);
            return "editPassword";
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return "redirect:/profile";
    }


}
