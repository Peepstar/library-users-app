package com.libraryCRUD.mainApp.mappers;

import com.libraryCRUD.mainApp.dto.seminardto.EntryNewSeminarDTO;
import com.libraryCRUD.mainApp.dto.seminardto.SeminarDTO;
import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import com.libraryCRUD.mainApp.entities.users.Member;
import org.mapstruct.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// --> Mapper for Seminar DTOs and Entities <-- \\
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeminarMapper {
    // --> MAPPINGS FOR POST METHODS <-- \\

    //  Mapping to POST an Entity from DTO
    @Mappings({
            @Mapping(source = "seminarName", target = "seminarName"),
            @Mapping(source = "specialization", target = "specialization"),
            @Mapping(target = "seminarId", ignore = true),
            @Mapping(target = "librarian", ignore = true),
            @Mapping(target = "listOfStudents", ignore = true)
    })
    Seminar toEntryNewSeminar(EntryNewSeminarDTO newSeminarDTO);

    // --> MAPPINGS FOR GET METHODS <-- \\

    //  Mapping from Entity to DTO
    @Mappings({
            @Mapping(source = "librarian.fullName", target = "librarianName"),
            @Mapping(source = "librarian.email", target = "librarianEmail"),
            @Mapping(source = "specialization", target= "specialization"),
            @Mapping(source = "listOfStudents", target = "listOfStudents", qualifiedByName = "studentNames"),
            @Mapping(source = "listOfStudents", target = "totalOfStudents", qualifiedByName = "countStudents"),
            @Mapping(source = "librarian.userId", target = "librarianId"),
            @Mapping(target = "studentsToAdd", ignore = true)
    })
    SeminarDTO toDto(Seminar seminar);

    //  Mapping from Entities list to DTOs list
    List<SeminarDTO> toListDTO(List<Seminar> seminarList);

    // --> MAPPINGS FOR PUT METHODS <-- \\

    //  Mapping from DTO to Entity to UPDATE passed in fields
    @Mappings({
            @Mapping(source = "seminarName", target = "seminarName", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE),
            @Mapping(target = "librarian", ignore = true),
            @Mapping(target = "seminarId", ignore = true),
            @Mapping(target = "specialization", ignore = true), //  Specialization is not allowed to be updated
            @Mapping(target = "listOfStudents", ignore = true) //  This update is handled by service class
    })
    Seminar updateSeminarFromDTO(SeminarDTO theSeminarDTO, @MappingTarget Seminar theSeminar);


    //  Helper method to map from a List of Member to a List of String with all Member's name
    @Named("studentNames")
    default Set<String> studentNames(Set<Member> students) {
        if (students == null) {
            return Collections.emptySet();
        }
        return students.stream().map(Member::getFullName).collect(Collectors.toSet());
    }

    //  Helper method to map totalOfStudents from size of listOfStudents
    @Named("countStudents")
    default Integer countStudents(Set<Member> listOfStudents){
        if(listOfStudents != null){
            return listOfStudents.size();
        }
        return 0;  //  No students when mapping occurs5
    }

}

