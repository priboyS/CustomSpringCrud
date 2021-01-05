package com.priboy.volunteer.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public User(String username, String email, String password, String fullname, String city, String phone, LocalDate birth, String photo, String information) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.city = city;
        this.phone = phone;
        this.birth = birth;
        this.photo = photo;
        this.information = information;
    }

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
