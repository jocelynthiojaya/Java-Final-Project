package com.example.recyclerviewproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    private ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        //connect objects with their id
        final TextView orderName = (TextView) findViewById(R.id.orderName);
        final TextView orderDescription = (TextView) findViewById(R.id.orderDescription);
        final TextView orderItem = (TextView) findViewById(R.id.orderItem);

        final Button addOrderItemBtn = (Button) findViewById(R.id.addOrderItemBtn);
        final Button removeOrderItemBtn = (Button) findViewById(R.id.removeOrderItemBtn);
        final Button confirmOrderItemBtn = (Button) findViewById(R.id.confirmAddItemBtn);
        final Button confirmRemoveItemBtn = (Button) findViewById(R.id.confirmRemoveItemBtn);
        final ImageButton saveBtnOrderDetails = (ImageButton) findViewById(R.id.saveBtnOrderDetails);

        final EditText editTextItemName = (EditText) findViewById(R.id.editTextItemName);
        final EditText editTextItemQty = (EditText) findViewById(R.id.editTextItemQty);
        final EditText editTextRemovePosition = (EditText) findViewById(R.id.editTextRemovePosition);


        //load the array list data and the passed position
        //set the text to the according data
        loadData();
        final int position = getIntent().getExtras().getInt("position");
        try {
            setInfo(orderName, orderDescription, orderItem, position);
        }
        catch (IndexOutOfBoundsException e){
            openDialog();
        }


        //set visibilities
        confirmOrderItemBtn.setVisibility(View.INVISIBLE);
        confirmRemoveItemBtn.setVisibility(View.INVISIBLE);
        editTextItemName.setVisibility(View.INVISIBLE);
        editTextItemQty.setVisibility(View.INVISIBLE);
        editTextRemovePosition.setVisibility(View.INVISIBLE);

        //add item button
        addOrderItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextItemName.setVisibility(View.VISIBLE);
                editTextItemQty.setVisibility(View.VISIBLE);
                confirmOrderItemBtn.setVisibility(View.VISIBLE);

                addOrderItemBtn.setVisibility(View.INVISIBLE);
                removeOrderItemBtn.setVisibility(View.INVISIBLE);
            }
        });

        //confirm add item button
        confirmOrderItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertOrderItem(editTextItemName, editTextItemQty, position);
                setInfo(orderName, orderDescription, orderItem, position);

                confirmOrderItemBtn.setVisibility(View.INVISIBLE);
                editTextItemName.setVisibility(View.INVISIBLE);
                editTextItemQty.setVisibility(View.INVISIBLE);

                addOrderItemBtn.setVisibility(View.VISIBLE);
                removeOrderItemBtn.setVisibility(View.VISIBLE);
            }
        });

        //remove item button
        removeOrderItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextRemovePosition.setVisibility(View.VISIBLE);
                confirmRemoveItemBtn.setVisibility(View.VISIBLE);

                addOrderItemBtn.setVisibility(View.INVISIBLE);
                removeOrderItemBtn.setVisibility(View.INVISIBLE);
            }
        });

        //confirm remove item button
        confirmRemoveItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOrderItem(editTextRemovePosition, position);
                setInfo(orderName, orderDescription, orderItem, position);

                editTextRemovePosition.setVisibility(View.INVISIBLE);
                confirmRemoveItemBtn.setVisibility(View.INVISIBLE);

                addOrderItemBtn.setVisibility(View.VISIBLE);
                removeOrderItemBtn.setVisibility(View.VISIBLE);
            }
        });

        //save button
        saveBtnOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }


    private void setInfo(TextView name, TextView description, TextView orderItem, int position){
        name.setText(orderList.get(position).getName());
        description.setText(orderList.get(position).getDescription());

        ArrayList<Item> customerItems;
        customerItems = orderList.get(position).getItems();

        if (customerItems.size() < 1 || customerItems == null) {
            orderItem.setText("No items");
        }
        else {
            String itemString = "";
            for (int i = 0; i < customerItems.size(); i++) {
                Item current = customerItems.get(i);
                itemString += ((i + 1) + ". " + current.toString() + "\n");
                orderItem.setText(itemString);
            }
        }
    }

    private void insertOrderItem(EditText text1, EditText text2, int position) {
        String name = text1.getText().toString();
        int qty = Integer.parseInt(text2.getText().toString());

        orderList.get(position).addItem(name, qty);
    }

    private void removeOrderItem(EditText text, int position){
        int pos = Integer.parseInt(text.getText().toString());
        orderList.get(position).removeItem(pos-1);
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
    }

    public void openDialog(){
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.show(getSupportFragmentManager(), "error dialog");
    }
}
