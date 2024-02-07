package com.libraryCRUD.mainApp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.libraryCRUD.mainApp.entities.Role;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
public class MemberUserDTO implements UserDTO{

    @Pattern(regexp = "[a-zA-Z\s]+", message = "Name must contain only letters")
    @Size(max = 255, message = "Name can not be longer than 255 characters")
    private String fullName;
    @Email(message = "Email address has to be in a correct format")
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Ensure that the date is in ISO format
    private LocalDate dateOfBirth;
    private String address;
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only numbers")
    @Size(min = 10, max = 10, message = "Phone number has to be 10 digit long")
    private String phoneNumber;

    //Fields that only belong to Member class

    private Boolean membershipActive;

    private Integer currentBooks;

    //Setters and getters

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

    public Boolean getMembershipActive() {
        return membershipActive;
    }

    public void setMembershipActive(Boolean membershipActive) {
        this.membershipActive = membershipActive;
    }

    public Integer getCurrentBooks() {
        return currentBooks;
    }

    public void setCurrentBooks(Integer currentBooks) {
        this.currentBooks = currentBooks;
    }


    //Constructors

    public MemberUserDTO() {
        // Default constructor
    }


}
