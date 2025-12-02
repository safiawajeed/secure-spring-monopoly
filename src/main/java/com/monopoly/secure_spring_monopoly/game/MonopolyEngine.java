package com.monopoly.secure_spring_monopoly.game;

import java.util.*;

public class MonopolyEngine {

    private final int roomId;
    private final List<GamePlayer> players;
    private final List<GameTile> tiles;

    private final GameLogger logger;

    // tileId → playerId (owner)
    private final Map<Integer, Integer> ownershipMap = new HashMap<>();

    private int currentPlayerIndex = 0;
    private boolean isRolled = false;
    private boolean gameOver = false;

    public MonopolyEngine(int roomId,
                          List<GamePlayer> players,
                          List<GameTile> tiles,
                          GameLogger logger) {
        this.roomId = roomId;
        this.players = players;
        this.tiles = tiles;
        this.logger = logger;
    }

    public GamePlayer getCurrentPlayer() {
        return players.get(currentPlayerIndex % players.size());
    }

    public Integer getOwner(int tileId) {
        return ownershipMap.get(tileId);
    }

    public void setOwner(int tileId, int ownerId) {
        ownershipMap.put(tileId, ownerId);
    }

    public List<GamePlayer> getPlayers() { return players; }

    public RollResult rollDiceForCurrentPlayer() {
        GamePlayer player = getCurrentPlayer();

        int d1 = (int)(Math.random()*6) + 1;
        int d2 = (int)(Math.random()*6) + 1;
        int total = d1 + d2;

        int oldPos = player.getPosition();
        player.setPosition((oldPos + total) % tiles.size());
        player.setRound(player.getRound() + 1);

        GameTile tile = tiles.get(player.getPosition());

        String msg = player.getUsername() +
                " rolled " + d1 + " + " + d2 + " = " + total +
                " moved from " + oldPos + " → " + player.getPosition() +
                " landed on " + tile.getName();

        logger.log(msg);

        isRolled = true;

        // main logic
        String action = handleLanding(player, tile);

        return new RollResult(d1, d2, total, msg, action);
    }

    private String handleLanding(GamePlayer player, GameTile tile) {

        if (tile.isSpecial()) {
            logger.log(player.getUsername() + " landed on special tile: " + tile.getName());
            return "SPECIAL";
        }

        Integer owner = getOwner(tile.getId());

        // unowned property
        if (owner == null) {
            logger.log("Tile " + tile.getName() + " is unowned. Player may purchase for $" + tile.getCost());
            return "UNOWNED";
        }

        // owned by same player
        if (owner == player.getPlayerId()) {
            logger.log(player.getUsername() + " landed on their own property.");
            return "OWN";
        }

        // RENT
        GamePlayer ownerPlayer = players.stream()
                .filter(p -> p.getPlayerId() == owner)
                .findFirst()
                .orElse(null);

        if (ownerPlayer != null) {
            payRent(player, ownerPlayer, tile.getRent(), tile.getName());
        }

        return "RENT";
    }

    public boolean buyProperty(GamePlayer buyer) {
        int tileId = buyer.getPosition();
        GameTile tile = tiles.get(tileId);

        if (tile.isSpecial()) {
            logger.log("Cannot buy special tile.");
            return false;
        }

        if (buyer.getBalance() < tile.getCost()) {
            logger.log(buyer.getUsername() + " does not have enough money to buy " + tile.getName());
            return false;
        }

        if (ownershipMap.containsKey(tileId)) {
            logger.log(tile.getName() + " is already owned.");
            return false;
        }

        buyer.setBalance(buyer.getBalance() - tile.getCost());
        setOwner(tileId, buyer.getPlayerId());
        buyer.addProperty(tileId);

        logger.log(buyer.getUsername() + " purchased " + tile.getName() + " for $" + tile.getCost());
        return true;
    }

    private void payRent(GamePlayer payer, GamePlayer owner, int rent, String tileName) {
        payer.setBalance(payer.getBalance() - rent);
        owner.setBalance(owner.getBalance() + rent);

        logger.log(payer.getUsername() + " paid $" + rent +
                " rent to " + owner.getUsername() +
                " for landing on " + tileName);

        if (payer.getBalance() < 0) {
            gameOver = true;
            logger.log("GAME OVER: " + payer.getUsername() + " went bankrupt.");
        }
    }

    public void endTurn() {
        if (!isRolled) return;

        GamePlayer old = getCurrentPlayer();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        GamePlayer next = getCurrentPlayer();

        logger.log(old.getUsername() + " ended turn. Now " + next.getUsername() + "'s turn.");
        isRolled = false;
    }

    public static class RollResult {
        public final int dice1, dice2, total;
        public final String message, action;

        public RollResult(int dice1, int dice2, int total, String message, String action) {
            this.dice1 = dice1;
            this.dice2 = dice2;
            this.total = total;
            this.message = message;
            this.action = action;
        }
    }
}
