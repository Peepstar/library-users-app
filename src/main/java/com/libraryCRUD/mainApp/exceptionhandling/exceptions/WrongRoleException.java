package com.libraryCRUD.mainApp.exceptionhandling.exceptions;

// --> Exception for wrong Role for different endpoints. (E.g "libraryusers/member/**")  <-- \\
public class WrongRoleException extends RuntimeException {
    public WrongRoleException(String message) {
        super(message);
    }

}
