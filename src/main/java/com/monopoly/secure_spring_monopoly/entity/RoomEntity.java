package com.monopoly.secure_spring_monopoly.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    @Column(name = "room_id")
    private Integer id; // manually assigned from max(room_id) + 1

    @Column(name = "room_player", nullable = false)
    private Integer roomPlayerId;

    @Column(name = "room_status", nullable = false)
    private String status;

    @Column(name = "room_winner")
    private Integer roomWinnerId; // nullable

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomPlayerId() {
        return roomPlayerId;
    }

    public void setRoomPlayerId(Integer roomPlayerId) {
        this.roomPlayerId = roomPlayerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRoomWinnerId() {
        return roomWinnerId;
    }

    public void setRoomWinnerId(Integer roomWinnerId) {
        this.roomWinnerId = roomWinnerId;
    }
}
