package com.libraryCRUD.mainApp.repository;

import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

// --> Seminar repository using JPA to persist in database <-- \\
public interface SeminarRepository extends JpaRepository<Seminar, Long> {
    //  Find all Seminaries associated to a Librarian
    Set<Seminar> findSeminarsByLibrarian_userId(Long librarianId);
    //  Find all Seminaries associated to a Member
    Set<Seminar> findSeminarsByListOfStudents_userId(Long memberId);
    //  Find if a Seminar already exists in database
    boolean existsBySeminarName(String seminarName);

}




