package com.libraryCRUD.mainApp.ExceptionHandling;

// --> Exception for Duplicated emails in database <-- \\
public class DuplicatedEmailException extends RuntimeException{
    public DuplicatedEmailException(String message) { super(message); }
}
