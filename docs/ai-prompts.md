# AI prompts

The prompts you actually used, in the order you used them, grouped by what you were trying to achieve. For each significant one: what you asked, what you got back, and what you had to correct.

Include at least one prompt that produced something wrong, and what you did about it.

If you did not use AI at all, say so here, and describe your process instead.

## 1. Building the starter code and setting up the main database tables

### Prompt
> Initialize the Spring Boot 3 (Java 17+) Maven project inside `backend/` with dependencies for Spring Web, Spring Data JPA, Spring Security, Bean Validation, PostgreSQL Driver, and JJWT (0.12.x), plus `application.properties` configured via environment variables (`DB_URL`, `DB_USER`, `DB_PASS`, `JWT_SECRET`).
>
> Then design and create the JPA entities and enums inside `backend/src/main/java/com/studio/booking/entity/` covering all 10 requirements:
> 1. `User` (id, name, email, passwordHash, role: STAFF vs INSTRUCTOR)
> 2. `StudioClass` (id, title, description, discipline, defaultDurationMinutes, defaultCapacity, archived)
> 3. `ClassSession` (id, studioClass, sessionDate, startTime, durationMinutes, capacity, room, primaryInstructor, coInstructors many-to-many, and `@Version` for concurrency control)
> 4. `Member` (id, name, email, membershipExpiryDate, dismissedForExpiryDate)
> 5. `Booking` (id, member, classSession, status: BOOKED, WAITLISTED, CANCELLED, ATTENDED, NO_SHOW, createdAt, updatedAt)
> 6. `BookingHistory` (id, booking, oldStatus, newStatus, changedByUser, note, createdAt — strictly append-only)

### What you got
- Initially, the agent ran a background shell check (`java -version; mvn -version`) that hung indefinitely on Windows because global `mvn` was not on PATH.
- After instructing it to skip the global Maven check and include the Maven Wrapper (`mvnw` / `mvnw.cmd`), it generated `pom.xml`, `application.properties`, `StudioBookingApplication.java`, two enums (`Role`, `BookingStatus`), and six JPA entities (`User`, `StudioClass`, `ClassSession`, `Member`, `Booking`, `BookingHistory`) with PostgreSQL indexes and `@Version` optimistic locking on `ClassSession`.

### What you corrected
- **Cancelled hung environment check & enforced Maven Wrapper:** Stopped the stuck `mvn -version` task and explicitly directed the agent to generate the standard Maven Wrapper (`mvnw.cmd`) instead of relying on a global Maven binary.
- **Bytecode target alignment (`pom.xml`):** Verified that `<java.version>` targets Java 17/21 LTS rather than local JDK 22 so builds remain compatible with Render's standard Java runtime.
- **JDBC URL cleanup (`.env`):** Stripped `&channelBinding=require` from Neon's default connection snippet (`?sslmode=require` only) because the PostgreSQL JDBC driver rejects channel binding parameters during HikariCP pool initialization.
- **IDE metadata hygiene (`.gitignore`):** Added `.idea/` and `*.iml` to `.gitignore` after IntelliJ indexed the new `backend/` module so IDE settings stay out of Git history.

---