package com.libraryCRUD.mainApp.restcontrollers.usercontroller;

import com.libraryCRUD.mainApp.dto.usersdto.LibrarianUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.LibraryUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.MemberUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.UserDTO;
import com.libraryCRUD.mainApp.services.user.LibraryService;
import com.libraryCRUD.mainApp.entities.users.LibraryUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

// --> RestController to access LibraryUsers end points <-- \\
@RestController
public class LibraryUserController {
    //  Create a libraryService private field to manage logic and use it for autowired injection
    private final LibraryService libraryService;
    @Autowired
    public LibraryUserController(LibraryService theLibraryService){
        libraryService = theLibraryService;
    }


    // --> END POINTS <-- \\

    @GetMapping("/libraryusers")  //  GET a list of USERS
    public Set<LibraryUserDTO> findAll(){
        return libraryService.findAll();
    }

    @GetMapping("/libraryusers/{userId}")  // GET a single USER
    public UserDTO getEmployee(@PathVariable Long userId) {
        LibraryUser theLibraryUser = libraryService.findById(userId);
        return libraryService.convertToDTO(theLibraryUser);
    }

    @PostMapping("/libraryusers") //  POST a new user
    public UserDTO addLibraryUser(@Valid @RequestBody LibraryUserDTO newLibraryUserDTO) {
        //We save DTO sent from client to database and return a DTO mapped from new created entity
        return libraryService.saveLibraryUser(newLibraryUserDTO);
    }

    @PutMapping("/libraryusers/librarian/{userId}") //  PUT an existing LIBRARIAN || PENDING TO CHANGE return type
    public UserDTO updateLibrarianUser(@PathVariable Long userId, @Valid @RequestBody LibrarianUserDTO theLibrarianDTO) {
        return libraryService.updateLibrarianUser(userId, theLibrarianDTO);
    }

    @PutMapping("/libraryusers/member/{userId}") //  PUT an existing MEMBER
    public UserDTO updateMemberUser(@PathVariable Long userId, @Valid @RequestBody MemberUserDTO theMemberDTO){
        return libraryService.updateMemberUser(userId, theMemberDTO);
    }

    @DeleteMapping("/libraryusers/{userId}") //  DELETE an existing USER
    public String deleteUser(@PathVariable Long userId){
        if(libraryService.deleteById(userId)){
            return String.format("User with id %d was successfully deleted from database", userId);
        } else {
            // In case that any other exception different from "UserID not found" is thrown.
            return String.format("User with id %d was not deleted", userId);
        }
    }

    @PutMapping("/libraryusers/librarian/deleteSpecialization/{userId}") //  DELETE a Specialization from a LIBRARIAN
    public String deleteSpecialization(@PathVariable Long userId, @RequestBody String specialization){
        if(libraryService.deleteSpecializationFromLibrarian(userId, specialization)){
            return String.format("Specialization %s was successfully deleted from Librarian with id -> %d", specialization, userId);
        } else {
            //  In case that any other exception is thrown when trying to delete Specialization.
            return String.format("No changes were made to Librarian with id -> %d", userId);
        }
    }

}
