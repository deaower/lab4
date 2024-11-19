package com.example.lab4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Track.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TrackDao trackDao();
}

