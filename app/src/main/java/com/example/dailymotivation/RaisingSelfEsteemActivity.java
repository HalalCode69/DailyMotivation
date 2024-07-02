package com.example.dailymotivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.Intent;

import java.util.List;
import java.util.Locale;

public class RaisingSelfEsteemActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private DatabaseHelper db;
    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raising_selfesteem);
        // Initialize TextToSpeech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home_button);

        db = new DatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.item_list_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Entry> entries = db.getAllEntries();
        // Correctly set up the adapter with the TextToSpeech instance
        adapter = new EntryAdapter(entries, tts);
        recyclerView.setAdapter(adapter);

        setupUI();
    }
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    private void deleteSelectedEntry() {
        // Placeholder for delete logic
        // You might show a dialog here to select and confirm deletion of an entry
    }
    private void repeatSelectedEntry(EditText repeatCountEditText) {
        int repeatCount;
        try {
            repeatCount = Integer.parseInt(repeatCountEditText.getText().toString());
        } catch (NumberFormatException e) {
            repeatCount = 0; // Default or error handling
        }
        // Placeholder for repeat logic
        // Implement the logic to repeat the selected entry `repeatCount` times with a 2-second delay between each
    }
    private void setupUI() {
        Button createVoiceButton = findViewById(R.id.create_voice_button);
        LinearLayout buttonsLinearLayout = findViewById(R.id.buttons_linear_layout);
        EditText titleEditText = findViewById(R.id.title_edit_text);
        EditText textEditText = findViewById(R.id.text_edit_text);
        Button cancelButton = findViewById(R.id.cancel_button);
        Button saveButton = findViewById(R.id.save_button);


        Button deleteButton = findViewById(R.id.delete_button);
        Button repeatButton = findViewById(R.id.repeat_button);
        EditText repeatCountEditText = findViewById(R.id.repeat_count_edit_text);
        deleteButton.setOnClickListener(v -> deleteSelectedEntry());
        repeatButton.setOnClickListener(v -> repeatSelectedEntry(repeatCountEditText));


        createVoiceButton.setOnClickListener(v -> toggleInputFields(titleEditText, textEditText, buttonsLinearLayout));

        saveButton.setOnClickListener(v -> {
            saveEntry(titleEditText, textEditText);
            toggleInputFields(titleEditText, textEditText, buttonsLinearLayout);
        });

        cancelButton.setOnClickListener(v -> clearAndHideInputFields(titleEditText, textEditText, buttonsLinearLayout));
    }

    private void toggleInputFields(EditText titleEditText, EditText textEditText, LinearLayout buttonsLinearLayout) {
        if (titleEditText.getVisibility() == View.GONE) {
            titleEditText.setVisibility(View.VISIBLE);
            textEditText.setVisibility(View.VISIBLE);
            buttonsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            clearAndHideInputFields(titleEditText, textEditText, buttonsLinearLayout);
        }
    }

    private void saveEntry(EditText titleEditText, EditText textEditText) {
        String title = titleEditText.getText().toString();
        String text = textEditText.getText().toString();
        db.addEntry(title, text);

        List<Entry> updatedEntries = db.getAllEntries();
        adapter.setEntries(updatedEntries);
        adapter.notifyDataSetChanged();

        titleEditText.setText("");
        textEditText.setText("");
    }

    private void clearAndHideInputFields(EditText titleEditText, EditText textEditText, LinearLayout buttonsLinearLayout) {
        titleEditText.setVisibility(View.GONE);
        textEditText.setVisibility(View.GONE);
        buttonsLinearLayout.setVisibility(View.GONE);
        titleEditText.setText("");
        textEditText.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}