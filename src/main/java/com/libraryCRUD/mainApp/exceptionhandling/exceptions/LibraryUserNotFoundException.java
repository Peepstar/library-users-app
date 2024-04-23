package com.libraryCRUD.mainApp.exceptionhandling.exceptions;

// --> Exception for Library users not present in database <-- \\
public class LibraryUserNotFoundException extends RuntimeException{
    public LibraryUserNotFoundException(String message) { super(message); }

}
