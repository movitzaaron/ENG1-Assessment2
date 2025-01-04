package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.scenarios.GrantScenario;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Unit tests for the Tile class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GrantScenarioTest {

    @BeforeAll
    public void setUp() {}

    @AfterAll
    public void tearDown() {
        GrantScenario.reset();
    }

    @Test
    public void testGrantScenarioConstructorAndGetters() {

            }

}
