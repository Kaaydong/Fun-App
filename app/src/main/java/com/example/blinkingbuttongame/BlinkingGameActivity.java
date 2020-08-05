package com.example.blinkingbuttongame;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class BlinkingGameActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonA, buttonB, buttonC, buttonD, buttonE;
    Button buttonIndicator, buttonRoundNumber, buttonStart, buttonSubmit, buttonHighScore;
    ImageButton backButton;

    Boolean didGameStart;
    BlinkingGame game;

    int letterCounter;

    private static final String FILE_NAME = "HighScore.txt";

    private List<String> urlList, nameList;

    String highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blinking_game_activity);

        didGameStart = false;

        wireWidgets();

        setListeners();

        setButtonStatus(false);
        buttonSubmit.setEnabled(false);

        getPastInfo();

        highScore = "0";
        readHighScoreFile();
        updateHighScoreBoard();
    }

    public void getPastInfo()
    {
        Intent listIntent = getIntent();
        urlList = listIntent.getStringArrayListExtra("url_list");
        nameList = listIntent.getStringArrayListExtra("name_list");
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
        buttonHighScore = findViewById(R.id.button_blink_highscore);

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
                    updateHighScore();
                    updateHighScoreBoard();
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
                    targetIntent.putStringArrayListExtra("first_url_list",(ArrayList<String>)urlList);
                    targetIntent.putStringArrayListExtra("first_name_list",(ArrayList<String>)nameList);
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

    public void writeHighScoreFile(int score)
    {
        String value = "" + score;
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(value.getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(fileOutputStream != null)
            {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readHighScoreFile()
    {
        FileInputStream fileInputStream = null;

        try{
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            String lines;
            while ((lines = bufferedReader.readLine()) != null){
                stringBuilder.append(lines).append("\n");
            }

            if(stringBuilder.toString() != null) {
                highScore = stringBuilder.toString();
            }
            else
            {
                highScore = "0";
            }

        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(fileInputStream != null)
            {
                try{ fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateHighScore()
    {
        if (Integer.parseInt(highScore.trim()) < game.getRoundNumber()) {
            writeHighScoreFile(game.getRoundNumber());
            readHighScoreFile();
        }

    }

    public void updateHighScoreBoard()
    {
        buttonHighScore.setText("Highscore: " + highScore);
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



