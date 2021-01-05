package com.priboy.volunteer.security;

import com.priboy.volunteer.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RegistrationForm {
    private String mail;
    private String name;
    private String secondName;
    private String thirdName;
    private String password;
    private String city;
    private String phone;
    private String birth;
    private boolean male;

    public User toUser(PasswordEncoder passwordEncoder){
        return new User(mail, name, secondName, thirdName, passwordEncoder.encode(password), city, phone,
                LocalDate.parse(birth), male);
    }
}
