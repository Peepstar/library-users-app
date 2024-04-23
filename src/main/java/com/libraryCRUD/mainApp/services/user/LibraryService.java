package com.libraryCRUD.mainApp.services.user;

import com.libraryCRUD.mainApp.dto.usersdto.LibrarianUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.LibraryUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.MemberUserDTO;
import com.libraryCRUD.mainApp.dto.usersdto.UserDTO;
import com.libraryCRUD.mainApp.entities.users.LibraryUser;
import java.util.Set;

public interface LibraryService {

    Set<LibraryUserDTO> findAll();

    LibraryUser findById(Long theId);

    UserDTO saveLibraryUser(LibraryUserDTO theUserDTO);

    boolean deleteById(Long theId);

    UserDTO convertToDTO(LibraryUser theUser);

    UserDTO updateLibrarianUser(Long userId, LibrarianUserDTO theLibrarianDTO);

    UserDTO updateMemberUser(Long userId, MemberUserDTO theMemberDTO);

    boolean deleteSpecializationFromLibrarian(Long librarianId, String theSpecialization);

}
