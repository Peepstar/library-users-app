package com.libraryCRUD.mainApp.Mapper;

import com.libraryCRUD.mainApp.DTOs.LibrarianUserDTO;
import com.libraryCRUD.mainApp.DTOs.LibraryUserDTO;
import com.libraryCRUD.mainApp.DTOs.MemberUserDTO;
import com.libraryCRUD.mainApp.entities.Librarian;
import com.libraryCRUD.mainApp.entities.LibraryUser;
import com.libraryCRUD.mainApp.entities.Member;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LibraryUserMapper {

    //Mapping general LibraryUser to LibraryDTO
    @Mappings( {
            @Mapping(source = "registrationDate", target = "registrationDate", dateFormat = "yyyy-MM-dd HH-mm-ss")
    })
    LibraryUserDTO toLibraryDTO(LibraryUser libraryUser);


    //Returning a list of LibraryUsersDTOd
    List<LibraryUserDTO> toLibraryDTOList(List<LibraryUser> usersList);

    //DTO mapping for entry data

    Librarian toEntryLibrarianFromDTO(LibraryUserDTO libraryUserDTO);

    Member toEntryMemberFromDTO(LibraryUserDTO libraryUserDTO);

    //Mapping from Librarians

    LibrarianUserDTO toLibrarianDTO(Librarian librarianUser);

    @InheritInverseConfiguration
    Librarian toLibrarian(LibrarianUserDTO theDTO);


    //Mapping Members

    MemberUserDTO toMemberDTO(Member memberUser);

    @InheritInverseConfiguration
    Member toMember(MemberUserDTO theDTO);


    @Mappings({
            @Mapping(target = "fullName", source = "fullName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "email", source = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "dateOfBirth", source = "dateOfBirth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "phoneNumber", source = "phoneNumber", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "address", source = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "workShift", source = "workShift", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "department", source = "department", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    })
    Librarian updateLibrarianFromDTO(LibrarianUserDTO theLibrarianDTO, @MappingTarget Librarian theLibrarian);

    @Mappings({
            @Mapping(target = "fullName", source = "fullName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "email", source = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "dateOfBirth", source = "dateOfBirth", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "address", source = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "phoneNumber", source = "phoneNumber", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "currentBooks", source = "currentBooks", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "membershipActive", source = "membershipActive", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
    })
    Member updateMemberFromDTO(MemberUserDTO theMemberDTO, @MappingTarget Member theMember);






}
