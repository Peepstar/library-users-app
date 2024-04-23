package com.libraryCRUD.mainApp.restcontrollers.seminarcontroller;

import com.libraryCRUD.mainApp.dto.seminardto.EntryNewSeminarDTO;
import com.libraryCRUD.mainApp.dto.seminardto.SeminarDTO;
import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import com.libraryCRUD.mainApp.services.seminar.SeminarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// --> RestController to access Seminar end points <-- \\
@RestController
public class SeminarController {
    private final SeminarService seminarService;
    //  Inject seminarServiceImpl using dependency injection
    @Autowired
    public SeminarController(SeminarService theSeminarService) {
        this.seminarService = theSeminarService;
    }


    // --> END POINTS <-- \\

    @GetMapping("/seminaries")
    public List<SeminarDTO> findAll() {
        return seminarService.findAll();
    }

    @GetMapping("/seminaries/{seminarId}")
    public SeminarDTO getSeminar(@PathVariable Long seminarId) {
        Seminar theSeminar = seminarService.findById(seminarId);
        return seminarService.convertToDTO(theSeminar);
    }

    @PostMapping("/seminaries")
    public SeminarDTO postNewSeminar(@Valid @RequestBody EntryNewSeminarDTO newSeminar) {
        return seminarService.saveSeminar(newSeminar);
    }

    @PutMapping("/seminaries/{seminarId}") //  PUT an existing Seminar || PENDING TO CHANGE return type
    public SeminarDTO updateSeminar(@PathVariable Long seminarId, @Valid @RequestBody SeminarDTO theSeminarDTO) {
        return seminarService.updateSeminar(seminarId, theSeminarDTO);
    }

    @DeleteMapping("/seminaries/{seminarId}") //  Delete an existing Seminar
    public String deleteSeminar(@PathVariable Long seminarId) {
        if (seminarService.deleteById(seminarId)) {
            return String.format("Seminar with id %d was successfully deleted from database", seminarId);
        } else {
            // In case that any other exception different from "SeminarID not found" is thrown.
            return String.format("User with id %d was not deleted", seminarId);
        }
    }

    @PutMapping("/seminaries/deleteStudents/{seminarId}") //  Delete Members from student list
    public String deleteStudentsFromSeminar(@PathVariable Long seminarId, @RequestBody List<Long> studentsToDelete){
        if(seminarService.deleteStudentsByIdFromSeminar(seminarId, studentsToDelete)) {
            return String.format("Students were successfully deleted from seminar with id %d", seminarId);
        } else {
            // Handle unsuccessful deletion (no students deleted or other reasons)
            return String.format("No students were deleted from seminar with id %d", seminarId);
        }
    }

}
