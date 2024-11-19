package com.example.lab4;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "track")
public class Track {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String artist;
    private String title;
    private long timestamp;

    // Default constructor
    public Track(String artist, String title, long timestamp) {
        this.artist = artist;
        this.title = title;
        this.timestamp = timestamp;
    }

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for artist
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for timestamp
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}


