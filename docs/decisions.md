# Decisions

Log the decisions that actually shaped this codebase — the ones where a real alternative existed and
you picked one. At least five entries. For each: what you chose, what you rejected, and why. At least
one entry must be a decision you later reversed — say what changed your mind. It can be any entry
below, not necessarily the last one; add a **Later reversed:** line to whichever one it is.

## Decision 1

- **Chose:** Storing `dismissed_for_expiry_date` as a `DATE` column on `Member` instead of a boolean `alert_dismissed` flag.
- **Rejected:** A mutable boolean `alert_dismissed` flag coupled with manual reset logic on membership renewal.
- **Why:** When a membership enters its 7-day expiration window, staff can dismiss the alert banner. If a boolean were used, dismissing sets `alert_dismissed = true`. When the member later renews their membership (extending `membership_expiry_date` by months or years), the boolean would either need to be manually cleared upon renewal. By tracking the specific expiry date that was dismissed(`dismissed_for_expiry_date`), the query condition `WHERE membership_expiry_date <= CURRENT_DATE + 7 AND (dismissed_for_expiry_date IS NULL OR dismissed_for_expiry_date != membership_expiry_date)` naturally suppresses the alert for that specific expiry date, but cleanly and automatically reactivates the alert when the renewed membership approaches its future expiration date.

## Decision 2

- **Chose:**
- **Rejected:**
- **Why:**

## Decision 3

- **Chose:**
- **Rejected:**
- **Why:**

## Decision 4

- **Chose:**
- **Rejected:**
- **Why:**

## Decision 5

- **Chose:**
- **Rejected:**
- **Why:**
