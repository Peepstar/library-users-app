package com.libraryCRUD.mainApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// --> LIBRARIAN entity that extends from Abstract LibraryUser <-- \\
@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends LibraryUser {
    @Column(name = "work_shift")
    private String workShift;
    ///PENDING FOR CHANGES
    @Column(name = "department")
    private String department;


    //  Getters and setters
    public String getWorkShift() {
        return workShift;
    }
    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }


    //  Constructors

    public Librarian(String workShift, String department) {
        this.workShift = workShift;
        this.department = department;
    }

    public Librarian() {
        this.workShift = "DefaultWorkShift"; // Set default value for workShift
        this.department = "DefaultDepartment"; // Set default value for department
    }


    //  Security methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = getUserRole().getPermissionsList().stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + getUserRole().name()));

        return authorities;
    }
    @Override
    public String getUsername() {
        return getEmail();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}


