package com.priboy.volunteer.dto;

import com.priboy.volunteer.domain.enumeration.Gender;
import com.priboy.volunteer.domain.enumeration.ProposalActive;
import com.priboy.volunteer.validation.ValidEmail;
import com.priboy.volunteer.validation.ValidPasswordConfirm;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@ValidPasswordConfirm
public class UserDto {
    long id;
    @NotBlank(message = "Поле должно быть заполнено")
    @Pattern(regexp = "([\\w]*)([а-яА-я]*)|([а-яА-Я]*)([\\w]*)",
             message = "Не должно содержать спецсимволы, не менее 3 и не более 15 символов")
    private String username;
    @ValidEmail
    @NotBlank(message = "Поле должно быть заполнено")
    private String email;
    @NotBlank(message = "Поле должно быть заполнено")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-z]).{4,20}",
            message = "Пароль должен содержать цифры и буквы (только латиница), не менее 4 и не более 20 символов")
    private String password;
    @NotBlank(message = "Поле должно быть заполнено")
    private String confirm;
    private String fullname;
    private String city;
    private String phone;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String photo;
    private String information;
    private LocalDate dateRegistration;
    private String organization;
    // перечисление для подачи заявок
    @Enumerated(EnumType.STRING)
    private ProposalActive proposalActive = ProposalActive.NONE;

    private int active = 1;
    private String roles = "";
    private String permissions = "";
}

