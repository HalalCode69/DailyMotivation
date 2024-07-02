package com.example.dailymotivation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;

import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Daily Motivation");
        // Get references to the buttons
        Button raisingSelfEsteemButton = findViewById(R.id.raising_self_esteem_button);
        Button gettingMotivatedButton = findViewById(R.id.getting_motivated_button);
        Button brainMechanismsButton = findViewById(R.id.brain_mechanisms_button);

        // Set up button click listeners
        raisingSelfEsteemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RaisingSelfEsteemActivity.class);
                startActivity(intent);
            }
        });

        gettingMotivatedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle getting motivated button click
                // TODO: Implement logic to play audio tape or display affirmations
            }
        });

        brainMechanismsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle brain mechanisms button click
                // TODO: Implement logic to display explanation of brain mechanisms
            }
        });
    }
}