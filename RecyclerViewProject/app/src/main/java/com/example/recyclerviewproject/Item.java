package com.example.recyclerviewproject;

public class Item {
    private int imageResource, stock;
    private String name, description;

    public Item(int imgResource, String nm, String desc, int stk){
        imageResource = imgResource;
        name = nm;
        description = desc;
        stock = stk;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addOneStock(){
        this.stock += 1;
    }

    public void removeOneStock(){
        this.stock -= 1;
    }

    public String toString(){
        return name + " = " + stock;
    }
}
