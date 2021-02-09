package com.priboy.volunteer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TestConfig {

    // пришлось пометить имененм, так как интеграционный тест видел только этот бин (@DataJpaTest), а
    // вот @SpringBootTest видел сразу все бины и кидал ошибку
    @Bean(name = "TestEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
