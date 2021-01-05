package com.priboy.volunteer;

import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Service
public class DbInit implements CommandLineRunner {

//    final private UserRepository userRepository;
//    final private PasswordEncoder passwordEncoder;
//
//    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public void run(String... args) throws Exception {
//
//        User user = new User("priboysl@gmail.com", "Антон", "Похолкин", "Андреевич", passwordEncoder.encode("788180195"),
//                "Минск","+375292708219", new GregorianCalendar(1995, Calendar.JANUARY, 18), true);
//
//        userRepository.save(user);

    }
}
