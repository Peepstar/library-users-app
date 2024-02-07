package com.libraryCRUD.mainApp;

import com.libraryCRUD.mainApp.Repository.LibraryRepository;
import com.libraryCRUD.mainApp.RestController.LibraryUserController;
import com.libraryCRUD.mainApp.Service.LibraryService;
import com.libraryCRUD.mainApp.Service.LibraryServiceImpl;
import com.libraryCRUD.mainApp.entities.Librarian;
import com.libraryCRUD.mainApp.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class MainAppApplication  {

	public static void main(String[] args) { SpringApplication.run(MainAppApplication.class, args); }


}







