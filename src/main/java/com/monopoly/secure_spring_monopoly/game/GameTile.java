package com.monopoly.secure_spring_monopoly.game;

public class GameTile {

    private final int id;
    private final String name;
    private final String description;
    private final int cost;
    private final int rent;
    private final int houseCost;
    private final int hotelCost;
    private final boolean special;

    public GameTile(int id, String name, String description,
                    int cost, int rent, int houseCost, int hotelCost,
                    boolean special) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.rent = rent;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
        this.special = special;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCost() { return cost; }
    public int getRent() { return rent; }
    public int getHouseCost() { return houseCost; }
    public int getHotelCost() { return hotelCost; }
    public boolean isSpecial() { return special; }
}
