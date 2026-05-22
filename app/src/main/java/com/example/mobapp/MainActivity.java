package com.example.mobapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EventStorage storage;
    private EventAdapter adapter;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        storage = new EventStorage(this);

        adapter = new EventAdapter((event, complete) -> {
            storage.setComplete(event.getId(), complete);
            loadEvents();
        });

        RecyclerView recyclerView = findViewById(R.id.eventsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        emptyText = findViewById(R.id.emptyText);

        FloatingActionButton addButton = findViewById(R.id.addEventButton);
        addButton.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, AddEventActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    private void loadEvents() {
        List<Event> events = storage.getEvents();
        adapter.submitList(events);

        if (events.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }
}