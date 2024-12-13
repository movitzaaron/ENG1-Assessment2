package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.*;
import com.badlogic.gdx.Gdx;
import static org.junit.jupiter.api.Assertions.*;
import com.spacecomplexity.longboilife.game.utils.Timer;

/**
 * Unit tests for the Timer class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimerTest extends AbstractHeadlessGdxTest{

    @BeforeAll
    public void setUp() {
        Timer timer = new Timer();
        timer.setTimer(100);
    }

    @AfterAll
    public void tearDown() {
    }

    @Test
    public void testSetTimerAndGetTimeLeft(){
        // Arrange
        Timer timer = new Timer();

        //Act
        timer.setTimer(100);

        // Assert
        assertEquals(timer.getTimeLeft(), 100, "Timer should be set to 100.");
    }

    @Test
    public void testPauseAndResumeTimer() {
        // Arrange
        Timer timer = new Timer();
        Timer timer2 = new Timer();
        Timer timer3 = new Timer();

        // Act
        timer.pauseTimer();
        timer3.pauseTimer();
        timer3.resumeTimer();

        // Assert
        assertTrue(timer.isPaused(), "Timer should be paused upon calling pauseTimer().");
        assertFalse(timer2.isPaused(), "Timer should not be paused when initialised.");
        assertFalse(timer3.isPaused(), "Timer should not be paused when resumeTimer() is called.");
    }

    @Test
    public void testPoll() {
        // Arrange
        Timer timer = new Timer();

        // Act


        // Assert


    }

}
