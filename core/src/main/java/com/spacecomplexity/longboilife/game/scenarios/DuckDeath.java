package com.spacecomplexity.longboilife.game.scenarios;

import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Singleton class to contain the duck death timer and relevant begin and end functions.
 */
public class DuckDeath {
    public static final DuckDeath duckDeath = new DuckDeath();

    private final Timer timer;

    private DuckDeath() {
        timer = new Timer();
    }

    public static void begin(){
        // do something
        System.out.println("Duck Death has occurred");
    }

    public static void end(){
        // do something
    }

    /**
     * Get the singleton instance of the {@link DuckDeath} class.
     *
     * @return The single {@link DuckDeath} class.
     */

    public static DuckDeath getDuckDeath() {return duckDeath;}

    public Timer getTimer() {return timer;}


}
