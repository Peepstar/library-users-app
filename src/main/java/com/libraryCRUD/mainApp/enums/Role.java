package com.libraryCRUD.mainApp.enums;

import java.util.Arrays;
import java.util.List;

// --> Roles for LibraryUsers. It allows single table inheritance in database and provide permissions based on Role <-- \\
public enum Role {
    MEMBER(Arrays.asList(Permissions.READ_ONE_USER, Permissions.UPDATE_ONE_USER)),

    LIBRARIAN(Arrays.asList(Permissions.READ_ONE_USER, Permissions.UPDATE_ONE_USER, Permissions.CREATE_ONE_USER, Permissions.DELETE_ONE_USER));

    private List<Permissions> permissionsList;

    Role(List<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public List<Permissions> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(List<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }

}
