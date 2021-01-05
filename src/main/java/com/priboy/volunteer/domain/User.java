package com.priboy.volunteer.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String mail;
    private String name;
    private String secondName;
    private String thirdName;
    private String password;
    private String city;
    private String phone;
    private LocalDate birth;
    private boolean male;

    private int active;
    private String roles = "";
    private String permissions = "";

    public User(String mail, String name, String secondName, String thirdName, String password, String city, String phone, LocalDate birth, boolean male) {
        this.mail = mail;
        this.name = name;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.birth = birth;
        this.male = male;
        this.active = 1;
    }

    public User(String mail, String name, String secondName, String thirdName, String password, String city, String phone, LocalDate birth, boolean male, int active, String roles, String permissions) {
        this.mail = mail;
        this.name = name;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.birth = birth;
        this.male = male;
        this.active = 1;
        this.roles = roles;
        this.permissions = permissions;
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
}
