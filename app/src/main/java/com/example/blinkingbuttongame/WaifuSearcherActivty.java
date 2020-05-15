package com.example.blinkingbuttongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blinkingbuttongame.R;
import com.example.blinkingbuttongame.ui.WaifuApiService;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WaifuSearcherActivty extends AppCompatActivity {

    Button search;
    ImageView display;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waifu_searcher_activty);

        wireWidgets();

        setListeners();
    }

    public void wireWidgets()
    {
        search = findViewById(R.id.button_waifulayout_search);
        display = findViewById(R.id.imageView_waifulayout_display);
    }

    public void setListeners()
    {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLink("19");
            }
        });
    }

    public void callLink(String number)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WaifuApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WaifuApiService waifuService = retrofit.create(WaifuApiService.class);
        Call<AnimeClass> animeCall = waifuService.searchInfo(number);

        animeCall.enqueue(new Callback<AnimeClass>() {
            @Override
            public void onResponse(Call<AnimeClass> call, Response<AnimeClass> response) {

                    Log.e("THIS", "SOMETHING HAPPENED");
                if (!response.isSuccessful()) {
                    Log.e("API Error", "BAD STUFF HAPPENED, probably character doesnt exist");
                    Toast.makeText(WaifuSearcherActivty.this, "hi", Toast.LENGTH_SHORT).show();
                    return;
                }

                AnimeClass anime = response.body();

              //  search.setText(anime.returnURL(1));
                loadImage(anime.returnURL(1),display);

            }

            @Override
            public void onFailure(Call<AnimeClass> call, Throwable t) {
                Toast.makeText(WaifuSearcherActivty.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "BIG ERROR");
                Log.e("Error", t.getMessage());
            }
        });
    }

    public void loadImage(String url, ImageView image)
    {
        Picasso.get().load(url).into(image);
    }

}
