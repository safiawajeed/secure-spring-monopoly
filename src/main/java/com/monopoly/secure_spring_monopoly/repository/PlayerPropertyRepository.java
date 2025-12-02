package com.monopoly.secure_spring_monopoly.repository;

import com.monopoly.secure_spring_monopoly.entity.PlayerPropertyEntity;
import com.monopoly.secure_spring_monopoly.entity.PlayerPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerPropertyRepository extends JpaRepository<PlayerPropertyEntity, PlayerPropertyKey> {

    List<PlayerPropertyEntity> findByRoomId(Integer roomId);

    List<PlayerPropertyEntity> findByRoomIdAndPlayerId(Integer roomId, Integer playerId);
}
