package com.libraryCRUD.mainApp.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LibrarianUserDTO implements UserDTO{
    @Pattern(regexp = "[a-zA-Z\s]+", message = "Name must contain only letters")
    @Size(max = 45, message = "Name can not be longer than 45 characters")
    private String fullName;
    @Email(message = "Email address has to be in a correct format")
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Ensure that the date is in ISO format
    private LocalDate dateOfBirth;
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only numbers")
    @Size(min = 10, max = 10, message = "Phone number has to be 10 digit long")
    private String phoneNumber;
    @Size(max = 255, message = "Address can not be longer than 255 characters long")
    private String address;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password can not be null")
    private String password;
    //Librarian specific fields
    @Pattern(regexp = "^[a-zA-Z]+$", message = "WorkShift must contain only letters")
    private String workShift;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Department must contain only letters")
    private String department;


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
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getWorkShift() { return workShift; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public void setWorkShift(String workShift) { this.workShift = workShift; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    //Constructor

    public LibrarianUserDTO() {
        // Default constructor
    }

}






