package com.example.lab4;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TrackAdapter trackAdapter;
    private TrackRepository trackRepository;
    private Handler handler;
    private Runnable fetchTask;
    private final int FETCH_INTERVAL = 20000; // Интервал 20 секунд

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Проверка подключения к интернету при запуске
        if (!isOnline()) {
            Toast.makeText(this, "Нет подключения к интернету. Запуск в автономном режиме.", Toast.LENGTH_LONG).show();
        } else {
            startFetchingSongs();
        }

        // Инициализация RecyclerView и адаптера
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTracks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trackAdapter = new TrackAdapter();
        recyclerView.setAdapter(trackAdapter);

        // Инициализация репозитория и загрузка данных из БД
        trackRepository = new TrackRepository(this);
        trackRepository.getAllTracks().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                // Обновляем данные в адаптере при изменении данных в базе
                trackAdapter.setTracks(tracks);
            }
        });
    }

    // Метод для проверки подключения к интернету
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    // Метод для запуска периодического опроса сервера
    private void startFetchingSongs() {
        handler = new Handler();
        fetchTask = new Runnable() {
            @Override
            public void run() {
                // Запрашиваем данные у сервера и добавляем их в базу, если изменились
                trackRepository.fetchAndAddCurrentSong();

                // Перезапускаем задачу через 20 секунд
                handler.postDelayed(this, FETCH_INTERVAL);
            }
        };
        handler.post(fetchTask); // Начинаем выполнение задачи сразу
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Очищаем handler, чтобы избежать утечек памяти
        if (handler != null && fetchTask != null) {
            handler.removeCallbacks(fetchTask);
        }
    }
}


