package com.priboy.volunteer.security;

import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.util.CheckDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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


        return new User(username, email, passwordEncoder.encode(password), fullname, city, phone,
                CheckDate.checkLocalDate(birth), photo, information);
    }
}






