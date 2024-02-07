package com.libraryCRUD.mainApp.test;


import com.libraryCRUD.mainApp.Repository.LibraryRepository;
import com.libraryCRUD.mainApp.Service.LibraryService;
import com.libraryCRUD.mainApp.entities.Librarian;
import com.libraryCRUD.mainApp.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class TestingDataBase {

    @Autowired
    private LibraryService libraryService;

    @Bean
    public CommandLineRunner testUsersMapperCommand(){

        return args -> {

            Librarian librarian = new Librarian(
                    "John Doe",
                    "john.doe@example.com",
                    LocalDate.of(1990, 5, 15),
                    "123 Main St",
                    "123-456-7890",
                    LocalDateTime.now(),
                    Role.LIBRARIAN,
                    "Day Shift",
                    "Reference Department"
            );

            System.out.println(librarian);


        };
    }


}
