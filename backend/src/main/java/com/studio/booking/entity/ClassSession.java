package com.studio.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "class_sessions", indexes = {
    @Index(name = "idx_sessions_date_time", columnList = "session_date, start_time"),
    @Index(name = "idx_sessions_studio_class", columnList = "studio_class_id"),
    @Index(name = "idx_sessions_primary_instructor", columnList = "primary_instructor_id")
})
public class ClassSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studio_class_id", nullable = false)
    private StudioClass studioClass;

    @NotNull
    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @NotNull
    @Min(1)
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer capacity;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String room;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "primary_instructor_id", nullable = false)
    private User primaryInstructor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "session_co_instructors",
        joinColumns = @JoinColumn(name = "session_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "instructor_id", nullable = false)
    )
    private Set<User> coInstructors = new HashSet<>();

    /**
     * Optimistic lock version counter to prevent lost updates during concurrent edits.
     * Transactional booking/cancellation services will additionally acquire a row-level
     * PESSIMISTIC_WRITE lock on this entity to serialize capacity decisions and waitlist promotions.
     */
    @Version
    @Column(nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ClassSession() {
    }

    public ClassSession(StudioClass studioClass, LocalDate sessionDate, LocalTime startTime,
                        Integer durationMinutes, Integer capacity, String room, User primaryInstructor) {
        this.studioClass = studioClass;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.durationMinutes = durationMinutes;
        this.capacity = capacity;
        this.room = room;
        this.primaryInstructor = primaryInstructor;
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

    public StudioClass getStudioClass() {
        return studioClass;
    }

    public void setStudioClass(StudioClass studioClass) {
        this.studioClass = studioClass;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public User getPrimaryInstructor() {
        return primaryInstructor;
    }

    public void setPrimaryInstructor(User primaryInstructor) {
        this.primaryInstructor = primaryInstructor;
    }

    public Set<User> getCoInstructors() {
        return coInstructors;
    }

    public void setCoInstructors(Set<User> coInstructors) {
        this.coInstructors = coInstructors;
    }

    public void addCoInstructor(User instructor) {
        this.coInstructors.add(instructor);
    }

    public void removeCoInstructor(User instructor) {
        this.coInstructors.remove(instructor);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        if (!(o instanceof ClassSession that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
