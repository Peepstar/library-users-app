package com.libraryCRUD.mainApp.exceptionhandling.exceptions;

// --> Exception for Duplicated emails in database <-- \\
public class DuplicatedConstraintException extends RuntimeException{
    public DuplicatedConstraintException(String message) { super(message); }

}
