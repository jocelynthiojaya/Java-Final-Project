package com.example.recyclerviewproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ViewInventory extends AppCompatActivity {
    private ArrayList<Item> itemList;

    private RecyclerView myRecyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        buildRecyclerView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);

        loadData();
        buildRecyclerView();

        //connect objects with their id
        final EditText editText1 = (EditText) findViewById(R.id.editTextName);
        final EditText editText2 = (EditText) findViewById(R.id.editTextDescription);
        final EditText editTextRemove = (EditText) findViewById(R.id.editTextRemove);

        final Button confirmBtn = (Button) findViewById(R.id.confirmAddBtn);
        final Button confirmBtn2 = (Button) findViewById(R.id.confirmRemoveBtn);
        final Button addBtn = (Button) findViewById(R.id.addBtn);
        final Button removeBtn = (Button) findViewById(R.id.removeBtn);



        //set visibilities
        editText1.setVisibility(View.INVISIBLE);
        editText2.setVisibility(View.INVISIBLE);
        editTextRemove.setVisibility(View.INVISIBLE);
        confirmBtn.setVisibility(View.INVISIBLE);
        confirmBtn2.setVisibility(View.INVISIBLE);

        //code for add button
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);

                confirmBtn.setVisibility(View.VISIBLE);
                addBtn.setVisibility(View.INVISIBLE);
                removeBtn.setVisibility(View.INVISIBLE);
            }
        });

        //code for confirm button 1
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertItem(editText1, editText2);

                editText1.setVisibility(View.INVISIBLE);
                editText2.setVisibility(View.INVISIBLE);
                confirmBtn.setVisibility(View.INVISIBLE);
                addBtn.setVisibility(View.VISIBLE);
                removeBtn.setVisibility(View.VISIBLE);
            }
        });

        //code for remove button
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextRemove.setVisibility(View.VISIBLE);

                confirmBtn2.setVisibility(View.VISIBLE);
                addBtn.setVisibility(View.INVISIBLE);
                removeBtn.setVisibility(View.INVISIBLE);
            }
        });

        //code for confirm button 2
        confirmBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(editTextRemove);

                buildRecyclerView();

                editTextRemove.setVisibility(View.INVISIBLE);
                confirmBtn2.setVisibility(View.INVISIBLE);
                addBtn.setVisibility(View.VISIBLE);
                removeBtn.setVisibility(View.VISIBLE);
            }
        });

        //code for save button
        ImageButton saveBtn = (ImageButton) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

    }

    private void insertItem(EditText text1, EditText text2){
        String name = text1.getText().toString();
        String description = text2.getText().toString();
        itemList.add(new Item(R.drawable.black, name, description, 50));
        myAdapter.notifyItemInserted(itemList.size() + 1);
    }

    private void removeItem(EditText text){
        int position = Integer.parseInt(text.getText().toString());
        itemList.remove(position - 1);
        myAdapter.notifyItemRemoved(position - 1);
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

        if (itemList == null || itemList.size() < 1){
            itemList = new ArrayList<>();
            createItemList();
        }
    }

    private void createItemList(){
        itemList.add(new Item(R.drawable.black, "Black Yoga Mat", "Black mat description.", 50));
        itemList.add(new Item(R.drawable.blue, "Blue Yoga Mat", "Blue mat description.", 50));
        itemList.add(new Item(R.drawable.brown, "Brown Yoga Mat", "Brown mat description", 50));
        itemList.add(new Item(R.drawable.green, "Green Yoga Mat", "Green mat description", 50));
        itemList.add(new Item(R.drawable.grey, "Grey Yoga Mat", "Grey mat description", 50));
        itemList.add(new Item(R.drawable.purple, "Purple Yoga Mat", "Purple mat description", 50));
        itemList.add(new Item(R.drawable.red, "Red Yoga Mat", "Red mat description", 50));
    }

    private void buildRecyclerView(){
        myRecyclerView = findViewById(R.id.myRecyclerView);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myAdapter = new MyAdapter(itemList);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                    Intent startIntent = new Intent(getApplicationContext(), InventoryDetails.class);
                    startIntent.putExtra("position", position);
                    startActivity(startIntent);
            }
        });
    }
}
