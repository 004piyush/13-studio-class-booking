# Plan

Answer each of these, in your own words.

## How did you break the work into sessions?

I broke the build into 6 focused vertical slices, each ending with a working, testable milestone and an incremental Git commit:

* **Session 1 — Database Schema, Spring Boot Scaffold & Core Entities:** Initialize the Maven backend, configure Neon PostgreSQL via environment variables, and model all 6 core entities (`User`, `StudioClass`, `ClassSession`, `Member`, `Booking`, `BookingHistory`) with optimistic locking (`@Version`) and append-only audit constraints.

* **Session 2 — Authentication, Role-Based Access Control & Demo Seeder:** Implement stateless JWT authentication, BCrypt password hashing, role enforcement (`STAFF` vs. `INSTRUCTOR`), and an idempotent startup seeder with realistic classes, sessions, members, and edge-case bookings.

* **Session 3 — Class Catalog & Session Scheduling APIs:** Build CRUD endpoints for `StudioClass` (including soft-archiving) and `ClassSession` (supporting co-instructors and capacity tracking), plus validation preventing instructors from modifying classes or schedules.

* **Session 4 — Booking Lifecycle, Concurrency, Waitlist & Audit Trail:** Implement transactional booking creation, automatic capacity-to-waitlist overflow, cancellation with automatic FIFO waitlist promotion, attendance/no-show marking, and immutable `BookingHistory` logging on every transition.

* **Session 5 — Search/Filters, Recurring Sessions & Smart Expiry Alerts:** Add multi-criteria schedule filtering (date range, instructor, discipline, availability), weekly recurring session generation with conflict/holiday skip reporting, and the 7-day membership expiry banner with `dismissed_for_expiry_date` persistence.

* **Session 6 — React Frontend, Today's Ops Dashboard & Cloud Deployment:** Build the React UI (Staff vs. Instructor views, Today's Operations dashboard, booking & history modals), verify end-to-end flows, deploy to Render + Vercel + Neon, and finalize `SUBMISSION.md` and `architecture.md`.


---

## What order did you build in, and why that order?

I built **bottom-up from data invariants to business transactions to UI**, rather than starting with screens:

1. **Schema & Entities First:** Core domain rules—such as preventing double-booking under concurrency (`@Version`), preserving historical records when classes are archived, and storing `dismissed_for_expiry_date` instead of a boolean flag—depend directly on table relationships and database constraints. Getting the schema right first prevents cascading rewrites later.

2. **Security & Seeder Second:** Putting JWT auth, role checks, and seeded test accounts (`STAFF` and `INSTRUCTOR`) in place early means every API endpoint built afterward can be tested immediately with real role permissions and realistic edge cases (full sessions, waitlists, expiring memberships).

3. **Core Transactions Before Convenience Features:** Booking state transitions, waitlist promotion, and append-only `BookingHistory` are the highest-risk logic in the app. Completing and testing them before recurring session generation or search filters ensures bulk operations reuse proven single-session rules.

4. **Frontend & Deployment Last:** Once the REST API contract and error responses are stable, the React frontend can consume real endpoints cleanly without mock-data drift, leaving dedicated time at the end for cloud deployment and documentation polish.

---

## What did you estimate versus what it actually took?

| Session | Scope | Estimated | Actual | Notes / Variance |
| :--- | :--- | :---: | :---: | :--- |
| **Session 1** | Repo setup, Neon DB config, Spring Boot scaffold & JPA entities | 2.0 hrs | 2.5 hrs | Spent extra ~30 mins resolving Windows `mvn` PATH hang in agent terminal (switched to `mvnw.cmd`) and stripping `channelBinding` from Neon JDBC URL. |
| **Session 2** | Spring Security, JWT auth, RBAC & demo data seeder | 2.0 hrs | *TBD* | — |
| **Session 3** | StudioClass & ClassSession CRUD + soft-archive logic | 2.0 hrs | *TBD* | — |
| **Session 4** | Booking concurrency (`@Version`), waitlist promotion & `BookingHistory` | 3.0 hrs | *TBD* | — |
| **Session 5** | Schedule filters, recurring session generator & smart expiry alerts | 2.5 hrs | *TBD* | — |
| **Session 6** | React UI, Today's Ops dashboard, Render/Vercel deploy & final docs | 3.5 hrs | *TBD* | — |
| **Total** | **End-to-End Delivery** | **15.0 hrs** | *In Progress* | — |

---

## What did you cut when you ran short?

* **Cut from initial scope (intentional MVP boundaries):**

  * **Automated email/SMS notifications** on waitlist promotion or membership expiry (kept alerts in-app via the Today's Operations dashboard to focus on stateful dismissal logic).

  * **Member self-service portal & payment gateway integration** (the system is scoped specifically for internal studio `STAFF` and `INSTRUCTOR` operations).

  * **Complex recurrence rules** like "every 2nd Tuesday of the month" (focused on clean weekly recurrence across a date range with explicit conflict reporting).
