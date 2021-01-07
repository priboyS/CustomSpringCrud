package com.priboy.volunteer.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Builder(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String email;
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
    private String organization = "";
    // перечисление для подачи заявок
    @Enumerated(EnumType.STRING)
    private ProposalActive proposalActive = ProposalActive.NONE;

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

    // вложенный клас с нумерацией
    enum ProposalActive{
        NONE, EMAIL, PHONE, EMAILPHONE, ACTIVE;
    }

    enum Gender{
        MALE, FEMALE;
    }
}
