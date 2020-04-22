package com.example.blinkingbuttongame;

import android.graphics.Color;
import android.widget.Button;

import java.util.TimerTask;

public class BlinkingGame {

    int roundNumber;
    String userSubmission;
    String pattern;
    boolean blinking;

    public BlinkingGame()
    { roundNumber = 1;
    userSubmission = "";
    pattern = "";
    blinking = false;
    generatePattern();
    }

    public String generatePattern()
    {
        pattern = "";
        for (int x=0; x<roundNumber; x++)
        {
            int spot = (int)Math.random() * 5 + 1;
            String letter = "abcde".substring(spot-1,spot);

            pattern += letter;
        }
        return pattern;
    }

    public void buttonBlink(Button button)
    {
        button.setBackgroundColor(Color.GREEN);
        timer(1000);
        button.setBackgroundColor(Color.GRAY);
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

    public boolean match()
    {
        if (userSubmission.equals(pattern))
        {
            return true;
        }
        return false;
    }

    public void nextRound() 
    {
        roundNumber++;
        generatePattern();
    }

    public int getRoundNumber()
    {
        return roundNumber;
    }

    public String getUserSubmission()
    {
        return userSubmission;
    }

    public void editUserSubmission(String letter)
    {
        userSubmission += letter;
    }

    public String getPattern()
    {
        return pattern;
    }

    public String getLetterInPatten(int pos1, int pos2)
    {
        return pattern.substring(pos1,pos2);
    }
}

