# 🎲 Secure Spring Monopoly

A secure, turn-based **online Monopoly backend** built using **Java 17** and **Spring Boot 3**.  
Players can create rooms, join sessions, roll dice, buy properties, pay rent, and track game state — all via REST APIs.

This project is a **Java/Spring rewrite** of the original Python FastAPI CLI version, updated with session-based authentication and a clean in-memory game engine.

---

## 📌 Table of Contents
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Setup](#-setup)
- [Environment](#-environment)
- [Running the Application](#-running-the-application)
- [API Overview](#-api-overview)
  - [Auth Endpoints](#auth-endpoints)
  - [Room Endpoints](#room-endpoints)
  - [Game Endpoints](#game-endpoints)
  - [Polling Endpoints](#polling-endpoints)
- [Game Engine](#-game-engine)
- [H2 Database](#-h2-database)
- [Future Enhancements](#-future-enhancements)
- [License](#-license)

---

# ⭐ Features

### 🔐 Secure Authentication
- Register, login, session tracking  
- Session cookie–based security (simple + safe for multiplayer)

### 🧑‍🤝‍🧑 Multiplayer Support
- Create a room  
- Join existing rooms  
- Each room maintains its own game state

### 🎲 Full Turn-Based Gameplay
- Dice rolling  
- Movement  
- Property buying  
- Rent logic  
- Balance updates  
- Bankruptcy placeholder

### 🧩 In-Memory Monopoly Engine
- Fast, lightweight, no DB needed  
- Each room has its own `MonopolyEngine` instance  
- Easy to extend (Chance, Jail, Houses, etc.)

### 📝 Persistent Logging
Every action is recorded:
- Rolls  
- Property purchases  
- Rent payments  
- Movements  
- Player interactions  

Logs are returned in `/poll/state`.

### 🗄 H2 In-Memory Database
- Zero setup  
- Auto-wipes on restart  
- Great for local development

---

# 🛠 Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.4.x |
| Security | Spring Security (Session Auth) |
| Persistence | Spring Data JPA |
| Database | H2 In-Memory |
| Build | Maven |
| Architecture | MVC + Service Layer + In-Memory Engine |

---

# 📁 Project Structure

src/main/java/com/monopoly/secure_spring_monopoly/
│
├── controller/ # REST controllers
├── service/ # Business logic layer
├── repository/ # JPA repositories
├── entity/ # JPA entities
└── game/ # Monopoly engine (in memory)


---

# ⚙ Setup

### Prerequisites
Install Java 17 (Temurin recommended):

```sh
brew install --cask temurin@17
🌱 Environment

Your application.yml config should include:
spring:
  datasource:
    url: jdbc:h2:mem:monopolydb
    driver-class-name: org.h2.Driver
    username: sa

  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true

  h2:
    console:
      enabled: true
      path: /h2

server:
  port: 8080
🧠 Game Engine

Core logic stored in game/:

game/
  ├── MonopolyEngine.java
  ├── GamePlayer.java
  ├── GameTile.java
  ├── GameLogger.java
  ├── Dice.java
  └── Board.java

Features:

Full game state management

Turns & sequencing

Property buying

Rent calculation

Movement

Logging

Extensible:

Chance/Community Chest

Jail mechanics

Houses/Hotels

Trading

Auctions

# 🔒 Security Features (CWE-Based)

This project implements multiple safeguards aligned with common **CWE (Common Weakness Enumeration)** recommendations.

### ✔ JWT Tokens (CWE-384: Session Fixation & CWE-613: Session Management)
- Supports secure JWT authentication option  
- Tokens are short-lived and signed  
- Prevents session hijacking and tampering  

### ✔ Input Validation (CWE-20: Improper Input Validation)
- All user inputs validated at controller and service layers  
- Sanitization for unsafe strings  
- Rejects malformed JSON / invalid object states  

### ✔ HTTPS + TLS Certificates (CWE-296: Improper Certificate Validation)
- Supports HTTPS mode  
- Includes key + certificate support in Spring Boot config  
- Mitigates MITM and eavesdropping  

### ✔ XSS Prevention (CWE-79)
- No HTML rendering  
- JSON-only API responses  
- Sanitized inputs and headers  
- Spring Security default XSS hardening  

### ✔ Secure Password Storage (CWE-256: Plaintext Storage)
- BCrypt hashing  
- Never stored or logged in plaintext  

### ✔ Security Headers (CWE-614 / CWE-346 / CWE-602)
- Session cookie set to `HttpOnly`  
- CORS hardening  
- Frame, content-type, caching headers  

### ✔ Authentication Required for Game Actions (CWE-306: Missing Authentication)
All endpoints under: