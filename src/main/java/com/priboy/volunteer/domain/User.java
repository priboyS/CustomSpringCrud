package com.priboy.volunteer.domain;

import com.priboy.volunteer.domain.enumeration.Gender;
import com.priboy.volunteer.domain.enumeration.ProposalActive;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Builder(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "(?=[\\w]*)(?=[^\\W])(?=[а-яА-я]*).{3,15}")
    private String username;
    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[_A-Za-z0-9-+]" +
            "(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*"+
            "(.[A-Za-z]{2,})$")
    private String email;
    @NotBlank
    @Size(min = 32, max = 128)
    private String password;
    private String fullname;
    private String city;
    private String phone;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.MALE;
    private String photo;
    private String information;
    private LocalDate dateRegistration;
    private String organization;
    // перечисление для подачи заявок
    @Enumerated(EnumType.STRING)
    private ProposalActive proposalActive = ProposalActive.NONE;

    @Column(columnDefinition = "int default 1")
    private int active = 1;
    private String roles = "";
    private String permissions = "";

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<String>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<String>();
    }

    // получаем текущую дату регистрации
    @PrePersist
    void dateRegistration(){
        this.dateRegistration = LocalDate.now();
    }

}
