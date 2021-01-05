package com.priboy.volunteer.security;

import com.priboy.volunteer.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RegistrationForm {
    private String username;
    private String email;
    private String password;

    private String fullname;
    private String city;
    private String phone;
    private String birth;
    private boolean male;
    private String photo;
    private String information;

    public User toUser(PasswordEncoder passwordEncoder){

        // проверка на наличие даты
        LocalDate localDate = null;
        if(!birth.equals("")){
            localDate = LocalDate.parse(birth);
        }

        return new User(username, email, passwordEncoder.encode(password), fullname, city, phone,
                localDate, photo, information);
    }
}






