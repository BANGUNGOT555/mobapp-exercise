package com.example.mobapp;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public interface OnEventCheckedChangeListener {
        void onEventCheckedChanged(Event event, boolean complete);
    }

    private final List<Event> events = new ArrayList<>();
    private final OnEventCheckedChangeListener listener;

    public EventAdapter(OnEventCheckedChangeListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Event> updatedEvents) {
        events.clear();
        events.addAll(updatedEvents);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(events.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView detailsText;
        private final CheckBox completeCheckbox;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.eventTitleText);
            detailsText = itemView.findViewById(R.id.eventDetailsText);
            completeCheckbox = itemView.findViewById(R.id.completeCheckbox);
        }

        void bind(Event event, OnEventCheckedChangeListener listener) {
            titleText.setText(event.getTitle());

            String details = event.getDate();

            if (!event.getNote().isEmpty()) {
                if (details.isEmpty()) {
                    details = event.getNote();
                } else {
                    details = details + " - " + event.getNote();
                }
            }

            detailsText.setText(details);
            detailsText.setVisibility(details.isEmpty() ? View.GONE : View.VISIBLE);

            completeCheckbox.setOnCheckedChangeListener(null);
            completeCheckbox.setChecked(event.isComplete());
            completeCheckbox.setOnCheckedChangeListener((buttonView, isChecked) ->
                    listener.onEventCheckedChanged(event, isChecked));

            if (event.isComplete()) {
                titleText.setPaintFlags(titleText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                titleText.setPaintFlags(titleText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}