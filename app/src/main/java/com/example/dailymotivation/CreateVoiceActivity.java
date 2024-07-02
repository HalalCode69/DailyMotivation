package com.example.dailymotivation;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class CreateVoiceActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText textEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raising_selfesteem);
        titleEditText = findViewById(R.id.title_edit_text);
        textEditText = findViewById(R.id.text_edit_text);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String title = titleEditText.getText().toString();
                String text = textEditText.getText().toString();

                // Save to database
                // ...

                // Close activity
                finish();
            }
        });

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Close activity
                finish();
            }
        });
    }
}