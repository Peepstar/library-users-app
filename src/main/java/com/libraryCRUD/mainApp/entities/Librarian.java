package com.libraryCRUD.mainApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends LibraryUser {

    @Column(name = "work_shift")
    private String workShift;
    ///PENDING FOR CHANGES
    @Column(name = "department")
    private String department;

    public String getWorkShift() {
        return workShift;
    }

    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public Librarian(String fullName, String email, LocalDate dateOfBirth, String address, String phoneNumber, LocalDateTime registrationDate, Role role, String workShift, String department) {
        super(fullName, email, dateOfBirth, address, phoneNumber, registrationDate, role);
        this.workShift = workShift;
        this.department = department;
    }

    public Librarian() {
        this.workShift = "DefaultWorkShift"; // Set default value for workShift
        this.department = "DefaultDepartment"; // Set default value for department
    }



}


