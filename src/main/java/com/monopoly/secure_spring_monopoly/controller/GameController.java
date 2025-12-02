package com.monopoly.secure_spring_monopoly.controller;

import com.monopoly.secure_spring_monopoly.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    public GameController(GameService gameService) { this.gameService = gameService; }

    @PostMapping("/roll/{roomId}")
    public Map<String,Object> roll(
            @PathVariable Integer roomId,
            HttpSession session
    ) {
        Integer playerId = (Integer) session.getAttribute("userId");
        if (playerId == null) return Map.of("error", "not logged in");

        return gameService.rollDice(roomId, playerId);
    }
    @PostMapping("/buy/{roomId}")
public Map<String,Object> buy(
        @PathVariable Integer roomId,
        HttpSession session
) {
    Integer playerId = (Integer) session.getAttribute("userId");
    if (playerId == null)
        return Map.of("error", "not logged in");

    return gameService.buyProperty(roomId, playerId);
}

}

