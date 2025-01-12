package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.scenarios.RosesScenario;
import org.junit.jupiter.api.*;

/** Unit tests for the Tile class. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RosesScenarioTest {

  @BeforeAll
  public void setUp() {}

  @AfterAll
  public void tearDown() {
    RosesScenario.reset();
  }

  @Test
  public void testRosesScenarioConstructorAndGetters() {}
}
