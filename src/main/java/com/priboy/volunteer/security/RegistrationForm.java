package com.priboy.volunteer.security;

import com.priboy.volunteer.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RegistrationForm {
    @NotBlank(message = "Поле должно быть заполнено")
    private String username;
    @NotBlank(message = "Поле должно быть заполнено")
    @Email(message = "Неправильная почта")
    private String email;
    @NotBlank(message = "Поле должно быть заполнено")
    private String password;
    private String confirm;


    public User toUser(PasswordEncoder passwordEncoder){

        return User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password)).build();
    }
}






