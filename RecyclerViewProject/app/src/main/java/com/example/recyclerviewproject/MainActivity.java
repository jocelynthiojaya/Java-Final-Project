package com.example.recyclerviewproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button viewInventoryBtn = (Button) findViewById(R.id.viewInventoryBtn);
        viewInventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ViewInventory.class);
                startActivity(startIntent);
            }
        });

        Button manageOrdersBtn = (Button) findViewById(R.id.manageOrdersBtn);
        manageOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), ManageOrders.class);
                startActivity(startIntent);
            }
        });
    }
}
