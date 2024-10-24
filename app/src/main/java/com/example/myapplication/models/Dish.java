package com.example.myapplication.models;

public class Dish {
    private String cuisine;
    private String name;
    private String description;
    private String price;


    public Dish(String cuisine, String name,String description, String price){
        this.cuisine=cuisine;
        this.name=name;
        this.description=description;
        this.price=price;
    }
    public String getName() {
        return name;
    }

    public String getDescription(){
        return description;
    }


    public String getPrice() {
        return price;
    }

    public String getCuisine(){
        return cuisine;
    }
}
