package com.example.dailymotivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.Intent;

public class RaisingSelfEsteemActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click here
                Intent intent = new Intent(RaisingSelfEsteemActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Button createVoiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raising_selfesteem);

        Button createVoiceButton = findViewById(R.id.create_voice_button);
        LinearLayout buttonsLinearLayout = findViewById(R.id.buttons_linear_layout);
        EditText titleEditText = findViewById(R.id.title_edit_text);
        EditText textEditText = findViewById(R.id.text_edit_text);
        Button cancelButton = findViewById(R.id.cancel_button);

        createVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEditText.getVisibility() == View.GONE) {
                    titleEditText.setVisibility(View.VISIBLE);
                    textEditText.setVisibility(View.VISIBLE);
                    buttonsLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    titleEditText.setVisibility(View.GONE);
                    textEditText.setVisibility(View.GONE);
                    buttonsLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleEditText.setVisibility(View.GONE);
                textEditText.setVisibility(View.GONE);
                buttonsLinearLayout.setVisibility(View.GONE);
                titleEditText.setText("");
                textEditText.setText("");
            }
        });

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar); // Set the Toolbar as the ActionBar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the home button
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_button); // Set the home icon
    }

    // ... other methods
}