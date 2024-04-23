package com.libraryCRUD.mainApp.dto.seminardto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libraryCRUD.mainApp.enums.Specialization;
import jakarta.validation.constraints.Size;
import java.util.Set;

// --> DTO to GET and UPDATE a Seminar <-- \\
public class SeminarDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Field only allowed to be READ but not to be POST by user
    private Long seminarId;
    @Size(max = 45, message = "Seminar name can not be longer than 45 characters")
    private String seminarName;
    private Long librarianId; //  Validations for correct input are taken care before deserialization and in service class
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Field only allowed to be READ but not to be POST by user
    private String librarianName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Field only allowed to be READ but not to be POST by user
    private String librarianEmail;
    private Specialization specialization; //  Validations for correct input are taken care before deserialization and in service class
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //  Field only allowed to be PUT | UPDATED but not to be READ by user
    private Set<Long> studentsToAdd;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Field only allowed to be READ but not to be POST by user
    private Set<String> listOfStudents;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //  Field only allowed to be READ but not to be POST by user
    private Integer totalOfStudents;



    //--> Constructor <--\\

    public SeminarDTO() {
    }


    //--> Getters and setters <--\\

    public Long getSeminarId() { return seminarId; }
    public void setSeminarId(Long seminarId) { this.seminarId = seminarId; }
    public Long getLibrarianId() { return librarianId; }
    //  Make sure that a number is passed in for LibrarianUserID before deserializing
    public void setLibrarianId(String librarianIdStr) throws IllegalArgumentException {
        try {
            this.librarianId = Long.parseLong(librarianIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Librarian ID must be a valid Long number");
        }
    }
    public String getSeminarName() { return seminarName; }
    public void setSeminarName(String seminarName) { this.seminarName = seminarName; }

    public String getLibrarianName() { return librarianName; }

    public void setLibrarianName(String librarianName) { this.librarianName = librarianName; }

    public String getLibrarianEmail() { return librarianEmail; }

    public void setLibrarianEmail(String librarianEmail) { this.librarianEmail = librarianEmail; }

    public Specialization getSpecialization() { return specialization; }

    public void setSpecialization(Specialization specialization) { this.specialization = specialization; }

    public Set<Long> getStudentsToAdd() { return studentsToAdd; }

    public void setStudentsToAdd(Set<Long> studentsToAdd) { this.studentsToAdd = studentsToAdd; }

    public Set<String> getListOfStudents() { return listOfStudents; }

    public void setListOfStudents(Set<String> listOfStudents) { this.listOfStudents = listOfStudents; }

    public Integer getTotalOfStudents() { return totalOfStudents; }

    public void setTotalOfStudents(Integer totalOfStudents) { this.totalOfStudents = totalOfStudents; }

}
