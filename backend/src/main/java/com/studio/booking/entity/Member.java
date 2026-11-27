package com.studio.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "members", indexes = {
    @Index(name = "idx_members_email", columnList = "email", unique = true),
    @Index(name = "idx_members_expiry", columnList = "membership_expiry_date")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "membership_expiry_date", nullable = false)
    private LocalDate membershipExpiryDate;

    /**
     * Records the specific membership_expiry_date for which staff dismissed the expiring alert.
     * Storing this as a DATE rather than a boolean guarantees that when a member renews their
     * membership (advancing membership_expiry_date to a future date), the new expiry date will
     * no longer match dismissed_for_expiry_date, automatically causing the 7-day warning
     * alert to resurface in the future without manual flag resets.
     */
    @Column(name = "dismissed_for_expiry_date")
    private LocalDate dismissedForExpiryDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Member() {
    }

    public Member(String name, String email, LocalDate membershipExpiryDate) {
        this.name = name;
        this.email = email;
        this.membershipExpiryDate = membershipExpiryDate;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getMembershipExpiryDate() {
        return membershipExpiryDate;
    }

    public void setMembershipExpiryDate(LocalDate membershipExpiryDate) {
        this.membershipExpiryDate = membershipExpiryDate;
    }

    public LocalDate getDismissedForExpiryDate() {
        return dismissedForExpiryDate;
    }

    public void setDismissedForExpiryDate(LocalDate dismissedForExpiryDate) {
        this.dismissedForExpiryDate = dismissedForExpiryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
