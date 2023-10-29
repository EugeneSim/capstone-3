package com.example.demooauth;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int acctNum;
    
    @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)?$", message = "Name must have 2 to 20 alphabets")
    private String name;
    @Pattern(regexp = "^[A-Za-z]\\d{7}[A-Za-z]$", message = "Invalid NRIC format")
    private String nric;
    @Pattern(regexp = "^\\d{8}$", message = "Invalid Phone Number")
    private String phoneNumber;
    @Pattern(regexp = "^[^@]+@[^@]+\\.com$", message = "Invalid email format")
    private String email;
    @NotNull(message = "Invalid Birthdate")
    @Past(message = "Cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?#&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = " 8-20 characters with at least 1 Caps, 1 small, 1 spl char, 1 number")
    private String acctPwd;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "Invalid userId format")
    @Column(unique = true)
    private String userId;

    private double balance;
    private int tellerId;
    private String acctStatus;


    public int getAcctNum() {
        return acctNum;
    }


    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }


    public void setAcctNum(int acctNum) {
        this.acctNum = acctNum;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getNric() {
        return nric;
    }


    public void setNric(String nric) {
        this.nric = nric;
    }


    public String getphoneNumber() {
        return phoneNumber;
    }


    public void setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }


    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public String getacctPwd() {
        return acctPwd;
    }


    public void setacctPwd(String acctPwd) {
        this.acctPwd = acctPwd;
    }


    public String getuserId() {
        return userId;
    }


    public void setuserId(String userId) {
        this.userId = userId;
    }


    public Student(
            @Pattern(regexp = "^[A-Za-z]+(?: [A-Za-z]+)?$", message = "Name must have 2 to 20 alphabets") String name,
            @Pattern(regexp = "^[A-Za-z]\\d{7}[A-Za-z]$", message = "Invalid NRIC format") String nric,
            @Pattern(regexp = "^\\d{8}$", message = "Invalid Phone Number") String phoneNumber,
            @Pattern(regexp = "^[^@]+@[^@]+\\.com$", message = "Invalid email format") String email,
            @NotNull(message = "Invalid Birthdate") @Past(message = "Cannot be in the past") LocalDate birthDate,
            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?#&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = "Must contain at least 8-20 characters with at least 1 Caps, 1 small, 1 spl character and 1 number") String acctPwd,
            @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "Invalid userId format") String userId,double balance,int tellerId,String acctStatus) {
        this.name = name;
        this.nric = nric;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.acctPwd = acctPwd;
        this.userId = userId;
        this.balance=balance;
        this.tellerId=tellerId;
        this.acctStatus=acctStatus;
    }


    public Student() {
    }


    
    

}