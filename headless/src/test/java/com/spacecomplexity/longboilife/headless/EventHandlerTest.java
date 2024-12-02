package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import com.spacecomplexiy.longboilife.headless.HeadlessLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EventHandler class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventHandlerTest extends AbstractHeadlessGdxTest{

    @BeforeAll
    public void setUp() {
        // No initialization needed
    }

    @AfterAll
    public void tearDown() {
        // TBC
    }

    @Test
    public void testCreateEvent() {}


}
