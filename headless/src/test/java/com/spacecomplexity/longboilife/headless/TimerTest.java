package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.*;

import com.spacecomplexity.longboilife.game.utils.Timer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance;

/** Unit tests for the Timer class. TEST REF : 10 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimerTest extends AbstractHeadlessGdxTest {

  @BeforeAll
  public void setUp() {
    Timer timer = new Timer();
    timer.setTimer(100);
  }

  @AfterAll
  public void tearDown() {}

  @Test
  public void testSetTimerAndGetTimeLeft() {
    // Arrange
    Timer timer = new Timer();
    Timer timer2 = new Timer();

    // Act
    timer.setTimer(100);
    timer2.setTimer(-1);

    // Assert
    assertEquals(100, timer.getTimeLeft(), "Timer should be set to 100.");
    assertEquals(0, timer2.getTimeLeft(), "Timer should be set to 0 upon invalid input.");
  }

  @Test
  public void testPauseAndResumeTimer() {
    // Arrange
    Timer timer = new Timer();
    Timer timer2 = new Timer();
    Timer timer3 = new Timer();
    Timer timer4 = new Timer();

    // Act
    timer.pauseTimer();

    timer3.pauseTimer();
    timer3.resumeTimer();

    timer4.pauseTimer();
    timer4.pauseTimer();

    // Assert
    assertTrue(timer.isPaused(), "Timer should be paused upon calling pauseTimer().");
    assertFalse(timer2.isPaused(), "Timer should not be paused when initialised.");
    assertFalse(timer3.isPaused(), "Timer should not be paused when resumeTimer() is called.");
    assertTrue(timer4.isPaused(), "Timers that are repeatedly paused should remain so.");
  }

  @Test
  public void testResumeWhenNotPaused() {
    // Arrange
    Timer timer = new Timer();

    // Act & Assert
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            timer::resumeTimer,
            "Timer should throw IllegalStateException when trying to resume a timer that is not paused");

    assertEquals("Timer has not been paused", exception.getMessage());
  }

  @Test
  public void testPoll() {
    // Arrange
    Timer timer = new Timer();
    timer.setTimer(100);
    timer.setEvent(
        new Runnable() {
          @Override
          public void run() {}
        });

    Timer timer2 = new Timer();
    timer2.setTimer(0);
    timer2.setEvent(
        new Runnable() {
          @Override
          public void run() {}
        });

    Timer timer3 = new Timer();
    timer3.setTimer(100);

    // Act

    // Assert
    assertFalse(timer.poll(), "Timers with time remaining should not be polled.");
    assertTrue(timer2.poll(), "Timers with time remaining should be polled.");
    assertTrue(timer2.poll(), "Timers that have already been polled should always return true.");
    assertFalse(timer3.poll(), "Timers with no event set should not be polled.");
  }

  @Test
  public void testGetEventCalled() {
    // Arrange
    Timer timer = new Timer();
    timer.setTimer(100);

    Timer timer2 = new Timer();
    timer2.setEvent(
        new Runnable() {
          @Override
          public void run() {}
        });
    timer2.setTimer(0);
    timer2.poll();

    // Act

    // Assert
    assertFalse(
        timer.getEventCalled(), "EventCalled should be false for timers with no event set.");
    assertTrue(
        timer2.getEventCalled(), "EventCalled should be true for timers that have been polled.");
  }
}
