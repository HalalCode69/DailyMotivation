package com.example.dailymotivation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.content.Intent;

public class ItemDetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView textTextView;
    private Button playButton;
    private Button stopButton;
    private Button loopButton;
    private Button loopX2Button;
    private Button loopX4Button;
    private Button loopInfinityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        titleTextView = findViewById(R.id.title_text_view);
        textTextView = findViewById(R.id.text_text_view);
        playButton = findViewById(R.id.play_button);
        stopButton = findViewById(R.id.stop_button);
        loopButton = findViewById(R.id.loop_button);
        loopX2Button = findViewById(R.id.loop_x2_button);
        loopX4Button = findViewById(R.id.loop_x4_button);
        loopInfinityButton = findViewById(R.id.loop_infinity_button);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");

        titleTextView.setText(title);
        textTextView.setText(text);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play button clicked, add your code here
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop button clicked, add your code here
            }
        });

        loopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop button clicked, add your code here
            }
        });

        loopX2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop x2 button clicked, add your code here
            }
        });

        loopX4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop x4 button clicked, add your code here
            }
        });

        loopInfinityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop infinity button clicked, add your code here
            }
        });
    }
}