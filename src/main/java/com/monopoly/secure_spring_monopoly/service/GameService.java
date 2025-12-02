package com.monopoly.secure_spring_monopoly.service;

import com.monopoly.secure_spring_monopoly.entity.*;
import com.monopoly.secure_spring_monopoly.game.*;
import com.monopoly.secure_spring_monopoly.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    private final PlayerRepository playerRepo;
    private final TileRepository tileRepo;
    private final UserRepository userRepo;
    private final PlayerPropertyRepository propertyRepo;
    private final RoomService roomService;

    private final Map<Integer, MonopolyEngine> engineByRoom = new ConcurrentHashMap<>();

    public GameService(PlayerRepository playerRepo,
                       TileRepository tileRepo,
                       UserRepository userRepo,
                       PlayerPropertyRepository propertyRepo,
                       RoomService roomService) {
        this.playerRepo = playerRepo;
        this.tileRepo = tileRepo;
        this.userRepo = userRepo;
        this.propertyRepo = propertyRepo;
        this.roomService = roomService;
    }

    private MonopolyEngine buildEngineForRoom(Integer roomId) {

        List<PlayerEntity> playerEntities = playerRepo.findByRoomId(roomId);
        List<TileEntity> tileEntities = tileRepo.findAll();

        List<GamePlayer> players = new ArrayList<>();
        for (PlayerEntity pe : playerEntities) {
            UserEntity u = userRepo.findById(pe.getPlayerId()).orElse(null);

            GamePlayer gp = new GamePlayer(
                    pe.getRoomId(),
                    pe.getPlayerId(),
                    u != null ? u.getUsername() : "Unknown",
                    pe.getBalance(),
                    pe.getPosition(),
                    pe.getRound()
            );

            // load owned properties
            List<PlayerPropertyEntity> owned = propertyRepo.findByRoomIdAndPlayerId(roomId, pe.getPlayerId());
            for (PlayerPropertyEntity pp : owned) {
                gp.addProperty(pp.getTileId());
            }

            players.add(gp);
        }

        List<GameTile> tiles = tileEntities.stream().map(t -> new GameTile(
                t.getId(), t.getName(), t.getDescription(),
                t.getCost(), t.getRent(), t.getHouseCost(), t.getHotelCost(), t.getSpecial()
        )).toList();

        GameLogger logger = msg -> roomService.log(roomId, msg);

        MonopolyEngine engine = new MonopolyEngine(roomId, players, tiles, logger);

        // Load ownership map
        List<PlayerPropertyEntity> props = propertyRepo.findByRoomId(roomId);
        for (PlayerPropertyEntity pp : props) {
            engine.setOwner(pp.getTileId(), pp.getPlayerId());
        }

        return engine;
    }

    private MonopolyEngine engine(Integer roomId) {
        return engineByRoom.computeIfAbsent(roomId, this::buildEngineForRoom);
    }

    public Map<String, Object> rollDice(Integer roomId, Integer playerId) {
        MonopolyEngine eng = engine(roomId);
        GamePlayer current = eng.getCurrentPlayer();

        if (current.getPlayerId() != playerId)
            return Map.of("error", "Not your turn");

        MonopolyEngine.RollResult result = eng.rollDiceForCurrentPlayer();

        // save updated balance/position
        PlayerEntity pe = playerRepo.findById(new PlayerKey(roomId, playerId)).orElse(null);
        if (pe != null) {
            pe.setBalance(current.getBalance());
            pe.setPosition(current.getPosition());
            pe.setRound(current.getRound());
            playerRepo.save(pe);
        }

        return Map.of(
                "dice1", result.dice1,
                "dice2", result.dice2,
                "total", result.total,
                "message", result.message,
                "action", result.action,
                "balance", current.getBalance()
        );
    }

    public Map<String, Object> buyProperty(Integer roomId, Integer playerId) {
        MonopolyEngine eng = engine(roomId);
        GamePlayer gp = eng.getPlayers().stream()
                .filter(p -> p.getPlayerId() == playerId)
                .findFirst().orElse(null);

        if (gp == null)
            return Map.of("error", "player not found");

        boolean ok = eng.buyProperty(gp);

        if (ok) {
            // insert into DB
            PlayerPropertyEntity prop = new PlayerPropertyEntity();
            prop.setRoomId(roomId);
            prop.setPlayerId(playerId);
            prop.setTileId(gp.getPosition());
            propertyRepo.save(prop);

            // update player balance DB
            PlayerEntity pe = playerRepo.findById(new PlayerKey(roomId, playerId)).orElse(null);
            if (pe != null) {
                pe.setBalance(gp.getBalance());
                playerRepo.save(pe);
            }
        }

        return Map.of("success", ok);
    }

    public void endTurn(Integer roomId, Integer playerId) {
        engine(roomId).endTurn();
    }
}
