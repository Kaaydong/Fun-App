package com.example.blinkingbuttongame;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.animation.Animation;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimerTask;

public class BlinkingGame {

    int roundNumber;
    String userSubmission;
    String pattern;
    boolean blinking;

    int primary, primaryDark, accent;
    int blinkTime;

    public BlinkingGame()
    { roundNumber = 1;
    userSubmission = "";
    pattern = "";
    blinking = false;
    blinkTime = 1000;

    primary = Color.parseColor("#5250DF");
    primaryDark = Color.parseColor("#3700B3");
    accent = Color.parseColor("#1FBF8D");

    generatePattern();
    }

    public String generatePattern()
    {
        pattern = "";
        for (int x=0; x<roundNumber; x++)
        {
            int spot = (int)(Math.random() * 5 + 1);
            String letter = "abcde".substring(spot-1,spot);

            pattern += letter;
        }
        return pattern;
    }

    public void buttonBlinkIndicator(Button button)
    {
        blinkEffect(button, Animation.ABSOLUTE, blinkTime, primary, accent, primary);
    }

    public void buttonBlinkAfterPress(Button button)
    {
        blinkEffect(button, Animation.ABSOLUTE, 200, primary, accent, primary);
    }

    public void blinkEffect(Button button, int repeatCount, int blinkingTime, int startingColor, int middleColor, int endingColor)
    {
        ObjectAnimator anime = ObjectAnimator.ofInt(button, "BackgroundColor", startingColor, middleColor, endingColor);
        anime.setDuration(blinkingTime);
        anime.setEvaluator(new ArgbEvaluator());
        anime.setRepeatMode(ValueAnimator.REVERSE);
        anime.setRepeatCount(repeatCount);
        anime.start();
    }

    //////////////////////////////////////////////////////////////////////

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
        userSubmission = "";
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

    public void updateBlinkTime()
    {
        blinkTime = (int)(blinkTime * (Math.pow(.98,roundNumber)));
    }

    public int getBlinkTime()
    {
        return blinkTime;
    }



}

