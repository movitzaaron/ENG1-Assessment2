package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.*;

import com.spacecomplexity.longboilife.game.utils.EventHandler;
import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventHandlerTest {

    private EventHandler eventHandler;

    @BeforeEach
    void setUp() {
        eventHandler = EventHandler.getEventHandler();
    }

    @AfterEach
    void tearDown() {
        for (EventHandler.Event event : EventHandler.Event.values()) {
            EventHandler.getEventHandler().createEvent(event, null);
        }
    }

    @Test
    void testCreateAndCallEvent_Success() {
        // Arrange
        EventHandler.Event testEvent = EventHandler.Event.BUILD;
        Function<Object[], Object> callback = (params) -> {
            if (params.length > 0 && params[0] instanceof String) {
                return "Building: " + params[0];
            }
            return "No parameters";
        };
        eventHandler.createEvent(testEvent, callback);

        // Act
        Object result = eventHandler.callEvent(testEvent, "Barracks");

        // Assert
        assertEquals("Building: Barracks", result);
    }

    @Test
    void testCallEvent_NoCallbackDefined() {
        // Arrange
        EventHandler.Event undefinedEvent = EventHandler.Event.SELECT_BUILDING;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventHandler.callEvent(undefinedEvent);
        });
        assertEquals("No event defined for: \"SELECT_BUILDING\"", exception.getMessage());
    }

    @Test
    void testMultipleEvents_IndependentCallbacks() {
        // Arrange
        EventHandler.Event buildEvent = EventHandler.Event.BUILD;
        EventHandler.Event pauseEvent = EventHandler.Event.PAUSE_GAME;

        Function<Object[], Object> buildCallback = (params) -> "Build event triggered";
        Function<Object[], Object> pauseCallback = (params) -> "Pause event triggered";

        eventHandler.createEvent(buildEvent, buildCallback);
        eventHandler.createEvent(pauseEvent, pauseCallback);

        // Act
        Object buildResult = eventHandler.callEvent(buildEvent);
        Object pauseResult = eventHandler.callEvent(pauseEvent);

        // Assert
        assertEquals("Build event triggered", buildResult);
        assertEquals("Pause event triggered", pauseResult);
    }

    @Test
    void testCallbackWithMultipleParameters() {
        // Arrange
        EventHandler.Event moveBuildingEvent = EventHandler.Event.MOVE_BUILDING;
        Function<Object[], Object> callback = (params) -> {
            if (params.length == 2 && params[0] instanceof Integer && params[1] instanceof Integer) {
                return (Integer) params[0] + (Integer) params[1];
            }
            return null;
        };
        eventHandler.createEvent(moveBuildingEvent, callback);

        // Act
        Object result = eventHandler.callEvent(moveBuildingEvent, 5, 10);

        // Assert
        assertEquals(15, result);
    }

    @Test
    void testCallbackThrowsException() {
        // Arrange
        EventHandler.Event gameEndEvent = EventHandler.Event.GAME_END;
        Function<Object[], Object> callback = (params) -> {
            throw new RuntimeException("Game Ended Abruptly");
        };
        eventHandler.createEvent(gameEndEvent, callback);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventHandler.callEvent(gameEndEvent);
        });
        assertEquals("Game Ended Abruptly", exception.getMessage());
    }

    @Test
    void testSingletonInstance() {
        // Arrange & Act
        EventHandler instance1 = EventHandler.getEventHandler();
        EventHandler instance2 = EventHandler.getEventHandler();

        // Assert
        assertSame(instance1, instance2, "Both instances should be the same (singleton)");
    }

    @Test
    void testOverwriteExistingCallback() {
        // Arrange
        EventHandler.Event sellBuildingEvent = EventHandler.Event.SELL_BUILDING;
        Function<Object[], Object> initialCallback = (params) -> "Initial Callback";
        Function<Object[], Object> newCallback = (params) -> "New Callback";

        eventHandler.createEvent(sellBuildingEvent, initialCallback);
        Object initialResult = eventHandler.callEvent(sellBuildingEvent);
        assertEquals("Initial Callback", initialResult);

        // Act
        eventHandler.createEvent(sellBuildingEvent, newCallback);
        Object newResult = eventHandler.callEvent(sellBuildingEvent);

        // Assert
        assertEquals("New Callback", newResult);
    }

    @Test
    void testCreateEvent_NullCallback() {
        // Arrange
        EventHandler.Event openMenuEvent = EventHandler.Event.OPEN_SELECTED_MENU;

        // Act
        eventHandler.createEvent(openMenuEvent, null);

        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventHandler.callEvent(openMenuEvent);
        });
        assertEquals("No event defined for: \"OPEN_SELECTED_MENU\"", exception.getMessage());
    }

    @Test
    void testCallbackWithNoParameters() {
        // Arrange
        EventHandler.Event resumeGameEvent = EventHandler.Event.RESUME_GAME;
        Function<Object[], Object> callback = (params) -> "Game Resumed";
        eventHandler.createEvent(resumeGameEvent, callback);

        // Act
        Object result = eventHandler.callEvent(resumeGameEvent);

        // Assert
        assertEquals("Game Resumed", result);
    }

    @Test
    void testCallbackWithDifferentReturnTypes() {
        // Arrange
        EventHandler.Event grantScenarioDialogEvent = EventHandler.Event.GRANT_SCENARIO_DIALOG;

        // Callback returning Integer
        Function<Object[], Object> intCallback = (params) -> 42;
        eventHandler.createEvent(grantScenarioDialogEvent, intCallback);
        Object intResult = eventHandler.callEvent(grantScenarioDialogEvent);
        assertEquals(42, intResult);

        // Overwrite with a String callback
        Function<Object[], Object> stringCallback = (params) -> "Dialog Granted";
        eventHandler.createEvent(grantScenarioDialogEvent, stringCallback);
        Object stringResult = eventHandler.callEvent(grantScenarioDialogEvent);
        assertEquals("Dialog Granted", stringResult);
    }

    @Test
    void testCallbackSideEffects() {
        // Arrange
        EventHandler.Event cancelOperationsEvent = EventHandler.Event.CANCEL_OPERATIONS;
        AtomicBoolean flag = new AtomicBoolean(false);
        Function<Object[], Object> callback = (params) -> {
            flag.set(true);
            return null;
        };
        eventHandler.createEvent(cancelOperationsEvent, callback);

        // Act
        eventHandler.callEvent(cancelOperationsEvent);

        // Assert
        assertTrue(flag.get(), "Callback should have set the flag to true");
    }

    @Test
    void testCallEvent_NullEvent() {
        assertThrows(NullPointerException.class, () -> {
            eventHandler.callEvent(null);
        });
    }
}
