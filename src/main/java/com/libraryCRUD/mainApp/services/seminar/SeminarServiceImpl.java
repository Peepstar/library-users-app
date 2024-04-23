package com.libraryCRUD.mainApp.services.seminar;

import com.libraryCRUD.mainApp.dto.seminardto.EntryNewSeminarDTO;
import com.libraryCRUD.mainApp.dto.seminardto.SeminarDTO;
import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import com.libraryCRUD.mainApp.entities.users.Librarian;
import com.libraryCRUD.mainApp.entities.users.LibraryUser;
import com.libraryCRUD.mainApp.entities.users.Member;
import com.libraryCRUD.mainApp.enums.Specialization;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.DuplicatedConstraintException;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.LibraryUserNotFoundException;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.WrongRoleException;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.WrongSpecializationException;
import com.libraryCRUD.mainApp.mappers.SeminarMapper;
import com.libraryCRUD.mainApp.repository.SeminarRepository;
import com.libraryCRUD.mainApp.services.user.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// --> Service layer for business logic related to LibraryUsers <-- \\
@Service
public class SeminarServiceImpl implements  SeminarService {
    //  Object fields to dependency injection
    private final SeminarRepository seminarRepository;
    private final SeminarMapper seminarMapper;
    private final LibraryService libraryService;


    //  Constructor using Autowired to inject object fields

    @Autowired
    public SeminarServiceImpl(SeminarRepository theRepository, SeminarMapper theMapper, LibraryService theLibraryService){

        this.seminarRepository = theRepository;
        this.seminarMapper = theMapper;
        this.libraryService = theLibraryService;
    }


    //-->  Methods for SeminarController <--\\

    //  Find all Seminaries in database
    @Override
    public List<SeminarDTO> findAll() {
        //  Find all seminars in database and map them to DTO
         List<Seminar> theSeminarList = seminarRepository.findAll();
         return seminarMapper.toListDTO(theSeminarList);
    }

    //  Find a Seminar by ID
    @Override
    public Seminar findById(Long theId) {
        Optional<Seminar> seminarResult = seminarRepository.findById(theId);
        //Create an instance of library user to check if result is present
        Seminar theSeminar;
        //Check if result is present, if so, assign value to theLibraryUser variable
        if(seminarResult.isPresent()){
            theSeminar = seminarResult.get();
        }else{
            //We didn't find any libraryUser
            throw new LibraryUserNotFoundException("Seminar ID was not found --> " + theId);
        }
        return theSeminar;
    }

    //  Save a new Seminar in database
    @Override
    public SeminarDTO saveSeminar(EntryNewSeminarDTO theNewSeminarDTO){
        //  Find LibraryUser by ID. Exception for not found LibraryUser is taken care of by service class
        LibraryUser tempUser = libraryService.findById(theNewSeminarDTO.getLibrarianId());
        //  Check if LibraryUser is valid to be assigned to Seminar. Must be a LIBRARIAN and have specialization required by seminar in their Specializations list
        Librarian librarianForSeminar = checkIfLibrarianIsValid(theNewSeminarDTO.getLibrarianId(), theNewSeminarDTO.getSpecialization());
        //  Check if Seminar Name isn't duplicated in database
        catchDuplicatedSeminar(theNewSeminarDTO);
        //  Librarian found. Convert DTO to Seminar
        Seminar theNewSeminar = seminarMapper.toEntryNewSeminar(theNewSeminarDTO);
        //  Librarian is not set in mapping, so we set up Librarian in Seminar entity before persisting
        theNewSeminar.setLibrarian(librarianForSeminar);
        //  Persist new Seminar in database
        seminarRepository.save(theNewSeminar);
        //  Map new entry to DTO
        return convertToDTO(theNewSeminar);
    }

    //  Update a seminar in database based in a passed in DTO
    @Override
    public SeminarDTO updateSeminar(Long seminarId, SeminarDTO seminarDTO){
        //  Find Seminar by ID
        Seminar seminarToUpdate = findById(seminarId);
        //  If the DTO received contains a specialization to be updated, throw an exception since it is not allowed
        if (seminarDTO.getSpecialization() != null) {
            throw new WrongSpecializationException("Specialization can not be changed. Consider adding a new Seminar");
        }
        //  If the DTO received contains a name to be updated, then check if name already exists in database
        if(seminarDTO.getSeminarName() != null){
            catchDuplicatedSeminar(seminarDTO);
        }
        //  Map the received DTO to the existing Seminar in DB
        Seminar updatedSeminar =  seminarMapper.updateSeminarFromDTO(seminarDTO,  seminarToUpdate);
        //  If the DTO received contains a librarian ID to be updated, then check if new librarianID is valid and assign it to updatedSeminar
        if(seminarDTO.getLibrarianId() != null){
            Librarian newLibrarianForSeminar = checkIfLibrarianIsValid(seminarDTO.getLibrarianId(), updatedSeminar.getSpecialization());
            updatedSeminar.setLibrarian(newLibrarianForSeminar);
        }
        //  Check if DTO received students to be added to Seminar. If so, apply logic to convert Ids into a Set<Member>
        if(seminarDTO.getStudentsToAdd() != null) {
            //  Get current list of students and assign it to HashSet that will be persisted in database after verifications. Set won't allow duplications
            Set<Member> newStudentsSet = seminarToUpdate.getListOfStudents();
            //  Get Members to persist in Seminar and check if all the ids provided are MEMBER. If not, throw exception
            for (Long memberId : seminarDTO.getStudentsToAdd()) {
                //  Find LibraryUser by ID. If not found, exception will be handled
                LibraryUser memberToAdd = libraryService.findById(memberId);
                //  Check if LibraryUser is a MEMBER. Otherwise, throw an exception
                if (!((memberToAdd) instanceof Member)) {
                    throw new WrongRoleException("Student to add is not a MEMBER. Only Members can be students of a Seminar");
                }
                //  If LibraryUser is a Member, add it to a newStudentsSet.
                newStudentsSet.add((Member) memberToAdd);
            }
            //  Add students to Seminar entity
            updatedSeminar.setListOfStudents(newStudentsSet);
        }
        //  Persist updated Seminar in database
        seminarRepository.save(updatedSeminar);
        //  Return updated SeminarDTO
        return convertToDTO(updatedSeminar);
    }

    //  Delete a Seminar by ID
    @Override
    public boolean deleteById(Long seminarId){
        //  Find Seminar in database. Throw exception if not exists
        Seminar seminarToDelete = findById(seminarId);
        //  Delete Seminar from database and return true
        seminarRepository.delete(seminarToDelete);
        return true;
    }

    //  Delete students from a Seminar based on a list of students passed in
    @Override
    public boolean deleteStudentsByIdFromSeminar(Long seminarId, List<Long> studentsToDelete){
        //  Find seminar in database. Throw exception if not exists
        Seminar seminarToDeleteMembers = findById(seminarId);
        //  If seminar is found, get a list of current students and also creates a list for its ids
        Set<Member> studentsOfSeminar = seminarToDeleteMembers.getListOfStudents();
        //  Find Members to delete. If at least member is not found in database, throw exception and not make any change in database
        for(Long currMemberId : studentsToDelete){
            //  Get Member from ID provided. Exception will be thrown if it is not found
            LibraryUser currUser = libraryService.findById(currMemberId);
            //  Check if currUser is a Member. If not, throw an exception
            if(!((currUser) instanceof Member memberToDelete)){
                throw new WrongRoleException("User id -> " + currMemberId + " is not a Member. Only Member can be deleted from Student list.");
            }
            // If currUser is a Member then check if it is part of the list of students. If not, return exception
            if(!studentsOfSeminar.contains(memberToDelete)){
                throw new LibraryUserNotFoundException(String.format("Member %s with Id %d is not part of the Seminar list of students",
                        memberToDelete.getFullName(), currMemberId));
            } else {
                // If Member is part of the students list of Seminar, then remove it from list to then persist the updated table
                studentsOfSeminar.remove(memberToDelete);
            }

        }
        //  All verifications have been passed. Set new Set of students to Seminar entity and persist in database
        seminarToDeleteMembers.setListOfStudents(studentsOfSeminar);
        seminarRepository.save(seminarToDeleteMembers);
        //  Return true for successful operation
        return true;
    }

    //  Convert from a Seminar entity to SeminarDTO
    @Override
    public SeminarDTO convertToDTO(Seminar theSeminar) {
        return seminarMapper.toDto(theSeminar);
    }


    //--> Helper methods <--\\

    //  Catch if there is a duplicated seminar name passed in on a DTO
    private void catchDuplicatedSeminar(SeminarDTO theSeminarDTO){
        // Get email from DTO
        String seminarName = theSeminarDTO.getSeminarName();
        // If email is duplicated, throw a DuplicatedEmailException
        if(seminarRepository.existsBySeminarName(seminarName)){
            throw new DuplicatedConstraintException("Seminar is already in database. Use a different Seminar name");
        }
    }

    //  Catch if is there already a Seminar with SeminarName passed in from new entry DTO
    private void catchDuplicatedSeminar(EntryNewSeminarDTO theSeminarDTO){
        // Get email from DTO
        String seminarName = theSeminarDTO.getSeminarName();
        // If email is duplicated, throw a DuplicatedEmailException
        if(seminarRepository.existsBySeminarName(seminarName)){
            throw new DuplicatedConstraintException("Seminar is already in database. Use a different Seminar name");
        }
    }

    //  Check if LibraryUser is a Librarian and if it has Specialization required for Seminar
    private Librarian checkIfLibrarianIsValid(Long theLibraryUserId, Specialization theDTOSpecialization){
        LibraryUser theLibraryUser = libraryService.findById(theLibraryUserId);
        if(!(theLibraryUser instanceof Librarian librarianForSeminar)){ //  Check if LibraryUser retrieved by ID is a LIBRARIAN. If not, return exception
            throw new WrongRoleException("LibraryUser is not LIBRARIAN. Only one Librarian can lead the Seminar");
        }
        if(!librarianForSeminar.getSpecializations().contains(theDTOSpecialization)){ //  Check if Librarian posses the specialization required by Seminar
            throw new WrongSpecializationException("Librarian doesn't have the required specialization for this Seminar. Update Librarian's specializations");
        }
        //  If the Librarian is valid, then just return it without throwing exceptions
        return librarianForSeminar;
    }

}
