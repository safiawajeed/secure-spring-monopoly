package com.monopoly.secure_spring_monopoly.entity;

import java.io.Serializable;
import java.util.Objects;

public class PlayerPropertyKey implements Serializable {

    private Integer roomId;
    private Integer playerId;
    private Integer propertyId;

    public PlayerPropertyKey() {}

    public PlayerPropertyKey(Integer roomId, Integer playerId, Integer propertyId) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.propertyId = propertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerPropertyKey that)) return false;
        return Objects.equals(roomId, that.roomId) &&
               Objects.equals(playerId, that.playerId) &&
               Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, playerId, propertyId);
    }
}
