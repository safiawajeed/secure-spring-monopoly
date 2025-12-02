package com.monopoly.secure_spring_monopoly.repository;

import com.monopoly.secure_spring_monopoly.entity.TileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TileRepository extends JpaRepository<TileEntity, Integer> {
}
