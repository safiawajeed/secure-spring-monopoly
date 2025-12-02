package com.monopoly.secure_spring_monopoly.repository;

import com.monopoly.secure_spring_monopoly.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<LogEntity, Long> {

    List<LogEntity> findTop100ByRoomIdOrderByIdAsc(Integer roomId);

    List<LogEntity> findByRoomId(Integer roomId);
}
