package com.libraryCRUD.mainApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
public class Member extends LibraryUser {

    @Column(name = "membership_active")
    private Boolean membershipActive;

    @Column(name = "current_books")
    private Integer currentBooks;

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

    public Member() {
        this.currentBooks = 0; // Set default value for currentBooks
        this.membershipActive = false;
    }
}
