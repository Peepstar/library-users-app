package com.libraryCRUD.mainApp.dto.usersdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libraryCRUD.mainApp.enums.Role;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.Set;

// --> DTO to persist in database and show data to user (only MEMBER) <-- \\
public class MemberUserDTO implements UserDTO{
    @Pattern(regexp = "[a-zA-Z\s]+", message = "Name must contain only letters")
    @Size(max = 45, message = "Name can not be longer than 45 characters")
    private String fullName;
    @Email(message = "Email address has to be in a correct format")
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Ensure that the date is in ISO format
    private LocalDate dateOfBirth;
    @Size(max = 255, message = "Address can not be longer than 255 characters long")
    private String address;
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only numbers")
    @Size(min = 10, max = 10, message = "Phone number has to be 10 digit long")
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //  Field is only available to be UPDATED but not to be READ
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Field is only available to be READ but not to be UPDATED
    private Role userRole;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Field is only available to be READ but not to be UPDATED
    private Set<String> currentSeminaries;


    //  Setters and getters

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    @Override
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPassword() { return password; }
    public Role getUserRole() { return userRole; }
    public void setUserRole(Role userRole) { this.userRole = userRole; }
    public void setPassword(String password) { this.password = password; }
    public Set<String> getCurrentSeminaries() { return currentSeminaries; }
    public void setCurrentSeminaries(Set<String> currentSeminaries) { this.currentSeminaries = currentSeminaries; }


    //  Constructors

    public MemberUserDTO() {
        // Default constructor
    }

}
