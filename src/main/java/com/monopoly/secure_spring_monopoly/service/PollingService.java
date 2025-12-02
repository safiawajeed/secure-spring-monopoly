package com.monopoly.secure_spring_monopoly.service;

import com.monopoly.secure_spring_monopoly.entity.PlayerEntity;
import com.monopoly.secure_spring_monopoly.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PollingService {

    private final PlayerRepository playerRepo;
    private final RoomService roomService;

    public PollingService(PlayerRepository playerRepo, RoomService roomService) {
        this.playerRepo = playerRepo;
        this.roomService = roomService;
    }

    public Map<String, Object> getRoomState(Integer roomId) {
        List<PlayerEntity> players = playerRepo.findByRoomId(roomId);
        List<String> logs = roomService.getLogs(roomId);

        return Map.of(
                "players", players,
                "logs", logs
        );
    }
}
