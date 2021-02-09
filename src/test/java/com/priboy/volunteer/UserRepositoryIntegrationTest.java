package com.priboy.volunteer;

import com.priboy.volunteer.config.TestConfig;
import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
// если запускать SpringBootTest, то эти бубны не нужны
@DataJpaTest
// для замены H2 базы на мою MariaDB, прописанную в applicationProperties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// без этого не инжектался пассворд энкодер
@ContextConfiguration(classes = {TestConfig.class})
// без этого не работает тестконфиг
@EnableAutoConfiguration
public class UserRepositoryIntegrationTest{

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TestEntityManager testEntityManager;

    // исходные данные
    String username = "anton";
    String email = "priboysl@gmail.com";
    String password = "788180195";
    User userGiven = User.builder()
            .username(username)
            .email(email)
            .build();

    @Before
    public void addDataDatabase(){
        userGiven.setPassword(passwordEncoder.encode(password));
        testEntityManager.persistFlushFind(userGiven);
    }

    @After
    public void removeDataDatabase(){
        // прикрепляем объект обратно, так как сначала была одна транзакция, а это новая
        // и тут объекта нет в context
        User userMerge = testEntityManager.merge(userGiven);
        testEntityManager.remove(userMerge);
    }

    // тестируем свой метод в репозитории
    @Test
    public void findByUsername_thenOK(){
        User userFound = userRepository.findByUsername(username);
        assertThat(username, equalTo(userFound.getUsername()));
    }

    // тестируем свой метод в репозитории
    @Test
    public void findByEmail_thenOK(){
        User userFound = userRepository.findByEmail(email);
        assertThat(username, equalTo(userFound.getUsername()));
    }

}
