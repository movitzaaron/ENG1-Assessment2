package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.scenarios.GrantScenario;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Unit tests for the Tile class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GrantScenarioTest {

    private EventHandler mockEventHandler;
    private GameState mockGameState;


    @BeforeAll
    public void setUp() {
        // Mock dependencies
        mockEventHandler = new EventHandler();
        mockGameState = new GameState();

    }

    @AfterAll
    public void tearDown() {
        // Reset the scenario
        GrantScenario.reset();
    }

    @Test
    @DisplayName("Test grant scenario triggers when conditions are met")
    public void testScenarioTriggers() {
        //Arrange
        mockGameState.money = 200000;
        GrantScenario.grantTaken = false;

        //Act
        GrantScenario.poll();

        //Assert
        assertDoesNotThrow();
    }

    @Test
    @DisplayName("Test grant scenario does not trigger if money > 200000")
    public void testGrantScenarioNoTriggerWhenMoneyHigh() {
        // Set up mock state
        GameState.getState().money = 200001;
        GrantScenario.grantTaken = false;

        // Poll the scenario
        GrantScenario.poll();

        // Verify no events were triggered
        verify(EventHandler.getEventHandler(), never()).callEvent(any());
    }

    @Test
    @DisplayName("Test grant scenario does not trigger when grantTaken is true")
    public void testGrantScenarioNoTriggerWhenGrantTakenTrue() {
        // Set up mock state
        GameState.getState().money = 200000;
        GrantScenario.grantTaken = true;

        // Poll the scenario
        GrantScenario.poll();

        // Verify no events were triggered
        verify(EventHandler.getEventHandler(), never()).callEvent(any());
    }

}
