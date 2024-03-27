package com.libraryCRUD.mainApp.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libraryCRUD.mainApp.enums.Role;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

//DTO to entry new entities and show a libraryUsers list
public class LibraryUserDTO implements UserDTO{
    @Pattern(regexp = "[a-zA-Z\s]+", message = "Name must contain only letters")
    @Size(max = 45, message = "Name can not be longer than 45 characters")
    @NotNull(message = "Name can not be null")
    private String fullName;
    @NotNull(message = "Email can not be null")
    @Email(message = "Email address has to be in a correct format")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password can not be null")
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //This field is not provided by user. It is only use to show information
    private String registrationDate;
    @NotNull(message = "Date of birth can not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Ensure that the date is in ISO format
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only numbers")
    @Size(min = 10, max = 10, message = "Phone number has to be 10 digit long")
    private String phoneNumber;
    @Size(max = 255, message = "Address can not be longer than 255 characters long")
    private String address;
    //So far eNum is taking care of Validation for member or librarian but subject to change
    @NotNull(message = "userRole can not be null")
    private Role userRole;


    //Constructor

    public LibraryUserDTO() {
    }


    //Getters and setters

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
    public Role getUserRole() { return userRole; }
    public void setUserRole(Role userRole) { this.userRole = userRole; }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
