package com.libraryCRUD.mainApp.entities;

import com.libraryCRUD.mainApp.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("MEMBER")
public class Member extends LibraryUser {
    @Column(name = "membership_active")
    private Boolean membershipActive;
    @Column(name = "current_books")
    private Integer currentBooks;


    //Getters and setters

    public Boolean getMembershipActive() {
        return membershipActive;
    }
    public void setMembershipActive(Boolean membershipActive) {
        this.membershipActive = membershipActive;
    }
    public Integer getCurrentBooks() {
        return currentBooks;
    }
    public void setCurrentBooks(Integer currentBooks) {
        this.currentBooks = currentBooks;
    }
    
    
    //Constructors
    
    public Member() {
        this.currentBooks = 0; // Set default value for currentBooks
        this.membershipActive = false;
    }
    public Member(Boolean membershipActive, Integer currentBooks) {
        this.membershipActive = membershipActive;
        this.currentBooks = currentBooks;
    }


    //Security methods
    
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
