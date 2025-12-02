package com.monopoly.secure_spring_monopoly.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "player_property")
@IdClass(PlayerPropertyKey.class)
public class PlayerPropertyEntity {

    @Id
    @Column(name = "room_id")
    private Integer roomId;

    @Id
    @Column(name = "player_id")
    private Integer playerId;

    @Id
    @Column(name = "property_id")
    private Integer propertyId;

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

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }
}
