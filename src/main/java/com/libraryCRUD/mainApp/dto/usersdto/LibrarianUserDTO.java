package com.libraryCRUD.mainApp.dto.usersdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libraryCRUD.mainApp.enums.Role;
import com.libraryCRUD.mainApp.enums.Specialization;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.WrongSpecializationException;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.Set;

// --> DTO to persist in database and show data to user (only LIBRARIAN) <-- \\
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Property is only available to be UPDATED but not to be READ
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Property is only available to be READ but not to be UPDATED
    private Role userRole;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //  Property is only used to POST or PUT a new specialization
    private Specialization newSpecialization;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Property is only available to be READ but not to be UPDATED
    private Set<Specialization> specializations;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Property is only available to be READ but not to be UPDATED
    private Set<String> seminaries;


    //  Setters and getters

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    @Override
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
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Role getUserRole() { return userRole; }
    public void setUserRole(Role userRole) { this.userRole = userRole; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Specialization getNewSpecialization() { return newSpecialization; }
    // Check if newSpecialization is valid before deserialization to throw personalized exception.
    public void setNewSpecialization(String newSpecialization) throws WrongSpecializationException {
        try {
            this.newSpecialization = Specialization.valueOf(newSpecialization);
        } catch (IllegalArgumentException e) {
            throw new WrongSpecializationException("Invalid value for Specialization. Only  HISTORY, LITERATURE, SCIENCE, PHILOSOPHY, LANGUAGES are allowed");
        }
    }
    public Set<Specialization> getSpecializations() { return specializations; }
    public void setSpecializations(Set<Specialization> specializations) { this.specializations = specializations; }
    public Set<String> getSeminaries() { return seminaries; }
    public void setSeminaries(Set<String> seminaries) {
        this.seminaries = seminaries;
    }


    //  Constructor

    public LibrarianUserDTO() {
        // Default constructor
    }

}






