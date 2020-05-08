package com.example.blinkingbuttongame.ui;

import com.example.blinkingbuttongame.AnimeClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WaifuApiService {

    String BASE_URL = "https://myanimelist.net/";

    @GET("character/{id}")
    Call<List<AnimeClass>> searchInfo(@Path("id") int id);

}
