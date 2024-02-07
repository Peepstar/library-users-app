package com.libraryCRUD.mainApp.ExceptionHandling;

import com.libraryCRUD.mainApp.entities.Role;

public class WrongRoleException extends  RuntimeException {

    public WrongRoleException(String message) {
        super(message);
    }
}
