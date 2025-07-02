# Ticket-Booking-System

I have designed and implemented a backend API for booking event tickets, focusing on concurrency handling and seat availability management.

🔧 Core Features:
CRUD Operations for Events

Allows creating, updating, retrieving, and deleting events.

Each event includes: name, date, location, and totalSeats.

Seat Reservation and Booking Workflow

Hold Seats API: Temporarily reserves seats for a user for 5 minutes, returning a unique holdId. These seats are not permanently booked yet.

Confirm Booking API: Accepts a valid holdId, confirms the booking, and marks the seats as permanently unavailable.

Auto-Expiration Logic: Holds are automatically released after 5 minutes if not confirmed. This is handled via scheduled cleanup logic to ensure expired holds free up seats for others.

Booking Management

View Bookings: Users can view all their confirmed bookings.

Cancel Booking: Users can cancel their confirmed bookings, making those seats available again.

Business Rules Enforced

Prevents overbooking by ensuring the combined count of held and booked seats does not exceed the event’s totalSeats.

Enforces idempotency by restricting duplicate bookings for the same user and event.

Real-Time Availability API

Provides current availability for an event by calculating:
availableSeats = totalSeats - (bookedSeats + heldSeats)

**class diagram:**

![ClassDiagram](https://github.com/user-attachments/assets/0410df46-30ec-4a9a-b8be-317e52ae4fa8)
