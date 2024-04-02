package com.libraryCRUD.mainApp.Service;

import com.libraryCRUD.mainApp.DTOs.LibrarianUserDTO;
import com.libraryCRUD.mainApp.DTOs.LibraryUserDTO;
import com.libraryCRUD.mainApp.DTOs.MemberUserDTO;
import com.libraryCRUD.mainApp.DTOs.UserDTO;
import com.libraryCRUD.mainApp.ExceptionHandling.DuplicatedEmailException;
import com.libraryCRUD.mainApp.ExceptionHandling.LibraryUserNotFoundException;
import com.libraryCRUD.mainApp.ExceptionHandling.WrongRoleException;
import com.libraryCRUD.mainApp.Mapper.LibraryUserMapper;
import com.libraryCRUD.mainApp.Repository.LibraryRepository;
import com.libraryCRUD.mainApp.entities.Librarian;
import com.libraryCRUD.mainApp.entities.LibraryUser;
import com.libraryCRUD.mainApp.entities.Member;
import com.libraryCRUD.mainApp.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// --> Service layer for business logic <-- \\
@Service
public class LibraryServiceImpl implements LibraryService{
    //  Object fields for dependency injection
    private final LibraryRepository libraryRepository;   //  Repository to persist LibraryUsers in Database
    private final LibraryUserMapper libraryUserMapper; //  Mapper provided by MapStruct to map DTOs and LibraryUsers
    private final PasswordEncoder passwordEncoder; //  Password encoder bean to Bcrypt passwords inputted by user


    //  Constructor using Autowired to inject object fields

    @Autowired
    public LibraryServiceImpl(LibraryRepository theUser, LibraryUserMapper theMapper, PasswordEncoder theEncoder){
        libraryRepository = theUser;
        libraryUserMapper = theMapper;
        passwordEncoder = theEncoder;
    }


    //  Methods for LibraryUserController

    //  Find all LibraryUsers in database
    @Override
    public List<LibraryUserDTO> findAll() {
        List<LibraryUser> theUsersList = libraryRepository.findAll();
        return libraryUserMapper.toLibraryDTOList(theUsersList);
    }

    // Find a LibraryUser by ID
    @Override
    public LibraryUser findById(Long theId) {
        Optional<LibraryUser> userResult = libraryRepository.findById(theId);
        //Create an instance of library user to check if result is present
        LibraryUser theLibraryUser;
        //Check if result is present, if so, assign value to theLibraryUser variable
        if(userResult.isPresent()){
            theLibraryUser = userResult.get();
        }else{
            //We didn't find any libraryUser
            throw new LibraryUserNotFoundException("Library user was not found --> " + theId);
        }
        return theLibraryUser;
    }

    // Save a new LibraryUser
    @Override
    public UserDTO saveLibraryUser(LibraryUserDTO theUserDTO) { // //Persisting new LibraryUser into database
        //Hash the password before saving to keep consistency.
        String hashedPassword = passwordEncoder.encode(theUserDTO.getPassword());
        theUserDTO.setPassword(hashedPassword);
        catchDuplicatedEmail(theUserDTO);
        // Convert LibraryUserDTO to either MEMBER or LIBRARIAN to persist in database
        LibraryUser userEntity = convertToUser(theUserDTO);
        // Persist to database and return LibraryDTO
        libraryRepository.save(userEntity);
        return libraryUserMapper.toLibraryDTO(userEntity);
    }

    // Delete a LibraryUser by ID
    @Override
    public boolean deleteById(Long theId) {
        //Search for ID in database, if not ID found, an exception will be thrown
        LibraryUser userToDelete = findById(theId);
        //LibraryUser found, delete it and return true
        libraryRepository.delete(userToDelete);
        return true;
    }

    // Updated a LIBRARIAN
    @Override
    public UserDTO updateLibrarianUser(Long userId, LibrarianUserDTO theLibrarianDTO) {
        // Find user by ID and make sure it already exists for update
        LibraryUser userToUpdate = findById(userId);
        //Execute PUT if user to be updated is a LIBRARIAN, otherwise advice user that is not a LIBRARIAN
        if(!userToUpdate.getUserRole().equals(Role.LIBRARIAN)){
            throw new WrongRoleException("Library user is not a LIBRARIAN");
        }
        //If the DTO received contains a password to be updated, then hash the password for consistency in database
        theLibrarianDTO.setPassword(theLibrarianDTO.getPassword() != null ? passwordEncoder.encode(theLibrarianDTO.getPassword()) : null);
        // If the DTO received contains an email to be updated, then check if email already exists in database
        if(theLibrarianDTO.getEmail() != null){
            catchDuplicatedEmail(theLibrarianDTO);
        }
        //Map the received DTO to the existing Librarian in DB
        Librarian updatedLibrarian =  libraryUserMapper.updateLibrarianFromDTO(theLibrarianDTO, (Librarian) userToUpdate);
        //persist updated Librarian in database
        libraryRepository.save(updatedLibrarian);
        //Return updated LibrarianDTO
        return libraryUserMapper.toLibrarianDTO((Librarian) userToUpdate);
    }

    // UPDATE a MEMBER
    @Override
    public UserDTO updateMemberUser(Long userId, MemberUserDTO theMemberDTO) {
        // Find user by ID and make sure it already exists for update
        LibraryUser userToUpdate = findById(userId);
        //Execute PUT if user to be updated is a MEMBER, otherwise advice user that is not a MEMBER
        if(!userToUpdate.getUserRole().equals(Role.MEMBER)){
            throw new WrongRoleException("Library user is not a MEMBER");
        }
        // If the DTO received contains a password to be updated, then hash the password for consistency in database
        theMemberDTO.setPassword(theMemberDTO.getPassword() != null ? passwordEncoder.encode(theMemberDTO.getPassword()) : null);
        // If the DTO received contains an email to be updated, then check if email already exists in database
        if(theMemberDTO.getEmail() != null){
            catchDuplicatedEmail(theMemberDTO);
        }
        // Map the received DTO to Librarian to persist in database
        Member updatedMember =  libraryUserMapper.updateMemberFromDTO(theMemberDTO, (Member) userToUpdate);
        libraryRepository.save(updatedMember);
        // Return updated MemberDTO
        return libraryUserMapper.toMemberDTO((Member) userToUpdate);
    }

    //Convert a LibraryUser to DTO to show entity's information.
    @Override
    public UserDTO convertToDTO(LibraryUser theUser){
        //If user is LIBRARIAN, create a Librarian DTO and return it.
        if(theUser instanceof Librarian){
            return libraryUserMapper.toLibrarianDTO((Librarian) theUser);
        }else{// else statement will always be a MEMBER due to Database and eNum restrictions. Create a Member DTO and return it.
            return libraryUserMapper.toMemberDTO((Member) theUser);
        }
        // NOTE: Cast is needed because Mapper needs specific fields from LIBRARIAN or MEMBER.
    }


    //Helper methods


    // Convert a DTO into a LibraryUser to persist in database.
     private LibraryUser convertToUser(LibraryUserDTO theUserDTO){
        //Getting Role and then check if it is a LIBRARIAN or MEMBER to use correct mapping.
        Role userRole =  theUserDTO.getUserRole();
        //LIBRARIAN found. Map a LIBRARIAN from LibraryUserDTO and return it.
        if(Role.LIBRARIAN.equals(userRole)){
            return libraryUserMapper.toEntryLibrarianFromDTO(theUserDTO);
        } else { // If LIBRARIAN is not found, then this else ALWAYS will be a MEMBER due to Database & DTO restrictions and exception handling.
            return libraryUserMapper.toEntryMemberFromDTO(theUserDTO);
        }
    }

    // Try an email provided by DTO to catch a DuplicatedEmailException to protect Database rules
    private void catchDuplicatedEmail(UserDTO theUserDTO)  {
        // Get email from DTO
        String userEmail = theUserDTO.getEmail();
        // If email is duplicated, throw a DuplicatedEmailException
        if(libraryRepository.existsByEmail(userEmail)){
            throw new DuplicatedEmailException("Email is already in use. Email can not be duplicated in database");
        }
    }

}


