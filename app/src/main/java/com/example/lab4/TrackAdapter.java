package com.example.lab4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {
    private List<Track> tracks;

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = tracks.get(position);
        holder.artistTextView.setText(track.getArtist());
        holder.titleTextView.setText(track.getTitle());

        // Форматируем timestamp в удобочитаемую строку
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(track.getTimestamp());
        holder.timestampTextView.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return (tracks != null) ? tracks.size() : 0;
    }

    static class TrackViewHolder extends RecyclerView.ViewHolder {
        private final TextView artistTextView;
        private final TextView titleTextView;
        private final TextView timestampTextView;

        public TrackViewHolder(View itemView) {
            super(itemView);
            artistTextView = itemView.findViewById(R.id.textViewArtist);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            timestampTextView = itemView.findViewById(R.id.textViewTimestamp);
        }
    }
}

