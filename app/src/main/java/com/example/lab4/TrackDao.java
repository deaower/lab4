package com.example.lab4;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.lifecycle.LiveData;

import java.util.List;

@Dao
public interface TrackDao {
    // Метод для вставки записи
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrack(Track track);

    // Метод для получения всех треков
    @Query("SELECT * FROM track ORDER BY timestamp DESC")
    LiveData<List<Track>> getAllTracks();

    // Метод для получения последней записи
    @Query("SELECT * FROM track ORDER BY timestamp DESC LIMIT 1")
    Track getLastTrack();
}




