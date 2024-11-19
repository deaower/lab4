package com.example.lab4;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("api_get_current_song.php")
    Call<ApiResponse> getCurrentSong(
            @Field("login") String login,
            @Field("password") String password
    );

    // Вспомогательный класс для обработки ответа от API
    class ApiResponse {
        private String result;
        private String info;

        public String getResult() { return result; }
        public String getInfo() { return info; }
    }
}


