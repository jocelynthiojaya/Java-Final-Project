package com.example.recyclerviewproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InventoryDetails extends AppCompatActivity {
    private ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);

        //connect objects with their id
        final TextView itemName = (TextView) findViewById(R.id.itemName);
        final TextView itemDescription = (TextView) findViewById(R.id.itemDescription);
        final TextView itemStock = (TextView) findViewById(R.id.itemStock);

        final ImageButton saveBtnInvDetails = (ImageButton) findViewById(R.id.saveBtnInvDetails);
        final ImageButton addStockBtn = (ImageButton) findViewById(R.id.addStockBtn);
        final ImageButton removeStockBtn = (ImageButton) findViewById(R.id.removeStockBtn);

        final ImageView itemImage = (ImageView) findViewById(R.id.itemImage);


        //load the array list data and the passed position
        //set the text to the according data
        loadData();
        final int position = getIntent().getExtras().getInt("position");
        try {
            setInfo(itemName, itemDescription, itemStock, itemImage, position);
        }
        catch (IndexOutOfBoundsException e){
            openDialog();
        }


        //save button
        saveBtnInvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        //add stock button
        addStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStock(itemStock, position);
            }
        });

        //remove stock button
        removeStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStock(itemStock, position);
            }
        });

    }

    private void setInfo(TextView name, TextView description, TextView stock, ImageView image, int position){
        name.setText(itemList.get(position).getName());
        description.setText(itemList.get(position).getDescription());
        stock.setText(Integer.toString(itemList.get(position).getStock()));
        image.setImageResource(itemList.get(position).getImageResource());
    }

    private void addStock(TextView stock, int position){
        itemList.get(position).addOneStock();
        stock.setText(Integer.toString(itemList.get(position).getStock()));
    }

    private void removeStock(TextView stock, int position){
        itemList.get(position).removeOneStock();
        stock.setText(Integer.toString(itemList.get(position).getStock()));
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString("item list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("item list", null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        itemList = gson.fromJson(json, type);
    }

    public void openDialog(){
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.show(getSupportFragmentManager(), "error dialog");
    }
}
