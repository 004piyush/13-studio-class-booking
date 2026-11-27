package com.studio.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Immutable audit trail for all booking lifecycle state changes.
 * Every status transition, cancellation, waitlist promotion, and staff note
 * is strictly append-only.
 *
 * Notice all persistent columns have updatable = false: Hibernate will reject
 * or omit any UPDATE SQL queries for this table.
 */
@Entity
@Table(name = "booking_histories", indexes = {
    @Index(name = "idx_history_booking_id", columnList = "booking_id"),
    @Index(name = "idx_history_created_at", columnList = "created_at")
})
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false, updatable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", length = 32, updatable = false)
    private BookingStatus oldStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 32, updatable = false)
    private BookingStatus newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by_user_id", updatable = false)
    private User changedByUser;

    @Column(columnDefinition = "TEXT", updatable = false)
    private String note;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public BookingHistory() {
    }

    public BookingHistory(Booking booking, BookingStatus oldStatus, BookingStatus newStatus, User changedByUser, String note) {
        this.booking = booking;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedByUser = changedByUser;
        this.note = note;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public Booking getBooking() {
        return booking;
    }

    public BookingStatus getOldStatus() {
        return oldStatus;
    }

    public BookingStatus getNewStatus() {
        return newStatus;
    }

    public User getChangedByUser() {
        return changedByUser;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingHistory that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
