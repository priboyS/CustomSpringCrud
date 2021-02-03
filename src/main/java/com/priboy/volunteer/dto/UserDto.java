package com.priboy.volunteer.dto;

import com.priboy.volunteer.domain.enumeration.Gender;
import com.priboy.volunteer.domain.enumeration.ProposalActive;
import com.priboy.volunteer.validation.ValidEmail;
import com.priboy.volunteer.validation.ValidPasswordMatch;
import com.priboy.volunteer.validation.groups.PasswordInfo;
import com.priboy.volunteer.validation.groups.ProfileInfo;
import com.priboy.volunteer.validation.groups.UsernameInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@ValidPasswordMatch(groups = PasswordInfo.class)
public class UserDto {
    long id;
    @NotBlank(message = "Поле должно быть заполнено", groups = UsernameInfo.class)
    @Pattern(regexp = "([\\w]*)([а-яА-я]*)|([а-яА-Я]*)([\\w]*).{3,15}",
            message = "Не должно содержать спецсимволы, не менее 3 и не более 15 символов",
            groups = UsernameInfo.class)
    private String username;
    @ValidEmail(groups = ProfileInfo.class)
    @NotBlank(message = "Поле должно быть заполнено", groups = ProfileInfo.class)
    private String email;
    @NotBlank(message = "Поле должно быть заполнено", groups = PasswordInfo.class)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-z]).{4,20}",
            message = "Пароль должен содержать цифры и буквы (только латиница), не менее 4 и не более 20 символов",
            groups = PasswordInfo.class)
    private String password;
    @NotBlank(message = "Поле должно быть заполнено", groups = PasswordInfo.class)
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

