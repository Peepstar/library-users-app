package com.libraryCRUD.mainApp.services.user;

import com.libraryCRUD.mainApp.dto.usersdto.LibrarianUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.LibraryUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.MemberUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.UserDTO;
import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import com.libraryCRUD.mainApp.enums.Specialization;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.DuplicatedConstraintException;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.LibraryUserNotFoundException;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.WrongRoleException;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.WrongSpecializationException;
import com.libraryCRUD.mainApp.mappers.LibraryUserMapper;
import com.libraryCRUD.mainApp.mappers.SeminarMapper;
import com.libraryCRUD.mainApp.repository.LibraryRepository;
import com.libraryCRUD.mainApp.entities.users.Librarian;
import com.libraryCRUD.mainApp.entities.users.LibraryUser;
import com.libraryCRUD.mainApp.entities.users.Member;
import com.libraryCRUD.mainApp.enums.Role;
import com.libraryCRUD.mainApp.repository.SeminarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

// --> Service layer for business logic related to LibraryUsers <-- \\
@Service
public class LibraryServiceImpl implements LibraryService{
    //  Object fields for dependency injection
    private final LibraryRepository libraryRepository;   //  Repository to persist LibraryUsers in Database
    private final LibraryUserMapper libraryUserMapper; //  Mapper provided by MapStruct to map DTOs and LibraryUsers
    private final PasswordEncoder passwordEncoder; //  Password encoder bean to Bcrypt passwords inputted by user
    private final SeminarRepository seminarRepository;
    private final SeminarMapper seminarMapper;


    //  Constructor using Autowired to inject object fields

    @Autowired
    public LibraryServiceImpl(LibraryRepository theUserRepository, LibraryUserMapper theLibraryMapper, PasswordEncoder theEncoder,
                              SeminarRepository theSeminarRepository, SeminarMapper theSeminarMapper){
        libraryRepository = theUserRepository;
        libraryUserMapper = theLibraryMapper;
        passwordEncoder = theEncoder;
        seminarRepository = theSeminarRepository;
        seminarMapper = theSeminarMapper;
    }


    //-->  Methods for LibraryUserController <--\\


    //  Find all LibraryUsers in database
    @Override
    public Set<LibraryUserDTO> findAll() {
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
    public UserDTO saveLibraryUser(LibraryUserDTO theUserDTO) {  //Persisting new LibraryUser into database
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

    //  Delete a Specialization from Librarian's Specialization list
    @Override
    public boolean deleteSpecializationFromLibrarian(Long theUserId, String theSpecialization) {
        //  Find libraryUser by ID. Exception will be thrown if not found
        LibraryUser theLibraryUser = findById(theUserId);
        //  Check if it is a LIBRARIAN
        if (!((theLibraryUser) instanceof Librarian librarianToUpdate)) {
            throw new WrongRoleException("Library user is not a LIBRARIAN");
        }
        //  Check if String passed in is actually a Specialization enum value
        Specialization specializationToDelete;
        try {
            specializationToDelete = Specialization.valueOf(theSpecialization);
        } catch (IllegalArgumentException e) {
            throw new WrongSpecializationException("Invalid value for Specialization. Only  HISTORY, LITERATURE, SCIENCE, PHILOSOPHY, LANGUAGES are allowed");
        }
        //  Check if specialization to delete is part of Librarian specializations
        if (!librarianToUpdate.getSpecializations().contains(specializationToDelete)) {
            throw new WrongSpecializationException(String.format("Librarian doesn't have %s specialization to delete", theSpecialization));
        } else {
            //  If specialization to delete is part of Librarian's specialization, then delete specialization and assign to entity updated set of specializations
            Set<Specialization> updatedSpecializations = librarianToUpdate.getSpecializations();
            updatedSpecializations.remove(specializationToDelete);
            librarianToUpdate.setSpecializations(updatedSpecializations);
            //  All conditions have been met. Persist in database and return true
            libraryRepository.save(librarianToUpdate);
            return true;
        }
    }

    // UPDATE a LIBRARIAN
    @Override
    public UserDTO updateLibrarianUser(Long userId, LibrarianUserDTO theLibrarianDTO) {
        //  Find user by ID and make sure it already exists for update
        LibraryUser userToUpdate = findById(userId);
        //  Execute PUT if user to be updated is a LIBRARIAN, otherwise advice user that is not a LIBRARIAN
        if(!userToUpdate.getUserRole().equals(Role.LIBRARIAN)){
            throw new WrongRoleException("Library user is not a LIBRARIAN");
        }
        //If the DTO received contains a password to be updated, then hash the password for consistency in database
        theLibrarianDTO.setPassword(theLibrarianDTO.getPassword() != null ? passwordEncoder.encode(theLibrarianDTO.getPassword()) : null);
        //  If the DTO received contains an email to be updated, then check if email already exists in database
        if(theLibrarianDTO.getEmail() != null){
            catchDuplicatedEmail(theLibrarianDTO);
        }
        //  Map the received DTO to the existing Librarian in DB
        Librarian updatedLibrarian =  libraryUserMapper.updateLibrarianFromDTO(theLibrarianDTO, (Librarian) userToUpdate);
        //persist updated Librarian in database
        libraryRepository.save(updatedLibrarian);
        //Return updated LibrarianDTO
        return convertToDTO(updatedLibrarian);
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
        return convertToDTO(updatedMember);
        //return libraryUserMapper.toMemberDTO((Member) userToUpdate);
    }

    //Convert a LibraryUser to DTO to show entity's information.
    @Override
    public UserDTO convertToDTO(LibraryUser theUser){
        //
        Set<String> nameOfSeminaries = new HashSet<>();
        //If user is LIBRARIAN, create a Librarian DTO and return it.
        if(theUser instanceof Librarian){
            //  Get LibrarianUserDTO from theUser ID
            LibrarianUserDTO librarianUserDTO = libraryUserMapper.toLibrarianDTO((Librarian) theUser);
            //  Get Seminars associated to that Librarian using JOIN methods from repository
            Set<Seminar> librarianSeminaries = seminarRepository.findSeminarsByLibrarian_userId(theUser.getUserId());
            for(Seminar currSeminar : librarianSeminaries){
                nameOfSeminaries.add(currSeminar.getSeminarName());
            }
            //  Set seminaries to DTO and then return it with the retrieved data
            librarianUserDTO.setSeminaries(nameOfSeminaries);
            return librarianUserDTO;
        }else{  // Else statement will always be a MEMBER due to Database and eNum restrictions
            //  Get LibrarianUserDTO from theUser ID
            MemberUserDTO memberUserDTO = libraryUserMapper.toMemberDTO((Member) theUser);
            //  Get Seminars associated to that Member using JOIN methods from repository
            Set<Seminar> memberSeminaries = seminarRepository.findSeminarsByListOfStudents_userId(theUser.getUserId());
            for(Seminar currSeminar : memberSeminaries){
                nameOfSeminaries.add(currSeminar.getSeminarName());
            }
            memberUserDTO.setCurrentSeminaries(nameOfSeminaries);
            return memberUserDTO;
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
            throw new DuplicatedConstraintException("Email is already in use. Email can not be duplicated in database");
        }
    }

}


