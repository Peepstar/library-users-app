package com.libraryCRUD.mainApp.Mapper;

import com.libraryCRUD.mainApp.DTOs.LibrarianUserDTO;
import com.libraryCRUD.mainApp.DTOs.LibraryUserDTO;
import com.libraryCRUD.mainApp.DTOs.MemberUserDTO;
import com.libraryCRUD.mainApp.entities.Librarian;
import com.libraryCRUD.mainApp.entities.LibraryUser;
import com.libraryCRUD.mainApp.entities.Member;
import org.mapstruct.*;
import java.util.List;

// --> Mapper for DTOs and Entities <-- \\
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LibraryUserMapper {
    // --> MAPPINGS FOR GET METHODS <-- \\

    //  Mapping to GET a LibraryUserDTO from LibraryUser
    @Mappings( {
            @Mapping(source = "registrationDate", target = "registrationDate", dateFormat = "yyyy-MM-dd HH-mm-ss")
    })
    LibraryUserDTO toLibraryDTO(LibraryUser libraryUser);

    //Mapping to GET a list of LibraryUsersDTOs from a list of LibraryUsers
    List<LibraryUserDTO> toLibraryDTOList(List<LibraryUser> usersList);

    //Mapping to GET a LibrarianDTO from Librarian
    LibrarianUserDTO toLibrarianDTO(Librarian librarianUser);

    //Mapping to GET a MemberDTO from Member
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
            @Mapping(target = "workShift", source = "workShift", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "department", source = "department", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "password", source = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    })
    Librarian updateLibrarianFromDTO(LibrarianUserDTO theLibrarianDTO, @MappingTarget Librarian theLibrarian);

    // PUT|UPDATE A MEMBER from a MemberUserDTO
    @Mappings({
            @Mapping(target = "fullName", source = "fullName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "email", source = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "dateOfBirth", source = "dateOfBirth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "address", source = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "phoneNumber", source = "phoneNumber", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "currentBooks", source = "currentBooks", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "membershipActive", source = "membershipActive", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "password", source = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    })
    Member updateMemberFromDTO(MemberUserDTO theMemberDTO, @MappingTarget Member theMember);

}
