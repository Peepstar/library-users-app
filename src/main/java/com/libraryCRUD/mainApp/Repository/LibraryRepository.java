package com.libraryCRUD.mainApp.Repository;

import com.libraryCRUD.mainApp.entities.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;


public interface LibraryRepository extends JpaRepository<LibraryUser, Long> {
    Optional<LibraryUser> findByEmail(String email);
}
