# HomeServe – Home Services Booking App

## Project Overview
HomeServe is an Android application for booking home service providers.  
Users can browse service categories, view top providers, book a service, and manage bookings.

## Technologies Used
- Android Studio
- Java + XML
- Firebase Authentication (Email/Password)
- Cloud Firestore Database
- RecyclerView + CardView / Material UI
- Activities + Intents

## Features
### User Side
- Role Selection (User / Worker)
- Splash Screen
- Register (Firebase Auth + store user in Firestore)
- Login (Firebase Auth)
- Home Screen (Service Categories list)
- Provider List (Firestore data filtered by category)
- Booking Screen (date, time, address, details)
- My Bookings Screen (Firestore fetch)
- Booking Details Screen (complete details + Call Provider)
- Profile Screen (name + email)
- Forgot Password (send reset link to email)
- Menu (Profile, My Bookings, Privacy Policy, About, Logout)

### Worker Side (Basic)
- Worker Login/Register screens open
- Worker info stored in Firestore providers collection

## Firestore Collections
- `users` → stores user profile (name, email, createdAt)
- `providers` → stores providers (name, category, phone, rating)
- `bookings` → stores bookings (userId, provider details, date/time/address/details/status)

## Navigation Flow
RoleSelection → Login/Register → Home → Providers → Booking → MyBookings → BookingDetails

## How to Run
1. Open project in Android Studio
2. Sync Gradle
3. Run on Emulator or Physical Device


