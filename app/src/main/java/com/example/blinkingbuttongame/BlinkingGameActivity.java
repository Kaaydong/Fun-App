package com.example.blinkingbuttongame;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

        buttonIndicator.setBackgroundColor(Color.RED);
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
                if (didGameStart == false) // starts the game
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
                    game.buttonBlinkAfterPress(buttonA);
                    break;
                case R.id.button_blink_b:
                    game.editUserSubmission("b");
                    game.buttonBlinkAfterPress(buttonB);
                    break;
                case R.id.button_blink_c:
                    game.editUserSubmission("c");
                    game.buttonBlinkAfterPress(buttonC);
                    break;
                case R.id.button_blink_d:
                    game.editUserSubmission("d");
                    game.buttonBlinkAfterPress(buttonD);
                    break;
                case R.id.button_blink_e:
                    game.editUserSubmission("e");
                    game.buttonBlinkAfterPress(buttonE);
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
            game.updateBlinkTime();
            DisableButtons disableButtonsTask = new DisableButtons();
            disableButtonsTask.execute();
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
        protected Void doInBackground(Void... voids) {

            timer(game.getBlinkTime());
            letterCounter++;
            publishProgress(voids);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

                String letterInPattern = game.getLetterInPatten(letterCounter, letterCounter + 1);

                if (letterInPattern.equals("a")) {
                    game.buttonBlinkIndicator(buttonA);
                } else if (letterInPattern.equals("b")) {
                    game.buttonBlinkIndicator(buttonB);
                } else if (letterInPattern.equals("c")) {
                    game.buttonBlinkIndicator(buttonC);
                } else if (letterInPattern.equals("d")) {
                    game.buttonBlinkIndicator(buttonD);
                } else {
                    game.buttonBlinkIndicator(buttonE);
                }

            super.onProgressUpdate(values);
        }

    }

    private class DisableButtons extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {

            buttonStart.setEnabled(false);
            setButtonStatus(false);
            buttonIndicator.setBackgroundColor(Color.RED);

            letterCounter = -1;
            for (int i = 0; i < game.getRoundNumber(); i++)
            {
                ButtonBlinkingTask task = new ButtonBlinkingTask();
                task.execute();
            }

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            timer(game.getBlinkTime());
            publishProgress(voids);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            setButtonStatus(true);
            buttonIndicator.setBackgroundColor(Color.GREEN);
            buttonSubmit.setEnabled(true);
            super.onProgressUpdate(values);
        }
    }
}



