package com.priboy.volunteer.service;

import com.priboy.volunteer.config.TestConfig;
import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.dto.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {TestConfig.class})
@EnableAutoConfiguration
// в @DataJpaTest не происходит автоматического сканирования всех компонентов, поэтому сервис добавляем вручную
// также сервису нужен репозиторий и энкодер, который мы тоже добавляем
@Import(UserServiceImpl.class)
public class UserServiceIntegrationTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    TestEntityManager testEntityManager;

    User user = User.builder()
            .username("anton")
            .email("priboysl@gmail.com")
            .build();

    // добавляем тестовые данные в базу данных
    @Before
    public void addDataDatabase(){
        user.setPassword(passwordEncoder.encode("1234"));
        testEntityManager.persistFlushFind(user);
    }

    // удалить тестовые данные из базы данных
    @After
    public void removeDataDatabase(){
        User userMerge = testEntityManager.merge(user);
        testEntityManager.remove(userMerge);
    }


    @Test
    public void addUser_thenOk(){
        UserDto userDto = UserDto.builder()
                .username("andrey")
                .password("1234")
                .email("priboys@tut.by")
                .build();

        userService.addUser(userDto);

        UserDto userDtoFound = userService.findByUsername("andrey");
        assertThat("andrey", equalTo(userDtoFound.getUsername()));
        assertThat("priboys@tut.by", equalTo(userDtoFound.getEmail()));
        assertTrue(passwordEncoder.matches("1234", userDtoFound.getPassword()));
    }

    // обновляем без имени и пароля, дто приходит с именем и остальными данными
    @Test
    public void updateUser_thenOk(){
        UserDto userDto = UserDto.builder()
                .username("anton")
                .email("priboys@tut.by")
                .city("Минск")
                .information("я делаю сайт")
                .fullname("Антон Андреевич")
                .phone("+375297101029")
                .birth(LocalDate.parse("2020-10-10"))
                .build();

        userService.updateUser(userDto);

        UserDto userDtoFound = userService.findByUsername("anton");
        assertThat("anton", equalTo(userDtoFound.getUsername()));
        assertThat("priboys@tut.by", equalTo(userDtoFound.getEmail()));
        assertThat("Минск", equalTo(userDtoFound.getCity()));
        assertThat("я делаю сайт", equalTo(userDtoFound.getInformation()));
        assertThat("Антон Андреевич", equalTo(userDtoFound.getFullname()));
        assertThat("+375297101029", equalTo(userDtoFound.getPhone()));
        assertThat(LocalDate.parse("2020-10-10"), equalTo(userDtoFound.getBirth()));
        assertTrue(passwordEncoder.matches("1234", userDtoFound.getPassword()));
    }

    // в данный метод приходит dto только с именем и паролем
    @Test
    public void updatePassword_thenOk(){
        UserDto userDto = UserDto.builder()
                .username("anton")
                .password("12345")
                .build();

        userService.updatePassword(userDto);

        UserDto userDtoFound = userService.findByUsername("anton");
        assertThat("anton", equalTo(userDtoFound.getUsername()));
        assertThat("priboysl@gmail.com", equalTo(userDtoFound.getEmail()));
        assertTrue(passwordEncoder.matches("12345", userDtoFound.getPassword()));
    }

    @Test
    public void updateUsername_thenOk(){
        UserDto userDto = UserDto.builder()
                .username("antonNew")
                .build();

        userService.updateUsername(userDto, "anton");

        UserDto userDtoFound = userService.findByUsername("antonNew");
        assertThat("antonNew", equalTo(userDtoFound.getUsername()));
        assertThat("priboysl@gmail.com", equalTo(userDtoFound.getEmail()));
        assertTrue(passwordEncoder.matches("1234", userDtoFound.getPassword()));
    }

    @Test
    public void findByUsername_thenOk(){
        UserDto userDtoFound = userService.findByUsername("anton");
        assertThat("anton", equalTo(userDtoFound.getUsername()));
        assertThat("priboysl@gmail.com", equalTo(userDtoFound.getEmail()));
        assertTrue(passwordEncoder.matches("1234", userDtoFound.getPassword()));
    }

    @Test
    public void findByUsernameProfile_thenOk(){
        UserDto userDtoFound = userService.findByUsernameProfile("anton");
        assertThat("anton", equalTo(userDtoFound.getUsername()));
        assertThat("priboysl@gmail.com", equalTo(userDtoFound.getEmail()));
        assertNull(userDtoFound.getPassword());
    }

}
