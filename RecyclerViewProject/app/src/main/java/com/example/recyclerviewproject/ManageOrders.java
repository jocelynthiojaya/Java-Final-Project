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

public class ManageOrders extends AppCompatActivity {

    private ArrayList<Order> orderList;

    private RecyclerView myRecyclerView;
    private MyAdapterOrders myAdapter;
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
        setContentView(R.layout.activity_manage_orders);

        loadData();
        buildRecyclerView();

        //connect objects with their id
        final EditText editText1 = (EditText) findViewById(R.id.editTextName2);
        final EditText editText2 = (EditText) findViewById(R.id.editTextDescription2);
        final EditText editTextRemove = (EditText) findViewById(R.id.editTextRemove2);

        final Button confirmBtn = (Button) findViewById(R.id.confirmAddBtn2);
        final Button confirmBtn2 = (Button) findViewById(R.id.confirmRemoveBtn2);
        final Button addBtn = (Button) findViewById(R.id.addBtn2);
        final Button removeBtn = (Button) findViewById(R.id.removeBtn2);
        final ImageButton saveBtn = (ImageButton) findViewById(R.id.saveBtn2);

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
        orderList.add(new Order(name, description));
        myAdapter.notifyItemInserted(orderList.size() + 1);
    }

    private void removeItem(EditText text){
        int position = Integer.parseInt(text.getText().toString());
        orderList.remove(position - 1);
        myAdapter.notifyItemRemoved(position - 1);
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orderList);
        editor.putString("order list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("order list", null);
        Type type = new TypeToken<ArrayList<Order>>() {}.getType();
        orderList = gson.fromJson(json, type);

        if (orderList == null || orderList.size() < 1){
            orderList = new ArrayList<>();
            createOrderList();
        }
    }

    private void createOrderList(){
        orderList.add(new Order("Name", "Address"));
        orderList.add(new Order("Name 2", "Address 2"));
        orderList.add(new Order("Name 3", "Address 3"));
    }

    private void buildRecyclerView(){
        myRecyclerView = findViewById(R.id.myRecyclerView2);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myAdapter = new MyAdapterOrders(orderList);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapterOrders.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //orderList.get(position).setDescription("Clicked");
                //myAdapter.notifyItemChanged(position);

                Intent startIntent = new Intent(getApplicationContext(), OrderDetails.class);
                startIntent.putExtra("position", position);
                startActivity(startIntent);
            }
        });
    }
}
