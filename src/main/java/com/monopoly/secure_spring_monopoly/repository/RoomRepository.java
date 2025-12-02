package com.monopoly.secure_spring_monopoly.repository;

import com.monopoly.secure_spring_monopoly.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

}
