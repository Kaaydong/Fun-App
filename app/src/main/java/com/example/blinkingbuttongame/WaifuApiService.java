package com.example.blinkingbuttongame;

import com.example.blinkingbuttongame.AnimeClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WaifuApiService {

    String BASE_URL = "https://api.jikan.moe/v3/";

    @GET("character/{number}/pictures")
    Call<AnimeClass> searchInfo(@Path("number") String number);

}
