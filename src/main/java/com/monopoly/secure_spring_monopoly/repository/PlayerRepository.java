package com.monopoly.secure_spring_monopoly.repository;

import com.monopoly.secure_spring_monopoly.entity.PlayerEntity;
import com.monopoly.secure_spring_monopoly.entity.PlayerKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, PlayerKey> {

    List<PlayerEntity> findByRoomId(Integer roomId);

    List<PlayerEntity> findByPlayerId(Integer playerId);
}
