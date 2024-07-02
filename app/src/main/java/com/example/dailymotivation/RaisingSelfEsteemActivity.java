package com.example.dailymotivation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class RaisingSelfEsteemActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private DatabaseHelper db;
    private EntryAdapter adapter;
    private int repeatCount = 1; // Default repeat count


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
        adapter = new EntryAdapter(entries, tts, repeatCount);
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

        Button lockButton = findViewById(R.id.lock_button);
        lockButton.setOnClickListener(this::onLockButtonClick);
        Button repeatButton = findViewById(R.id.repeat_button);
        EditText repeatCountEditText = findViewById(R.id.repeat_count_edit_text);
        LinearLayout repeatCountLayout = findViewById(R.id.linearLayout_repeatCount);

        repeatButton.setOnClickListener(v -> {
            // Toggle visibility of the entire layout, not just the EditText
            if (repeatCountLayout.getVisibility() == View.GONE) {
                repeatCountLayout.setVisibility(View.VISIBLE);
            } else {
                repeatCountLayout.setVisibility(View.GONE);
            }
        });

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> deleteSelectedEntry());

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
    public void onLockButtonClick(View view) {
        updateRepeatCount(); // This method already updates the adapter's repeat count based on the textbox
    }
    public void updateRepeatCount() {
        EditText repeatCountEditText = findViewById(R.id.repeat_count_edit_text);
        try {
            int newRepeatCount = Integer.parseInt(repeatCountEditText.getText().toString());
            adapter.setRepeatCount(newRepeatCount);
        } catch (NumberFormatException e) {
            // Handle invalid input or set a default value
            adapter.setRepeatCount(1); // Default to 1 or consider showing an error message
        }
    }
    public void onConfirmRepeatCountButtonClick(View view) {
        EditText repeatCountEditText = findViewById(R.id.repeat_count_edit_text);
        try {
            int newRepeatCount = Integer.parseInt(repeatCountEditText.getText().toString());
            EntryAdapter adapter = (EntryAdapter) ((RecyclerView) findViewById(R.id.item_list_recyclerview)).getAdapter();
            if (adapter != null) {
                adapter.setRepeatCount(newRepeatCount);
                Toast.makeText(this, "Repeat count set to " + newRepeatCount, Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid repeat count", Toast.LENGTH_SHORT).show();
        }

        // Close the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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