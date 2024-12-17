package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreSave {

    private Preferences scores = Gdx.app.getPreferences("playerScores");

    public void updateScores(String user, Integer score){

    }

    public Preferences getScores(){
        return scores;
    }

    public Integer getUserScore(String user) {
        return scores.getInteger(user, 0);
    }

    public void callFlush() {
        scores.flush();
    }

}
