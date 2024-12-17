package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Class used for saving user scores to a preferences file, "playerScores".
 */
public class ScoreSave {

    /**
     * Accesses preferences file if it exists, otherwise creates it.
     */
    private Preferences scores = Gdx.app.getPreferences("playerScores");

    /**
     * <p>Updates playerScores preferences file:</p>
     * <p>If user is not present within file, creates index pair containing the user and their
     * score.</p>
     * <p>If user is present within file, checks if new score is greater than previously recorded
     * score {@link #getUserScore(String)}, and overwrites preferences with new score
     * if this is the case.</p>
     * <p>If score is updated, flushes preferences file to save changes. {@link #callFlush()} </p>
     *
     * @param user {@link String} User's name
     * @param score {@link Integer} User's new score
     */
    public void updateScores(String user, Integer score){
        if (scores.contains(user)) {
            Integer highScore = getUserScore(user);
            if (highScore.compareTo(score) < 0) {
                System.out.println("New score is greater than previous score.");
                scores.putInteger(user, score);
                System.out.println("Updated preferences.");
                callFlush();
            } else {
                System.out.println("New score is no greater than previous score.");
            }
        }
        else{
            scores.putInteger(user, score);
            callFlush();
            }
    }

    /**
     * <p>Searches for index {@code user} within preferences:</p>
     * <p>If present, returns corresponding {@link Integer} value.</p>
     * <p>Otherwise, returns {@code 0}</p>
     *
     * @param user {@link String} User's name
     * @return {@link Integer} User's recorded score
     */
    public Integer getUserScore(String user) {
        return scores.getInteger(user, 0);
    }

    /**
     * Writes to file.
     */
    private void callFlush() {
        scores.flush();
        System.out.println("Successful flush.");
    }

}
