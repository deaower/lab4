# Лабораторная работа №4. Взаимодействие с сервером
- _Выполнила:_ Толстова Кристина
- _Язык программирования:_ Java

## Описание приложения
Это Android-приложение,сохраняющее статистику проигрываемых песен, для
сохранения песни и названия была создана база данных, содержащая
таблицу со следующими полями:
1) ID
2) Исполнитель
3) Название трека
4) Время внесения записи

## Основные функции приложения
- Проверка интернет-соединения: При запуске приложения осуществляется проверка наличия активного соединения с интернетом. В случае его отсутствия, приложение переходит в оффлайн-режим, и пользователь получает соответствующее уведомление.
- Хранение данных в локальной базе: Полученная информация (имя исполнителя, название трека, время) сохраняется в локальной базе данных.
- Отображение данных на экране: Вся сохраненная информация из базы данных представляется на экране в виде списка (ListView), с обновлением данных через использование SimpleCursorAdapter.

## Составляющие приложения
1) MainActivity - Основной экран приложения
Этот класс выполняет проверку сети, запускает асинхронную задачу для получения данных и отображает список треков на экране.

Проверка подключения к интернету:
``` java
private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
``` 
Загрузка данных из БД и отображение в ListView:
``` java
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
}
```
2) Хранение данных в локальной базе
``` java
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
```
3) Данные из базы данных выводятся в ListView с помощью SimpleCursorAdapter.

