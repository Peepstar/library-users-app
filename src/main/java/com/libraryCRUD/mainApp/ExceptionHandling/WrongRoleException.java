package com.libraryCRUD.mainApp.ExceptionHandling;

public class WrongRoleException extends  RuntimeException {

    public WrongRoleException(String message) {
        super(message);
    }
}
