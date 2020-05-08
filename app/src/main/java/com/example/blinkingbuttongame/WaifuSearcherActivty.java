package com.example.blinkingbuttongame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.blinkingbuttongame.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class WaifuSearcherActivty extends AppCompatActivity {

    Button search;
    ImageView display;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waifu_searcher_activty);

        wireWidgets();
        turnUrlToString();
    }

    public void wireWidgets()
    {
        search = findViewById(R.id.button_waifulayout_search);
        display = findViewById(R.id.imageView_waifulayout_display);
    }


    public void turnUrlToString() {
        try {
            // get URL content

            String a = "https://myanimelist.net/character/36828/";
            url = new URL(a);
            URLConnection conn = url.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                Log.d("TEST",inputLine);

            }
            br.close();

            Log.d("Test", "DONE");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
