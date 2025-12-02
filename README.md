🎲 Secure Spring Monopoly — Backend Game Engine

A fully online, turn-based Monopoly game built with Java 17, Spring Boot 3, and an in-memory game engine.
Players can create rooms, join sessions, roll dice, buy properties, pay rent, and view the game state — all through REST APIs.

The engine mimics the core flow of your original Python FastAPI project, rewritten cleanly in Java with Spring Boot.

🚀 Features
🧑‍🤝‍🧑 Multiplayer Rooms

Any user can create a room

Other players can join using the room ID

🎲 Turn-Based Engine

Dice rolls

Movement around the board

Property ownership

Rent payments

Automatic bankruptcy detection

🧩 Game State Engine (In-Memory)

Powered by:

game/
  ├── MonopolyEngine.java
  ├── GamePlayer.java
  ├── GameTile.java
  └── GameLogger.java


Each room gets its own MonopolyEngine instance held in memory for fast gameplay.

📝 Action Logging

Every action (roll, movement, property bought, rent paid) is written into a DB log table and returned via /poll/state.

🗄 In-Memory H2 Database

No setup needed.
Tables auto-created from JPA entities.

📦 Tech Stack

Java 17

Spring Boot 3.4.12

Spring Web

Spring Data JPA

Spring Security (session-based)

H2 in-memory database

Maven

⚙️ Setup & Running Locally
1️⃣ Install Java 17 (Temurin)

On macOS:

brew install --cask temurin@17

2️⃣ Clone the repo
git clone https://github.com/safiawajeed/secure-spring-monopoly.git
cd secure-spring-monopoly

3️⃣ Run the game
mvn spring-boot:run

4️⃣ Open H2 console

http://localhost:8080/h2

Use:

JDBC URL: jdbc:h2:mem:monopolydb
User: sa
Password: (empty)

🛠 API Endpoints
Authentication
Register
POST /auth/register
{
  "username": "ali",
  "email": "ali@example.com",
  "password": "pass"
}

Login
POST /auth/login
{
  "email": "ali@example.com",
  "password": "pass"
}

Who Am I
GET /auth/whoami

🏠 Rooms
Create Room
POST /room/create

Join Room
POST /room/join/{roomId}

Get Players
GET /room/players/{roomId}

🎮 Game Actions
Roll Dice
POST /game/roll/{roomId}

Buy Property
POST /game/buy/{roomId}

End Turn
POST /game/end/{roomId}

🔄 Polling
Get Game State
GET /poll/state/{roomId}


Returns:

players

positions

balances

logs

actions

properties owned

🕹 How The Engine Works
MonopolyEngine.java

Holds all room-specific game state:

players

tiles

property ownership

turn index

bankruptcy state

Game Flow

Player logs in

Creates a room or joins one

Rolls dice

Lands on a tile

Tile logic executes:

unowned → eligible to buy

owned → must pay rent

special (e.g., tax, jail) → custom action

Player may buy the tile

Player ends turn

Next player's turn starts

All events get logged into the DB table and can be seen via polling endpoint.

📁 Project Structure
src/main/java/com/monopoly/secure_spring_monopoly/
│
├── entity/                 ← JPA models
├── repository/             ← DB access
├── service/                ← Game + business logic
├── controller/             ← REST endpoints
└── game/                   ← Monopoly engine (in-memory)

🔒 Security

Session-based authentication (HttpSession) is used:

/auth/login sets a session cookie

All game actions require the player to be logged in

No JWT complexity needed for multiplayer game rooms

🧪 Testing

You can test API calls using:

Postman

Thunder Client (VSCode)

Curl

Browser REST extensions

📌 Notes

H2 resets every restart (perfect for dev)

For production, you’d switch to MySQL/Postgres

Game engine easily supports expansion:

chance/community chest

jail

house/hotel upgrades

auctions

📜 License

MIT License — do whatever you want with it 😄