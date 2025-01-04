package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.scenarios.TutorialScenario;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Timer;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for the Tile class.
 */
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

    }

}
