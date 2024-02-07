package com.libraryCRUD.mainApp.Repository;

import com.libraryCRUD.mainApp.entities.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface LibraryRepository extends JpaRepository<LibraryUser, Long> {
}
