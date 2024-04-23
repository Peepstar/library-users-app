package com.libraryCRUD.mainApp.services.seminar;

import com.libraryCRUD.mainApp.dto.seminardto.EntryNewSeminarDTO;
import com.libraryCRUD.mainApp.dto.seminardto.SeminarDTO;
import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import java.util.List;

public interface SeminarService {

    List<SeminarDTO> findAll();

    Seminar findById(Long id);

    SeminarDTO convertToDTO(Seminar seminar);

    SeminarDTO saveSeminar(EntryNewSeminarDTO seminarDTO);

    SeminarDTO updateSeminar(Long seminarId, SeminarDTO seminarDTO);

    boolean deleteById(Long seminarId);

    boolean deleteStudentsByIdFromSeminar(Long seminarId, List<Long> studentsToDelete);

}
