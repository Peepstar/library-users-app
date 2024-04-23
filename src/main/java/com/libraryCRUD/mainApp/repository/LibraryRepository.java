package com.libraryCRUD.mainApp.repository;

import com.libraryCRUD.mainApp.entities.users.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// --> LibraryUsers repository using JPA to persist in database <-- \\
public interface LibraryRepository extends JpaRepository<LibraryUser, Long> {
    Optional<LibraryUser> findByEmail(String email);
    boolean existsByEmail(String email);

}
