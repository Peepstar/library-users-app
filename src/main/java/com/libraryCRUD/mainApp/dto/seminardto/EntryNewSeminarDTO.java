package com.libraryCRUD.mainApp.dto.seminardto;

import com.libraryCRUD.mainApp.enums.Specialization;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.WrongSpecializationException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// -->  DTO to entry a new Seminar using validations <-- \\
public class EntryNewSeminarDTO {
    @Size(max = 45, message = "Seminar name can not be longer than 45 characters")
    @NotNull(message = "Seminar name can not be null")
    private String seminarName;
    @NotNull(message = "Librarian ID is required")
    private Long librarianId;
    @NotNull(message = "Specialization is required")
    private Specialization specialization;


    //--> Constructor <--\\

    public EntryNewSeminarDTO() {
    }


    //--> Getters and setters <--\\

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public Long getLibrarianId() { return librarianId; }

    //  Make sure that a number is passed in for LibrarianUserID before deserializing
    public void setLibrarianId(String librarianIdStr) throws IllegalArgumentException {
        try {
            this.librarianId = Long.parseLong(librarianIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Librarian ID must be a valid Long number");
        }
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    //  Make sure that Specialization passed in is a valid enum
    public void setSpecialization(String theSpecialization) throws WrongSpecializationException {
        try {
            this.specialization = Specialization.valueOf(theSpecialization);
        } catch (IllegalArgumentException e) {
            throw new WrongSpecializationException("Invalid value for Specialization. Only  HISTORY, LITERATURE, SCIENCE, PHILOSOPHY, LANGUAGES are allowed");
        }
    }

}
