package com.libraryCRUD.mainApp.RestController;


import com.libraryCRUD.mainApp.DTOs.LibrarianUserDTO;
import com.libraryCRUD.mainApp.DTOs.LibraryUserDTO;
import com.libraryCRUD.mainApp.DTOs.MemberUserDTO;
import com.libraryCRUD.mainApp.DTOs.UserDTO;
import com.libraryCRUD.mainApp.Service.LibraryService;
import com.libraryCRUD.mainApp.entities.LibraryUser;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibraryUserController {

    //Create a libraryService private field for autowired injection
    private final LibraryService libraryService;

    //Inject LibraryService object into private field
    @Autowired
    public LibraryUserController(LibraryService theLibraryService){
        libraryService = theLibraryService;
    }

    @GetMapping("/libraryusers")
    public List<LibraryUserDTO> findAll(){
        return libraryService.findAll();
    }

    @GetMapping("/libraryusers/{userId}")
    public UserDTO getEmployee(@PathVariable Long userId) {
        LibraryUser theLibraryUser = libraryService.findById(userId);

        return libraryService.convertToDTO(theLibraryUser);

    }

    @PostMapping("/libraryusers")
    public LibraryUserDTO addLibraryUser(@Valid @RequestBody LibraryUserDTO newLibraryUserDTO){
        //We save DTO sent from client to DB and return a DTO mapped from new entity
        return libraryService.saveLibraryUser(newLibraryUserDTO);
    }

    @PutMapping("/libraryusers/librarian/{userId}")
    public LibrarianUserDTO updateLibrarianUser(@PathVariable Long userId, @Valid @RequestBody LibrarianUserDTO theLibrarianDTO) {
        return libraryService.updateLibrarianUser(userId, theLibrarianDTO);
    }

    @PutMapping("/libraryusers/member/{userId}")
    public MemberUserDTO updateMemberUser(@PathVariable Long userId, @Valid @RequestBody MemberUserDTO theMemberDTO){
        return libraryService.updateMemberUser(userId, theMemberDTO);
    }

    @DeleteMapping("libraryusers/{userId}")
    public String deleteUser(@PathVariable Long userId){
        if(libraryService.deleteById(userId)){
            return String.format("User with id %d was successfully deleted from database", userId);
        }
        return String.format("User with id %d was not deleted", userId);
    }


}
