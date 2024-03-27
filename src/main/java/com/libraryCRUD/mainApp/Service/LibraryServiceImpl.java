package com.libraryCRUD.mainApp.Service;

import com.libraryCRUD.mainApp.DTOs.LibrarianUserDTO;
import com.libraryCRUD.mainApp.DTOs.LibraryUserDTO;
import com.libraryCRUD.mainApp.DTOs.MemberUserDTO;
import com.libraryCRUD.mainApp.DTOs.UserDTO;
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

@Service
public class LibraryServiceImpl implements LibraryService{

    //Repository field for dependency injection
    private final LibraryRepository libraryRepository;
    //Mapper field for dependency injection
    private final LibraryUserMapper libraryUserMapper;
    private final PasswordEncoder passwordEncoder;


    //Constructor using a libraryUserRepository for injection
    @Autowired
    public LibraryServiceImpl(LibraryRepository theUser, LibraryUserMapper theMapper, PasswordEncoder theEncoder){
        libraryRepository = theUser;
        libraryUserMapper = theMapper;
        passwordEncoder = theEncoder;
    }


    //Methods for business logic
    @Override
    public List<LibraryUserDTO> findAll() {
        List<LibraryUser> theUsersList = libraryRepository.findAll();
        return libraryUserMapper.toLibraryDTOList(theUsersList);

    }

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

    @Override
    public UserDTO saveLibraryUser(LibraryUserDTO theUserDTO) { // //Persisting new LibraryUser into database
        //Hash the password before saving to keep consistency.
        String hashedPassword = passwordEncoder.encode(theUserDTO.getPassword());
        theUserDTO.setPassword(hashedPassword);
        //Convert LibraryUserDTO to either MEMBER or LIBRARIAN to persist in database
        LibraryUser userEntity = convertToUser(theUserDTO);
        //Persist to database and return LibraryDTO
        libraryRepository.save(userEntity);
        return libraryUserMapper.toLibraryDTO(userEntity);
    }

    @Override
    public boolean deleteById(Long theId) {
        //Search for ID in database, if not ID found, an exception will be thrown
        LibraryUser userToDelete = findById(theId);
        //LibraryUser found, delete it and return true
        libraryRepository.delete(userToDelete);
        return true;
    }

    @Override
    public UserDTO updateLibrarianUser(Long userId, LibrarianUserDTO theLibrarianDTO) {

        LibraryUser userToUpdate = findById(userId);
        //Execute PUT if user to be updated is a LIBRARIAN, otherwise advice user that is not a LIBRARIAN
        if(!userToUpdate.getUserRole().equals(Role.LIBRARIAN)){
            throw new WrongRoleException("Library user is not a LIBRARIAN");
        }
        //If the DTO received contains a password to be updated, then hash the password for consistency in database
        theLibrarianDTO.setPassword(theLibrarianDTO.getPassword() != null ? passwordEncoder.encode(theLibrarianDTO.getPassword()) : null);

        //Map the received DTO to the existing Librarian in DB
        Librarian updatedLibrarian =  libraryUserMapper.updateLibrarianFromDTO(theLibrarianDTO, (Librarian) userToUpdate);
        //persist updated Librarian in database
        libraryRepository.save(updatedLibrarian);
        //Return updated LibrarianDTO
        return libraryUserMapper.toLibrarianDTO((Librarian) userToUpdate);

    }

    @Override
    public UserDTO updateMemberUser(Long userId, MemberUserDTO theMemberDTO) {
        LibraryUser userToUpdate = findById(userId);
        //Execute PUT if user to be updated is a MEMBER, otherwise advice user that is not a MEMBER
        if(!userToUpdate.getUserRole().equals(Role.MEMBER)){
            throw new WrongRoleException("Library user is not a MEMBER");
        }
        //If the DTO received contains a password to be updated, then hash the password for consistency in database
        theMemberDTO.setPassword(theMemberDTO.getPassword() != null ? passwordEncoder.encode(theMemberDTO.getPassword()) : null);

        //Map the received DTO to Librarian to persist in database
        // Using casting since repository provides LibraryUser(abstract) instead of a Librarian class.
        Member updatedMember =  libraryUserMapper.updateMemberFromDTO(theMemberDTO, (Member) userToUpdate);
        libraryRepository.save(updatedMember);
        //Return updated MemberDTO
        return libraryUserMapper.toMemberDTO((Member) userToUpdate);
    }


    //Helper methods

    //Convert a LibraryUser to DTO to show entity's information.
    @Override
    public UserDTO convertToDTO(LibraryUser theUser){

        if(theUser instanceof Librarian){ //If user is Librarian, create a Librarian DTO
            return libraryUserMapper.toLibrarianDTO((Librarian) theUser);
        }else if (theUser instanceof Member){ //If user is Member, create a Member DTO
            return libraryUserMapper.toMemberDTO((Member) theUser);
        }else {
            // Handle other cases or throw an exception if needed
            throw new IllegalArgumentException("Unsupported user type");
        }

    }

    //Conver a DTO into a LibraryUser to persist in database.
    @Override
     public LibraryUser convertToUser(LibraryUserDTO theUserDTO){
        //Getting role and then check if it is a librarian or member to use correct mapping
        Role userRole =  theUserDTO.getUserRole();
        //Librarian found
        if(Role.LIBRARIAN.equals(userRole)){
            return libraryUserMapper.toEntryLibrarianFromDTO(theUserDTO);
            //Member found
        } else if (Role.MEMBER.equals(userRole)){
            return libraryUserMapper.toEntryMemberFromDTO(theUserDTO);
        }
        //incorrect data so we trow an exception
        throw new WrongRoleException("Invalid userRole");
    }


}


