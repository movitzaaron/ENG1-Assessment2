package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Class used for saving user scores to a preferences file, "playerScores".</p>
 */
public class SaveScore {

    /**
     * <p>Accesses preferences file if it exists, otherwise creates it.</p>
     */
    private static final Preferences scores = Gdx.app.getPreferences("playerScores");

    /**
     * <p>Updates playerScores preferences file:</p>
     * <p>If user is not present within file, creates index pair containing the user and their
     * score.</p>
     * <p>If user is present within file, checks if new score is greater than previously recorded
     * score {@link #getUserScore(String)}, and overwrites preferences with new score.</p>
     * <p>If score is updated, flushes preferences file to write changes. {@link #callFlush()} </p>
     *
     * @param user {@link String} User's name
     * @param score {@link Integer} User's new score
     */
    public static void updateScores(String user, Integer score){
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
     * <p>Gets stored preferences and returns as Hashmap.</p>
     *
     * @return {@link HashMap} User's name as {@link String} keys with correlating {@link Integer} scores
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Integer> getScores(){
      return (HashMap<String, Integer>) scores.get();
    }

    /**
     * <p>Searches for index {@code user} within preferences:</p>
     * <p>If present, returns corresponding {@link Integer} value.</p>
     * <p>Otherwise, returns {@code 0}</p>
     *
     * @param user {@link String} User's name
     * @return {@link Integer} User's recorded score
     */
    public static Integer getUserScore(String user) {
        return scores.getInteger(user, -1);
    }

    /**
     * <p>Writes to file.</p>
     */
    private static void callFlush() {
        scores.flush();
        System.out.println("Successful flush.");
    }

    // Clears prefs, currently used for testing
    public static void deletePrefs(){
        scores.clear();
        System.out.println("Deleted preferences.");
        callFlush();
    }

}
