package com.example.recyclerviewproject;

import java.util.ArrayList;

public class Order {
    private String name, description; //mau tambahin order id
    private ArrayList<Item> items;

    public Order(String nm, String desc){
        name = nm;
        description = desc;
        items = new ArrayList<>();
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

    public void addItem(String name, int qty){
        items.add(new Item(0, name, "", qty));
    }

    public void removeItem(int pos){
        items.remove(pos);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
