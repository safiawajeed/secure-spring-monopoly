package com.monopoly.secure_spring_monopoly.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "property_list")
public class TileEntity {

    @Id
    @Column(name = "property_id")
    private Integer id;

    @Column(name = "property_name")
    private String name;

    @Column(name = "property_description")
    private String description;

    @Column(name = "property_cost")
    private Integer cost;

    @Column(name = "property_rent")
    private Integer rent;

    @Column(name = "property_house_cost")
    private Integer houseCost;

    @Column(name = "property_hotel_cost")
    private Integer hotelCost;

    @Column(name = "is_property_special")
    private Boolean special;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getHouseCost() {
        return houseCost;
    }

    public void setHouseCost(Integer houseCost) {
        this.houseCost = houseCost;
    }

    public Integer getHotelCost() {
        return hotelCost;
    }

    public void setHotelCost(Integer hotelCost) {
        this.hotelCost = hotelCost;
    }

    public Boolean getSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }
}
