package com.example.blinkingbuttongame;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BlinkingGameActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonA, buttonB, buttonC, buttonD, buttonE;
    Button buttonIndicator, buttonRoundNumber, buttonStart, buttonSubmit;
    ImageButton backButton;

    Boolean didGameStart;
    BlinkingGame game;

    int letterCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blinking_game_activity);

        didGameStart = false;

        wireWidgets();

        setListeners();

        setButtonStatus(false);
        buttonSubmit.setEnabled(false);
    }

    public void wireWidgets()
    {
        buttonA = findViewById(R.id.button_blink_a);
        buttonB = findViewById(R.id.button_blink_b);
        buttonC = findViewById(R.id.button_blink_c);
        buttonD = findViewById(R.id.button_blink_d);
        buttonE = findViewById(R.id.button_blink_e);

        buttonIndicator = findViewById(R.id.button_blink_indicator);
        buttonRoundNumber = findViewById(R.id.button_blink_round);
        buttonStart = findViewById(R.id.button_blink_start);
        buttonSubmit = findViewById(R.id.button_blink_submit);

        backButton = (ImageButton)findViewById(R.id.imageButton_blink_back);
    }

    public void setListeners()
    {
        buttonA.setOnClickListener(BlinkingGameActivity.this);
        buttonB.setOnClickListener(BlinkingGameActivity.this);
        buttonC.setOnClickListener(BlinkingGameActivity.this);
        buttonD.setOnClickListener(BlinkingGameActivity.this);
        buttonE.setOnClickListener(BlinkingGameActivity.this);

        backButton.setOnClickListener(BlinkingGameActivity.this);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didGameStart == false)
                {
                    didGameStart = true;
                    game = new BlinkingGame();
                    buttonStart.setText("");
                    buttonRoundNumber.setText("" + game.getRoundNumber());
                    runBlinkSequence();
                }
                else
                {
                    game.nextRound();
                    buttonRoundNumber.setText("" + game.getRoundNumber());
                    runBlinkSequence();
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStart.setEnabled(true);
                if(game.match())
                {
                    buttonStart.setText("Next Match");
                }
                else
                {
                    buttonStart.setText("Retry");
                    didGameStart = false;
                    game = new BlinkingGame();
                }
            }
        });
    }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.button_blink_a:
                    game.editUserSubmission("a");
                    break;
                case R.id.button_blink_b:
                    game.editUserSubmission("b");
                    break;
                case R.id.button_blink_c:
                    game.editUserSubmission("c");
                    break;
                case R.id.button_blink_d:
                    game.editUserSubmission("d");
                    break;
                case R.id.button_blink_e:
                    game.editUserSubmission("e");
                    break;
                case R.id.imageButton_blink_back:
                    Intent targetIntent = new Intent(BlinkingGameActivity.this,MainActivity2.class);
                    startActivity(targetIntent);
                    finish();
                    break;
            }
        }

        public void runBlinkSequence()
        {
            buttonStart.setEnabled(false);
            setButtonStatus(false);
            buttonIndicator.setBackgroundColor(Color.RED);
            letterCounter = -1;
            for (int i = 0; i < game.getRoundNumber(); i++)
            {
                ButtonBlinkingTask task = new ButtonBlinkingTask();
                task.execute();
            }
            setButtonStatus(true);
            buttonIndicator.setBackgroundColor(Color.GREEN);
            buttonSubmit.setEnabled(true);

            testers();
        }

        public void setButtonStatus(boolean b) // true enables, false disables
        {
            buttonA.setEnabled(b);
            buttonB.setEnabled(b);
            buttonC.setEnabled(b);
            buttonD.setEnabled(b);
            buttonE.setEnabled(b);
        }

        public void testers()
        {
            buttonA.setText(game.getPattern());
        }


    public void timer(long milli)
    {
        try {
            Thread.sleep(milli);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private class ButtonBlinkingTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onProgressUpdate(Void... values) {

            String letterInPattern = game.getLetterInPatten(letterCounter,letterCounter+1);

            if(letterInPattern.equals("a"))
            {
                game.buttonBlink(buttonA);
            }
            else if(letterInPattern.equals("b"))
            {
                game.buttonBlink(buttonB);
            }
            else if(letterInPattern.equals("c"))
            {
                game.buttonBlink(buttonC);
            }
            else if(letterInPattern.equals("d"))
            {
                game.buttonBlink(buttonD);
            }
            else
            {
                game.buttonBlink(buttonE);
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            timer(1000);
            letterCounter++;
            publishProgress(voids);
            return null;
        }
    }
}



