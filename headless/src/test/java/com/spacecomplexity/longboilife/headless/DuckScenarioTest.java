package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spacecomplexity.longboilife.game.scenarios.DuckScenario;
import com.spacecomplexity.longboilife.game.utils.Timer;
import org.junit.jupiter.api.*;

/** Unit tests for the DuckScenario class. TEST REF : 4 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DuckScenarioTest {

  @BeforeAll
  public void setUp() {}

  @AfterAll
  public void tearDown() {
    DuckScenario.reset();
  }

  @Test
  public void testDuckScenarioConstructorAndGetters() {
    DuckScenario duckScenario = DuckScenario.duckScenario;
    Timer timer = duckScenario.getTimer();

    // Check if the timer is set to the expected value (3 minutes)
    assertEquals(3 * 60 * 1000, timer.getTimeLeft(), "Timer should be set to 3 minutes");
  }
}
