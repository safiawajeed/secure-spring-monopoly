package com.monopoly.secure_spring_monopoly.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "player")
@IdClass(PlayerKey.class)
public class PlayerEntity {

    @Id
    @Column(name = "room_id")
    private Integer roomId;

    @Id
    @Column(name = "player_id")
    private Integer playerId; // maps to user.user_id

    @Column(name = "player_balance")
    private Integer balance;

    @Column(name = "player_prop_id")
    private Integer playerPropId; // rarely used

    @Column(name = "player_position")
    private Integer position;

    @Column(name = "player_round")
    private Integer round;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getPlayerPropId() {
        return playerPropId;
    }

    public void setPlayerPropId(Integer playerPropId) {
        this.playerPropId = playerPropId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }
}
