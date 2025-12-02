package com.monopoly.secure_spring_monopoly.entity;

import java.io.Serializable;
import java.util.Objects;

public class PlayerKey implements Serializable {

    private Integer roomId;
    private Integer playerId;

    public PlayerKey() {}

    public PlayerKey(Integer roomId, Integer playerId) {
        this.roomId = roomId;
        this.playerId = playerId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerKey that)) return false;
        return Objects.equals(roomId, that.roomId) &&
               Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, playerId);
    }
}
