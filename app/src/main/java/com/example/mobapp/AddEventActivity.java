package com.example.mobapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddEventActivity extends AppCompatActivity {
    private EditText titleInput;
    private EditText dateInput;
    private EditText noteInput;
    private EventStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);

        storage = new EventStorage(this);

        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        noteInput = findViewById(R.id.noteInput);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> saveEvent());
    }

    private void saveEvent() {
        String title = titleInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String note = noteInput.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            titleInput.setError("Title is required");
            return;
        }

        Event event = new Event(title, note, date);
        storage.addEvent(event);

        Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}