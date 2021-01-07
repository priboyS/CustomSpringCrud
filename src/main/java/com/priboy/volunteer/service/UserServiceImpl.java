package com.priboy.volunteer.service;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // проверка на наличие даты (при пустой строке ошибка)
    static public LocalDate checkLocalDate(String birth){
        LocalDate localDate = null;
        if(!birth.equals("")){
            localDate = LocalDate.parse(birth);
        }
        return localDate;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Map<String, String> userParam) {
        user.setUsername(userParam.get("username"));
        user.setEmail(userParam.get("email"));
        user.setFullname(userParam.get("fullname"));
        user.setCity(userParam.get("city"));
        user.setPhone(userParam.get("phone"));
        user.setInformation(userParam.get("information"));
        user.setBirth(checkLocalDate(userParam.get("birth")));
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, Map<String, String> passwordParam) {
        String password = passwordParam.get("password");
        String confirm = passwordParam.get("confirm");
        String oldPassword = passwordParam.get("oldPassword");

//        // проверяем confirm (в будущем сделать прямо в форме)
//        if(!password.equals(confirm)){
//            model.addAttribute("errorText", "Ваши пароли не совпали");
//            model.addAttribute("error", true);
//            return "editPassword";
//        }
//        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
//            model.addAttribute("errorText", "Неверный пароль");
//            model.addAttribute("error", true);
//            return "editPassword";
//        }

        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
}
