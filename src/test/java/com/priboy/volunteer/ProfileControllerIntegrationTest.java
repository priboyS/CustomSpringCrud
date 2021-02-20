package com.priboy.volunteer;

import com.priboy.volunteer.domain.User;
import com.priboy.volunteer.security.UserPrincipal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// проверка работоспособности контроллера
// используем mocMvc для имитации вызова контроллера
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@WebAppConfiguration
@Transactional
public class ProfileControllerIntegrationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;
    // без roles, permissions, active не заработает, ибо в principal должно быть
    // при builder они null
    User user = User.builder()
            .username("anton")
            .email("priboysl@gmail.com")
            .roles("")
            .permissions("")
            .active(1)
            .build();

    @Before
    public void addDataDatabase(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();

        user.setPassword(passwordEncoder.encode("1234a"));
        testEntityManager.persistFlushFind(user);
    }

    @After
    public void removeDataDatabase(){
        User userMerge = testEntityManager.merge(user);
        testEntityManager.remove(userMerge);
    }

    // вставляем UserPrincipal для @AuthenicationPrincipal
    // нужно @WebAppConfiguration и .apply(springSecurity())
    @Test
    public void getRequest_thenOk() throws Exception {
        UserPrincipal userPrincipal = new UserPrincipal(user);
        this.mockMvc.perform(get("/profile").with(user(userPrincipal)))
                .andExpect(status().isOk());
    }
}
