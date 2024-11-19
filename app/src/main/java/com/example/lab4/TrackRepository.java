package com.example.lab4;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackRepository {
    private final TrackDao trackDao;
    private final ApiService apiService;

    public TrackRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "megabyte_radio_db").build();
        trackDao = db.trackDao();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://media.ifmo.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    // Метод для получения всех треков в виде LiveData
    public LiveData<List<Track>> getAllTracks() {
        return trackDao.getAllTracks();
    }

    public void fetchAndAddCurrentSong() {
        apiService.getCurrentSong("4707login", "4707pass").enqueue(new Callback<ApiService.ApiResponse>() {
            @Override
            public void onResponse(Call<ApiService.ApiResponse> call, Response<ApiService.ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getResult())) {
                    String[] songInfo = response.body().getInfo().split(" – ");
                    String artist = songInfo[0];
                    String title = songInfo[1];
                    long timestamp = System.currentTimeMillis();

                    // Сохраняем песню в базу данных, если она отличается от последней записи
                    new Thread(() -> {
                        Track lastTrack = trackDao.getLastTrack();
                        if (lastTrack == null || !lastTrack.getTitle().equals(title)) {
                            Track track = new Track(artist, title, timestamp);
                            trackDao.insertTrack(track);
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ApiService.ApiResponse> call, Throwable t) {
                // Обработка ошибки, например, вывод в лог
            }
        });
    }
}



