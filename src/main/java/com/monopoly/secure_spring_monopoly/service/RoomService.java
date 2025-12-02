package com.monopoly.secure_spring_monopoly.service;

import com.monopoly.secure_spring_monopoly.entity.*;
import com.monopoly.secure_spring_monopoly.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    private final RoomRepository roomRepo;
    private final PlayerRepository playerRepo;
    private final UserRepository userRepo;
    private final TileRepository tileRepo;
    private final LogRepository logRepo;

    public RoomService(RoomRepository roomRepo,
                       PlayerRepository playerRepo,
                       UserRepository userRepo,
                       TileRepository tileRepo,
                       LogRepository logRepo) {

        this.roomRepo = roomRepo;
        this.playerRepo = playerRepo;
        this.userRepo = userRepo;
        this.tileRepo = tileRepo;
        this.logRepo = logRepo;
    }

    public RoomEntity createRoom(Integer userId) {
        Integer maxRoomId = roomRepo.findAll()
                .stream()
                .map(RoomEntity::getId)
                .max(Integer::compareTo)
                .orElse(0);

        RoomEntity room = new RoomEntity();
        room.setId(maxRoomId + 1);
        room.setRoomPlayerId(userId);
        room.setStatus("ACTIVE");

        roomRepo.save(room);
        return room;
    }

    public boolean joinRoom(Integer roomId, Integer userId) {
        Optional<RoomEntity> room = roomRepo.findById(roomId);
        if (room.isEmpty()) return false;

        PlayerEntity player = new PlayerEntity();
        player.setRoomId(roomId);
        player.setPlayerId(userId);
        player.setBalance(1500);
        player.setPosition(0);
        player.setRound(0);

        playerRepo.save(player);
        return true;
    }

    public List<PlayerEntity> getPlayers(Integer roomId) {
        return playerRepo.findByRoomId(roomId);
    }

    public List<TileEntity> getTiles() {
        return tileRepo.findAll();
    }

    public void log(Integer roomId, String value) {
        LogEntity log = new LogEntity();
        log.setRoomId(roomId);
        log.setValue(value);
        log.setTimestamp(java.time.LocalDateTime.now());
        logRepo.save(log);
    }

    public List<String> getLogs(Integer roomId) {
        List<LogEntity> logs = logRepo.findTop100ByRoomIdOrderByIdAsc(roomId);
        List<String> result = new ArrayList<>();

        for (LogEntity log : logs) result.add(log.getValue());

        return result;
    }
}
