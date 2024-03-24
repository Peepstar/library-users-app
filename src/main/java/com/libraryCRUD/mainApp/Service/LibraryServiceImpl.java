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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService{

    //Repository field for dependency injection
    private final LibraryRepository libraryRepository;
    //Mapper field for dependency injection
    private final LibraryUserMapper libraryUserMapper;


    //Constructor using a libraryUserRepository for injection
    @Autowired
    public LibraryServiceImpl(LibraryRepository theUser, LibraryUserMapper theMapper){
        libraryRepository = theUser;
        libraryUserMapper = theMapper;
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
    public LibraryUserDTO saveLibraryUser(LibraryUserDTO theUserDTO) { // //Persisting new LibraryUser into database
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


    //Convert a libraryUser to DTO to show information
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

    @Override
    public LibrarianUserDTO updateLibrarianUser(Long userId, LibrarianUserDTO theLibrarianDTO) {

        LibraryUser updatedLibrarian = findById(userId);
        //Execute PUT if user to be updated is a LIBRARIAN, otherwise advice user that is not a LIBRARIAN
        if(!updatedLibrarian.getUserRole().equals(Role.LIBRARIAN)){
            throw new WrongRoleException("Library user is not a LIBRARIAN");
        }
        //Map received DTO to Librarian to persist in database
        updatedLibrarian =  libraryUserMapper.updateLibrarianFromDTO(theLibrarianDTO, (Librarian) updatedLibrarian);
        libraryRepository.save(updatedLibrarian);
        //Return updated LibrarianDTO
        return libraryUserMapper.toLibrarianDTO((Librarian) updatedLibrarian);

    }

    @Override
    public MemberUserDTO updateMemberUser(Long userId, MemberUserDTO theMemberDTO) {
        LibraryUser updatedMember = findById(userId);
        //Execute PUT if user to be updated is a MEMBER, otherwise advice user that is not a MEMBER
        if(!updatedMember.getUserRole().equals(Role.MEMBER)){
            throw new WrongRoleException("Library user is not a MEMBER");
        }
        //Map received DTO to MEMBER to persist in database
        updatedMember =  libraryUserMapper.updateMemberFromDTO(theMemberDTO, (Member)updatedMember);
        libraryRepository.save(updatedMember);
        //Return updated MemberDTO
        return libraryUserMapper.toMemberDTO((Member) updatedMember);
    }




}


