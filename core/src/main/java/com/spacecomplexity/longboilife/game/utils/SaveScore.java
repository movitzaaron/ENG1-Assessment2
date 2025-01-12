package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// NEW: this is all new
/** Class used for saving user scores to a preferences file, "playerScores". */
public class SaveScore {

  /** Accesses preferences file if it exists, otherwise creates it. */
  private static final Preferences scores = Gdx.app.getPreferences("playerScores");

  /**
   * Updates playerScores preferences file:
   *
   * <p>If user is not present within file, creates index pair containing the user and their score.
   *
   * <p>If user is present within file, checks if new score is greater than previously recorded
   * score {@link #getUserScore(String)}, and overwrites preferences with new score.
   *
   * <p>If score is updated, flushes preferences file to write changes. {@link #callFlush()}
   *
   * @param user {@link String} User's name
   * @param score {@link Integer} User's new score
   */
  public static void updateScores(String user, Integer score) {
    user = user.isBlank() ? "Unknown" : user;
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
    } else {
      scores.putInteger(user, score);
      callFlush();
    }
  }

  /**
   * Gets preferences and returns as a {@link String} table.
   *
   * @return {@link String} Table of users names and corresponding scores
   */
  public static String getScores() {
    StringBuilder scoreStringBuild = new StringBuilder();
    scores
        .get()
        .forEach(
            (k, v) -> {
              scoreStringBuild.append(k).append(" ").append(v.toString()).append("\r\n");
            });
    return scoreStringBuild.substring(0, scoreStringBuild.length() - 1);
  }

  /**
   * Retrieves the top five scores and their corresponding names.
   *
   * <p>This method fetches scores from a persistent data store, sorts them in descending order, and
   * formats the top five entries as a single string. The names and scores are separated by a
   * vertical bar ({@code |}) to distinguish between them.
   *
   * <p>The format of the returned string is as follows:
   *
   * <pre>
   * Name1
   * Name2
   * ...
   * Name5
   * |
   * Score1
   * Score2
   * ...
   * Score5
   * </pre>
   *
   * @return A formatted string containing the top five names and scores, separated by a vertical
   *     bar.
   */
  public static String getTopFive() {
    StringBuilder nameStringBuilder = new StringBuilder();
    StringBuilder scoreStringBuilder = new StringBuilder();
    Map<String, ?> scoreMap = scores.get();
    LinkedHashMap<String, Integer> sortedScoreMap = new LinkedHashMap<>();
    ArrayList<Integer> scoreList = new ArrayList<>();

    // create a list of score entries
    for (Map.Entry<String, ?> entry : scoreMap.entrySet()) {
      scoreList.add(Integer.parseInt(entry.getValue().toString()));
    }

    // sort the score list in descending order
    scoreList.sort(Collections.reverseOrder());

    // arrange the k,v pairs according to the list we just ordered
    for (int num : scoreList) {
      for (Map.Entry<String, ?> entry : scoreMap.entrySet()) {
        if (Integer.parseInt(entry.getValue().toString()) == num) {
          sortedScoreMap.put(entry.getKey(), num);
        }
      }
    }

    // add the top 5 entries to the StringBuilder
    int count = 0;
    for (Map.Entry<String, Integer> entry : sortedScoreMap.entrySet()) {
      if (count == 5) {
        break;
      }
      nameStringBuilder.append(entry.getKey()).append("\r\n");
      scoreStringBuilder.append(entry.getValue().toString()).append("\r\n");

      count++;
    }

    return nameStringBuilder.append("|").append(scoreStringBuilder).toString();
  }

  /**
   * Searches for index {@code user} within preferences:
   *
   * <p>If present, returns corresponding {@link Integer} value.
   *
   * <p>Otherwise, returns {@code 0}
   *
   * @param user {@link String} User's name
   * @return {@link Integer} User's recorded score
   */
  public static Integer getUserScore(String user) {
    return scores.getInteger(user, -1);
  }

  /** Writes to file. */
  private static void callFlush() {
    scores.flush();
    System.out.println("Successful flush.");
  }
}
