package com.libraryCRUD.mainApp.entities.seminar;

import com.libraryCRUD.mainApp.entities.users.Librarian;
import com.libraryCRUD.mainApp.entities.users.Member;
import com.libraryCRUD.mainApp.enums.Specialization;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "seminar")
public class Seminar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seminar_id")
    private Long seminarId;
    @Column(name = "seminar_name", columnDefinition = "VARCHAR(45) NOT NULL UNIQUE")
    private String seminarName;
    @Column(name = "seminar_specialization")
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    @ManyToOne
    @JoinColumn(name = "librarian_id", nullable = false)
    private Librarian librarian;
    @Column(name = "list_of_students")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "seminar_students", // Create a join table
            joinColumns = @JoinColumn(name = "seminar_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Member> listOfStudents;


    // Constructor

    public Seminar() {
    }


    //  Getters and Setters

    public Long getSeminarId() {
        return seminarId;
    }

    public void setSeminarId(Long seminarId) {
        this.seminarId = seminarId;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public Set<Member> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(Set<Member> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

}
