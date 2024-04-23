package com.libraryCRUD.mainApp.mappers;

import com.libraryCRUD.mainApp.dto.usersdto.LibrarianUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.LibraryUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.MemberUserDTO;
import com.libraryCRUD.mainApp.entities.users.Librarian;
import com.libraryCRUD.mainApp.entities.users.LibraryUser;
import com.libraryCRUD.mainApp.entities.users.Member;
import com.libraryCRUD.mainApp.enums.Specialization;
import com.libraryCRUD.mainApp.exceptionhandling.exceptions.WrongSpecializationException;
import org.mapstruct.*;
import java.util.List;
import java.util.Set;

// --> Mapper for LibraryUsers DTOs and Entities <-- \\
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LibraryUserMapper {
    // --> MAPPINGS FOR GET METHODS <-- \\

    //  Mapping from LibraryDTO to LibraryUser Entity
    @Mapping(source = "registrationDate", target = "registrationDate", dateFormat = "yyyy-MM-dd HH-mm-ss")
    LibraryUserDTO toLibraryDTO(LibraryUser libraryUser);

    //  Mapping to GET a list of LibraryUsersDTOs from a list of LibraryUsers
    Set<LibraryUserDTO> toLibraryDTOList(List<LibraryUser> usersList);

    //  Mapping to GET a LibrarianDTO from Librarian
    @Mapping(target = "seminaries", ignore = true) //  Seminaries property is mapped from JOIN table in LibraryUserService
    LibrarianUserDTO toLibrarianDTO(Librarian librarianUser);

    //  Mapping to GET a MemberDTO from Member
    @Mapping(target = "currentSeminaries", ignore = true) //  Seminaries property is mapped from JOIN table in LibraryUserService
    MemberUserDTO toMemberDTO(Member memberUser);


    // --> MAPPINGS FOR POST METHODS <-- \\

    // Mapping to POST a LIBRARIAN from a LibraryUserDTO
    @Mapping(target = "address", source = "address", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, defaultValue = "")
    @Mapping(target = "phoneNumber", source = "phoneNumber", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, defaultValue = "")
    Librarian toEntryLibrarianFromDTO(LibraryUserDTO libraryUserDTO);

    // Mapping to POST a MEMBER from a LibraryUserDTO
    @Mapping(target = "address", source = "address", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, defaultValue = "")
    @Mapping(target = "phoneNumber", source = "phoneNumber", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, defaultValue = "")
    Member toEntryMemberFromDTO(LibraryUserDTO libraryUserDTO);


    // --> MAPPINGS FOR PUT METHODS <-- \\

    // PUT|UPDATE A LIBRARIAN from a LibrarianUserDTO
    @Mappings({
            @Mapping(target = "fullName", source = "fullName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "email", source = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "dateOfBirth", source = "dateOfBirth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "phoneNumber", source = "phoneNumber", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "address", source = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "password", source = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "userRole", source = "userRole", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "specializations", source = "newSpecialization", qualifiedByName = "mapSpecialization", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "seminaries", ignore = true)  //  Seminaries are not updated via list. Ignored to avoid parsing/mapping issues
    })
    Librarian updateLibrarianFromDTO(LibrarianUserDTO theLibrarianDTO, @MappingTarget Librarian theLibrarian);

    // PUT|UPDATE A MEMBER from a MemberUserDTO
    @Mappings({
            @Mapping(target = "fullName", source = "fullName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "email", source = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "dateOfBirth", source = "dateOfBirth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "address", source = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "phoneNumber", source = "phoneNumber", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "password", source = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "userRole", source = "userRole", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "currentSeminaries", ignore = true)
    })
    Member updateMemberFromDTO(MemberUserDTO theMemberDTO, @MappingTarget Member theMember);


    //  Qualifier method to map a new specialization into list of existing specializations for a Librarian
    @Named("mapSpecialization")
    default Set<Specialization> mapSpecialization(Specialization newSpecialization, @MappingTarget Set<Specialization> existingSpecializations) {
        // Check if newSpecialization is not null and not already present
        if (!existingSpecializations.contains(newSpecialization)) {
            existingSpecializations.add(newSpecialization);
            return existingSpecializations;
        } else{
            throw new WrongSpecializationException("Specialization " + newSpecialization + " is already part of the Librarian Specializations");
        }
    }

}
