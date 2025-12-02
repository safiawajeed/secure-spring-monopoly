package com.monopoly.secure_spring_monopoly.controller;

import com.monopoly.secure_spring_monopoly.entity.RoomEntity;
import com.monopoly.secure_spring_monopoly.entity.PlayerEntity;
import com.monopoly.secure_spring_monopoly.service.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;
    public RoomController(RoomService roomService) { this.roomService = roomService; }

    @PostMapping("/create")
    public Map<String,Object> createRoom(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return Map.of("error", "not logged in");

        RoomEntity room = roomService.createRoom(userId);
        roomService.joinRoom(room.getId(), userId);

        return Map.of("roomId", room.getId());
    }

    @PostMapping("/join/{roomId}")
    public Map<String,Object> join(
            @PathVariable Integer roomId,
            HttpSession session
    ) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return Map.of("error", "not logged in");

        boolean ok = roomService.joinRoom(roomId, userId);
        return Map.of("success", ok);
    }

    @GetMapping("/players/{roomId}")
    public List<PlayerEntity> players(@PathVariable Integer roomId) {
        return roomService.getPlayers(roomId);
    }

    @GetMapping("/tiles")
    public Object tiles() {
        return roomService.getTiles();
    }
}
