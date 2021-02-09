package com.priboy.volunteer;

import com.priboy.volunteer.controller.RegistrationController;
import com.priboy.volunteer.data.UserRepository;
import com.priboy.volunteer.dto.UserDto;
import com.priboy.volunteer.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// проверка сохранения объекта в контроллере с использованием сервиса и репозитория
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RegistrationControllerIntegrationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegistrationController registrationController;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Mock
    Model model;
    @Mock
    Errors errors;
    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    // тестируем, что модель в контролле правильно заполняется и отдает страницу с 400
    @Test
    public void getModel_modelReceived() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andExpect(model().attributeExists("userDto"))
                .andExpect(status().isOk());
    }

    // тестируем, что метод контроллера получает в запросе объект, отдает 400, правильно передает в сервис,
    // в сервисе правильно сохраняем и возвращаем объект
    @Test
    public void addUser_userSaved() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "anton")
                .param("email", "priboysl@gmail.com")
                .param("password", "788180195")
        ).andExpect(status().is3xxRedirection());

        UserDto userDtoFound = UserMapper.MAPPER.toUserDto(userRepository.findByUsername("anton"));
        assertThat("anton", equalTo(userDtoFound.getUsername()));
        assertThat("priboysl@gmail.com", equalTo(userDtoFound.getEmail()));
        assertTrue(passwordEncoder.matches("788180195", userDtoFound.getPassword()));
    }

    // здесь тестируем только один вариант неправильной валидации, саму валидацию тестируем в junit (не аннотации)
    // для проверки валидации также необходимо полностью имитировать запрос, а не просто вызвать метод контроллера
    @Test
    public void addWrongUser_userDontSaved() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "")
                .param("email", "priboysl@gmail.com")
                .param("password", "788180195")
        ).andExpect(status().isOk());

        UserDto userDtoFound = UserMapper.MAPPER.toUserDto(userRepository.findByUsername("anton"));
        assertNull(userDtoFound);
    }

}
