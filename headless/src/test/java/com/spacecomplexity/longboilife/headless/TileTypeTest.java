package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.spacecomplexity.longboilife.game.tile.TileType;
import org.junit.jupiter.api.*;

/** Unit tests for the TileType enum. TEST REF : 9 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TileTypeTest extends AbstractHeadlessGdxTest {

  @BeforeAll
  public void setUp() {}

  @AfterAll
  public void tearDown() {}

  @Test
  @DisplayName(" Test TileType enum constants and their properties. ")
  public void testTileTypeProperties() {

    // Test GRASS
    TileType grass = TileType.GRASS;
    assertEquals("tiles/grass.png", grass.getTexturePath(), "Grass texture mismatch.");
    assertTrue(grass.isNaturallyBuildable(), "Grass tile type should be naturally buildable.");

    // Test WATER
    TileType water = TileType.WATER;
    assertEquals("tiles/water.png", water.getTexturePath(), "Water texture mismatch.");
    assertFalse(water.isNaturallyBuildable(), "Water tile type should not be naturally buildable.");
  }
}
