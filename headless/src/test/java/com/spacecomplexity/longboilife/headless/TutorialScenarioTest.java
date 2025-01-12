package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spacecomplexity.longboilife.game.scenarios.TutorialScenario;
import com.spacecomplexity.longboilife.game.utils.Timer;
import org.junit.jupiter.api.*;

/** Unit tests for the TutorialScenario class. TEST REF : 11 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TutorialScenarioTest {

  @BeforeAll
  public void setUp() {}

  @AfterAll
  public void tearDown() {
    TutorialScenario.reset();
  }

  @Test
  public void testTutorialScenarioConstructorAndGetters() {
    TutorialScenario tutorialScenario = TutorialScenario.tutorialScenario;
    Timer timer = tutorialScenario.getTimer();

    assertEquals(1000, timer.getTimeLeft(), "Timer should be set to 1 second");
  }
}
