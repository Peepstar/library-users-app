package com.libraryCRUD.mainApp.entities.users;

import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// --> MEMBER entity that extends from Abstract LibraryUser <-- \\
@Entity
@DiscriminatorValue("MEMBER")
public class Member extends LibraryUser {
    @ManyToMany(mappedBy = "listOfStudents")
    Set<Seminar> currentSeminaries;


    //  Getters and setters

    public Set<Seminar> getCurrentSeminaries() { return currentSeminaries; }
    public void setCurrentSeminaries(Set<Seminar> currentSeminaries) { this.currentSeminaries = currentSeminaries; }


    //  Constructors
    
    public Member() {
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
