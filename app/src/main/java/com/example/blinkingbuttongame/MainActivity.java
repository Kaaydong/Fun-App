package com.example.blinkingbuttongame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();

        setListeners();
    }

    public void wireWidgets()
    {
        startButton = findViewById(R.id.button_main_start);
    }

    public void setListeners()
    {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent targetIntent = new Intent(MainActivity.this, BlinkingGameActivity.class);
        startActivity(targetIntent);
        finish();
            }
        });
    }
}
