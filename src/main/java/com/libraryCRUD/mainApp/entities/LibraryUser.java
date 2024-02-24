package com.libraryCRUD.mainApp.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "library_users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role", discriminatorType = DiscriminatorType.STRING, length = 10)

public abstract class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name", columnDefinition = "VARCHAR(45) NOT NULL")
    private String fullName;

    @Column(name = "email", columnDefinition = "VARCHAR(45) NOT NULL UNIQUE")
    private String email;

    @Column(name = "date_of_birth", columnDefinition = "DATE NOT NULL")
    private LocalDate dateOfBirth;

    @Column(name = "address", columnDefinition = "VARCHAR(255)")
    private String address;

    @Column(name = "phone_number", columnDefinition = "VARCHAR(10)")
    private String phoneNumber;

    @Column(name = "registration_date", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime registrationDate;

    @Column(name = "user_role", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

    //Constructors

    public LibraryUser(){
        this.registrationDate = LocalDateTime.now(); // Set registration date to current time since this fields is not provided by user
    }

    public LibraryUser(String fullName, String email, LocalDate dateOfBirth, String address, String phoneNumber, LocalDateTime registrationDate, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.userRole = role;
    }

    //Getters and setters


    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }
}
