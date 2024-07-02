package com.example.dailymotivation;

import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.speech.tts.UtteranceProgressListener;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.speech.tts.Voice;
import android.widget.Toast;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {
    private List<Entry> entries;
    private TextToSpeech tts;

    // Modified constructor to accept TextToSpeech instance
    public EntryAdapter(List<Entry> entries, TextToSpeech tts, int repeatCount) {
        this.entries = entries;
        this.tts = tts;
        this.repeatCount = repeatCount; // Initialize repeatCount
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

    private int repeatCount = 2; // Field to store repeat count

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entry entry = entries.get(position);
        holder.title.setText(entry.getTitle());
        holder.itemView.setOnClickListener(v -> {
            String textToSpeak = entry.getText();
            Locale language = detectLanguage(textToSpeak);
            tts.setPitch(0.85f);
            if (tts != null && !textToSpeak.isEmpty()) {
                int result = tts.setLanguage(language);
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    final int[] count = {0}; // Counter for the number of times spoken
                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            // Implementation
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            if (count[0] < repeatCount - 1) {
                                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                    count[0]++;
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID" + count[0]);
                                    tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, params);
                                }, 3000); // 3-second delay before speaking again
                            }
                        }

                        @Override
                        public void onError(String utteranceId) {
                            // Implementation
                        }
                    });
                    // Start speaking for the first time
                    if (count[0] < repeatCount) {
                        HashMap<String, String> params = new HashMap<>();
                        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID" + count[0]);
                        tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, params);
                    }
                    // Show the repeat count as a Toast message
                    Toast.makeText(holder.itemView.getContext(), "Repeating " + repeatCount + " times", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsupported language
                }
            }
        });
    }
    public void setRepeatCount(int newRepeatCount) {
        this.repeatCount = newRepeatCount;
        // Notify the adapter if necessary, depending on your implementation
        notifyDataSetChanged();
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