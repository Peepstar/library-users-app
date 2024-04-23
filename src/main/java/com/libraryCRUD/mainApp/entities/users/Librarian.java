package com.libraryCRUD.mainApp.entities.users;

import com.libraryCRUD.mainApp.entities.seminar.Seminar;
import com.libraryCRUD.mainApp.enums.Specialization;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.*;
import java.util.stream.Collectors;

// --> LIBRARIAN entity that extends from Abstract LibraryUser <-- \\
@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends LibraryUser {
    @Enumerated(EnumType.STRING) // Store specializations as strings
    @ElementCollection(targetClass = Specialization.class) // Use ElementCollection for enums
    @CollectionTable(name = "librarian_specializations", // Create a join table
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "specializations"}))
    private Set<Specialization> specializations;

    @OneToMany(mappedBy = "librarian", fetch = FetchType.LAZY)
    private List<Seminar> seminaries;


    //  Constructor with empty list as default value

    public Librarian() {
        /* this.specializations = new HashSet<>();
        this.seminaries = new ArrayList<>(); */
    }


    //  Getters and setters for Librarian fields

    public Set<Specialization> getSpecializations() { return specializations; }
    public void setSpecializations(Set<Specialization> specializations) { this.specializations = specializations; }
    public List<Seminar> getSeminaries() { return seminaries; }
    public void setSeminaries(List<Seminar> seminaries) { this.seminaries = seminaries; }


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


