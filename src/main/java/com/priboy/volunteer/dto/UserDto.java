package com.priboy.volunteer.dto;

import com.priboy.volunteer.domain.enumeration.Gender;
import com.priboy.volunteer.domain.enumeration.ProposalActive;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {
    long id;
    @NotBlank(message = "Поле должно быть заполнено")
    private String username;
    @NotBlank(message = "Поле должно быть заполнено")
    @Email(message = "Неправильная почта")
    private String email;
    @NotBlank(message = "Поле должно быть заполнено")
    private String password;
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
