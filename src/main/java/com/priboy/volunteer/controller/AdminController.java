package com.priboy.volunteer.controller;

import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String showAdminPanel(Model model){
        List<UserDto> allUser = userService.findAll();
        model.addAttribute("users", allUser);
        return "admin";
    }

    @GetMapping("/admin/edit")
    public String showAdminEditUser(@RequestParam String username, Model model){
        return "adminEditUser";
    }

    @PostMapping("/admin/delete")
    public String deleteAdminUser(@RequestParam String username){
        userService.deleteUserByUsername(username);
        return "redirect:/admin";
    }

}
