package com.libraryCRUD.mainApp.Service;

import com.libraryCRUD.mainApp.DTOs.LibrarianUserDTO;
import com.libraryCRUD.mainApp.DTOs.LibraryUserDTO;
import com.libraryCRUD.mainApp.DTOs.MemberUserDTO;
import com.libraryCRUD.mainApp.DTOs.UserDTO;
import com.libraryCRUD.mainApp.entities.LibraryUser;

import java.util.List;

public interface LibraryService {

    List<LibraryUserDTO> findAll();

    LibraryUser findById(Long theId);

    UserDTO saveLibraryUser(LibraryUserDTO theUserDTO);

    boolean deleteById(Long theId);

    UserDTO convertToDTO(LibraryUser theUser);

    LibraryUser convertToUser(LibraryUserDTO theUserDTO);

    UserDTO updateLibrarianUser(Long userId, LibrarianUserDTO theLibrarianDTO);

    UserDTO updateMemberUser(Long userId, MemberUserDTO theMemberDTO);

}
