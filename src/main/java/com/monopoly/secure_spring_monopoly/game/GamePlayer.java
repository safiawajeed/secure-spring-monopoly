package com.monopoly.secure_spring_monopoly.game;

import java.util.HashSet;
import java.util.Set;

public class GamePlayer {

    private final int roomId;
    private final int playerId;
    private final String username;

    private int position;
    private int round;
    private int balance;

    // property ownership
    private final Set<Integer> propertyIdsOwned = new HashSet<>();

    public GamePlayer(int roomId, int playerId, String username,
                      int balance, int position, int round) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.username = username;
        this.balance = balance;
        this.position = position;
        this.round = round;
    }

    public void addProperty(int propertyId) {
        propertyIdsOwned.add(propertyId);
    }

    public boolean owns(int tileId) {
        return propertyIdsOwned.contains(tileId);
    }

    public Set<Integer> getOwnedProperties() { return propertyIdsOwned; }

    // getters + setters
    public int getRoomId() { return roomId; }
    public int getPlayerId() { return playerId; }
    public String getUsername() { return username; }
    public int getPosition() { return position; }
    public int getRound() { return round; }
    public int getBalance() { return balance; }

    public void setBalance(int balance) { this.balance = balance; }
    public void setPosition(int position) { this.position = position; }
    public void setRound(int round) { this.round = round; }
}
