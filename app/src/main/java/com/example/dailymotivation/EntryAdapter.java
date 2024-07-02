package com.example.dailymotivation;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.speech.tts.Voice;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private List<Entry> entries;
    private TextToSpeech tts;

    // Modified constructor to accept TextToSpeech instance
    public EntryAdapter(List<Entry> entries, TextToSpeech tts) {
        this.entries = entries;
        this.tts = tts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry, parent, false);
        return new ViewHolder(view);
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    // Assuming you have a method detectLanguage(String text) that returns a Locale for the detected language
    private Locale detectLanguage(String text) {
        // This is a placeholder for actual language detection logic
        // You might use a third-party library or service for this purpose
        Pattern pattern = Pattern.compile("[\\u0400-\\u04FF]+"); // Pattern for Cyrillic script
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) { // Simple check for Cyrillic script, not accurate
            return new Locale("ru");
        } else {
            return Locale.ENGLISH;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entry entry = entries.get(position);
        holder.title.setText(entry.getTitle());
        holder.itemView.setOnClickListener(v -> {
            String textToSpeak = entry.getText();
            Locale language = detectLanguage(textToSpeak);
            tts.setPitch(0.85f);
             // Continue with setting the language and speaking the text
            if (tts != null && !textToSpeak.isEmpty()) {
                int result = tts.setLanguage(language);
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");
                } else {
                    // Handle case where language is not supported
                    tts.setLanguage(Locale.ENGLISH);
                    tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "UniqueID");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.textView_entry_title);
        }
    }
}