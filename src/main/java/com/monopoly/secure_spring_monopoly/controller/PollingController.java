package com.monopoly.secure_spring_monopoly.controller;

import com.monopoly.secure_spring_monopoly.service.PollingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poll")
public class PollingController {

    private final PollingService pollingService;
    public PollingController(PollingService pollingService) { this.pollingService = pollingService; }

    @GetMapping("/state/{roomId}")
    public Object state(@PathVariable Integer roomId) {
        return pollingService.getRoomState(roomId);
    }
}
