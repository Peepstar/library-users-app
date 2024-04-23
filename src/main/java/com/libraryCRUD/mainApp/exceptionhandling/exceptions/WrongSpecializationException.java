package com.libraryCRUD.mainApp.exceptionhandling.exceptions;

// --> Exception for wrong Specialization enum for different endpoints. (E.g "seminaries/**")  <-- \\
public class WrongSpecializationException extends RuntimeException{
    public WrongSpecializationException(String message) {
        super(message);
    }

}
