package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.Preferences;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.AchievementManager;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class AchievementManagerTest extends AbstractHeadlessGdxTest {
  private static FakePreferences fakePreferences;
  private AchievementManager achievementManager;
  private GameState gameState;

  // A simple in-memory fake preferences implementation
  private static class FakePreferences implements Preferences {
    private final Map<String, Object> map = new HashMap<>();

    @Override
    public Preferences putBoolean(String key, boolean val) {
      map.put(key, val);
      return this;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
      Object val = map.get(key);
      return val instanceof Boolean ? (Boolean) val : defValue;
    }

    @Override
    public void clear() {
      map.clear();
    }

    @Override
    public void flush() {
      // no-op for fake
    }

    // The rest of them are not used, so throw an UnsupportedOperationException

    @Override
    public Preferences putInteger(String s, int i) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Preferences putLong(String s, long l) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Preferences putFloat(String s, float v) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Preferences putString(String s, String s1) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Preferences put(Map<String, ?> map) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBoolean(String s) {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getInteger(String s) {
      throw new UnsupportedOperationException();
    }

    @Override
    public long getLong(String s) {
      throw new UnsupportedOperationException();
    }

    @Override
    public float getFloat(String s) {
      throw new UnsupportedOperationException();
    }

    @Override
    public String getString(String s) {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getInteger(String s, int i) {
      throw new UnsupportedOperationException();
    }

    @Override
    public long getLong(String s, long l) {
      throw new UnsupportedOperationException();
    }

    @Override
    public float getFloat(String s, float v) {
      throw new UnsupportedOperationException();
    }

    @Override
    public String getString(String s, String s1) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, ?> get() {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(String s) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void remove(String s) {
      throw new UnsupportedOperationException();
    }
  }

  @BeforeAll
  static void setupClass() {
    // Initialize FakePreferences.
    fakePreferences = new FakePreferences();
  }

  @BeforeEach
  void setUp() {
    gameState = GameState.testCreateInstance();
    gameState.reset();

    achievementManager = new AchievementManager(fakePreferences);
    achievementManager.resetAchievements(fakePreferences);

    fakePreferences.clear();
  }

  @AfterEach
  void tearDown() {
    // Reset after each test to clean up any stored achievements.
    achievementManager.resetAchievements(fakePreferences);
  }

  @Test
  void testResetAchievements() {
    // Simulate unlocking an achievement by manually putting a value in preferences.
    fakePreferences.putBoolean(AchievementManager.Achievement.BROKE.name(), true);
    achievementManager.resetAchievements(fakePreferences);

    Set<AchievementManager.Achievement> unlocked =
        achievementManager.getUnlockedAchievements(fakePreferences);
    assertFalse(
        unlocked.contains(AchievementManager.Achievement.BROKE),
        "All achievements should be reset.");
  }

  @Test
  void testGetUnlockedAchievements() {
    // Simulate that SATISFIED achievement was previously unlocked.
    fakePreferences.putBoolean(AchievementManager.Achievement.SATISFIED.name(), true);
    fakePreferences.flush();

    Set<AchievementManager.Achievement> unlocked =
        achievementManager.getUnlockedAchievements(fakePreferences);
    assertTrue(
        unlocked.contains(AchievementManager.Achievement.SATISFIED),
        "Achievement SATISFIED should be loaded from preferences.");
  }

  @Test
  void testLoadAchievements() {
    // Simulate multiple achievements unlocked in preferences.
    fakePreferences.putBoolean(AchievementManager.Achievement.FITNESS.name(), true);
    fakePreferences.putBoolean(AchievementManager.Achievement.ACADEMIC.name(), true);
    fakePreferences.flush();

    // Create a new manager to force loading from preferences.
    AchievementManager newManager = new AchievementManager(fakePreferences);
    Set<AchievementManager.Achievement> unlocked =
        newManager.getUnlockedAchievements(fakePreferences);

    assertTrue(
        unlocked.contains(AchievementManager.Achievement.FITNESS),
        "Achievement FITNESS should be loaded.");
    assertTrue(
        unlocked.contains(AchievementManager.Achievement.ACADEMIC),
        "Achievement ACADEMIC should be loaded.");
  }

  @Test
  void testPollUnlockBroke() {
    // Use Mockito static mocking for GameState and EventHandler.
    try (MockedStatic<EventHandler> eventHandlerMock = Mockito.mockStatic(EventHandler.class)) {
      // Mock EventHandler
      EventHandler dummyEventHandler = mock(EventHandler.class);
      eventHandlerMock.when(EventHandler::getEventHandler).thenReturn(dummyEventHandler);

      // PRE-UNLOCK
      // Verify that the BROKE achievement was not unlocked by checking preferences.
      assertFalse(
          fakePreferences.getBoolean(AchievementManager.Achievement.BROKE.name(), false),
          "BROKE achievement not should be unlocked yet.");

      // Verify that the corresponding event was not called.
      verify(dummyEventHandler, never()).callEvent(EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG);

      // POST-UNLOCK
      // Change satisfaction score to be that of unlocking SATISFIED
      gameState.money = 0;

      // Update the status of achievement manager, and hence fakePreferences
      achievementManager.poll(gameState, fakePreferences);

      // Verify that the BROKE achievement was unlocked by checking preferences.
      assertTrue(
          fakePreferences.getBoolean(AchievementManager.Achievement.BROKE.name(), false),
          "BROKE achievement should be unlocked.");

      // Verify that the corresponding event was called.
      verify(dummyEventHandler).callEvent(EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG);
    }
  }

  @Test
  void testPollUnlockSatisfied() {
    // Use Mockito static mocking for GameState and EventHandler.
    try (MockedStatic<EventHandler> eventHandlerMock = Mockito.mockStatic(EventHandler.class)) {
      // Mock EventHandler
      EventHandler dummyEventHandler = mock(EventHandler.class);
      eventHandlerMock.when(EventHandler::getEventHandler).thenReturn(dummyEventHandler);

      // PRE-UNLOCK
      // Verify that the SATISFIED achievement was not unlocked by checking preferences.
      assertFalse(
          fakePreferences.getBoolean(AchievementManager.Achievement.SATISFIED.name(), false),
          "SATISFIED achievement not should be unlocked yet.");

      // Verify that the corresponding event was not called.
      verify(dummyEventHandler, never()).callEvent(EventHandler.Event.SATISFIED_ACHIEVEMENT_DIALOG);

      // POST-UNLOCK
      // Change satisfaction score to be that of unlocking SATISFIED
      gameState.satisfactionScore = 1;

      // Update the status of achievement manager, and hence fakePreferences
      achievementManager.poll(gameState, fakePreferences);

      // Verify that the SATISFIED achievement was unlocked by checking preferences.
      assertTrue(
          fakePreferences.getBoolean(AchievementManager.Achievement.SATISFIED.name(), false),
          "SATISFIED achievement should be unlocked.");

      // Verify that the corresponding event was called.
      verify(dummyEventHandler).callEvent(EventHandler.Event.SATISFIED_ACHIEVEMENT_DIALOG);
    }
  }

  @Test
  void testPollUnlockFitness() {
    // Use Mockito static mocking for GameState and EventHandler.
    try (MockedStatic<EventHandler> eventHandlerMock = Mockito.mockStatic(EventHandler.class)) {
      // Mock EventHandler
      EventHandler dummyEventHandler = mock(EventHandler.class);
      eventHandlerMock.when(EventHandler::getEventHandler).thenReturn(dummyEventHandler);

      // PRE-UNLOCK
      // Verify that the FITNESS achievement was not unlocked by checking preferences.
      assertFalse(
          fakePreferences.getBoolean(AchievementManager.Achievement.FITNESS.name(), false),
          "FITNESS achievement not should be unlocked yet.");

      // Verify that the corresponding event was not called.
      verify(dummyEventHandler, never()).callEvent(EventHandler.Event.FITNESS_ACHIEVEMENT_DIALOG);

      // POST-UNLOCK
      // Change the building count to be that of the achievement
      gameState.changeBuildingCount(BuildingType.GYM, 3);

      // Update the status of achievement manager, and hence fakePreferences
      achievementManager.poll(gameState, fakePreferences);

      // Verify that the FITNESS achievement was unlocked by checking preferences.
      assertTrue(
          fakePreferences.getBoolean(AchievementManager.Achievement.FITNESS.name(), false),
          "FITNESS achievement should be unlocked.");

      // Verify that the corresponding event was called.
      verify(dummyEventHandler).callEvent(EventHandler.Event.FITNESS_ACHIEVEMENT_DIALOG);
    }
  }

  @Test
  void testPollUnlockAcademic() {
    // Use Mockito static mocking for GameState and EventHandler.
    try (MockedStatic<EventHandler> eventHandlerMock = Mockito.mockStatic(EventHandler.class)) {
      // Mock EventHandler
      EventHandler dummyEventHandler = mock(EventHandler.class);
      eventHandlerMock.when(EventHandler::getEventHandler).thenReturn(dummyEventHandler);

      // PRE-UNLOCK
      // Verify that the ACADEMIC achievement was not unlocked by checking preferences.
      assertFalse(
          fakePreferences.getBoolean(AchievementManager.Achievement.ACADEMIC.name(), false),
          "ACADEMIC achievement not should be unlocked yet.");

      // Verify that the corresponding event was not called.
      verify(dummyEventHandler, never()).callEvent(EventHandler.Event.ACADEMIC_ACHIEVEMENT_DIALOG);

      // POST-UNLOCK
      // Change the building count to be that of the achievement
      gameState.changeBuildingCount(BuildingType.LIBRARY, 3);

      // Update the status of achievement manager, and hence fakePreferences
      achievementManager.poll(gameState, fakePreferences);

      // Verify that the ACADEMIC achievement was unlocked by checking preferences.
      assertTrue(
          fakePreferences.getBoolean(AchievementManager.Achievement.ACADEMIC.name(), false),
          "ACADEMIC achievement should be unlocked.");

      // Verify that the corresponding event was called.
      verify(dummyEventHandler).callEvent(EventHandler.Event.ACADEMIC_ACHIEVEMENT_DIALOG);
    }
  }

  @Test
  void testPollUnlockFoodie() {
    // Use Mockito static mocking for GameState and EventHandler.
    try (MockedStatic<EventHandler> eventHandlerMock = Mockito.mockStatic(EventHandler.class)) {
      // Mock EventHandler
      EventHandler dummyEventHandler = mock(EventHandler.class);
      eventHandlerMock.when(EventHandler::getEventHandler).thenReturn(dummyEventHandler);

      // PRE-UNLOCK
      // Verify that the FOODIE achievement was not unlocked by checking preferences.
      assertFalse(
          fakePreferences.getBoolean(AchievementManager.Achievement.FOODIE.name(), false),
          "FOODIE achievement not should be unlocked yet.");

      // Verify that the corresponding event was not called.
      verify(dummyEventHandler, never()).callEvent(EventHandler.Event.FOODIE_ACHIEVEMENT_DIALOG);

      // POST-UNLOCK
      // Change the building count to be that of the achievement
      gameState.changeBuildingCount(BuildingType.GREGGS, 3);

      // Update the status of achievement manager, and hence fakePreferences
      achievementManager.poll(gameState, fakePreferences);

      // Verify that the FOODIE achievement was unlocked by checking preferences.
      assertTrue(
          fakePreferences.getBoolean(AchievementManager.Achievement.FOODIE.name(), false),
          "FOODIE achievement should be unlocked.");

      // Verify that the corresponding event was called.
      verify(dummyEventHandler).callEvent(EventHandler.Event.FOODIE_ACHIEVEMENT_DIALOG);
    }
  }

  @Test
  void testPollDoesNotUnlockAlreadyUnlocked() {
    // First, simulate that BROKE achievement is already unlocked.
    fakePreferences.putBoolean(AchievementManager.Achievement.BROKE.name(), true);
    fakePreferences.flush();
    // Force load of achievements.
    AchievementManager achievementManager = new AchievementManager(fakePreferences);

    try (MockedStatic<EventHandler> eventHandlerMock = Mockito.mockStatic(EventHandler.class)) {
      gameState.money = 0;
      gameState.satisfactionScore = 1;

      EventHandler dummyEventHandler = mock(EventHandler.class);
      eventHandlerMock.when(EventHandler::getEventHandler).thenReturn(dummyEventHandler);

      achievementManager.poll(gameState, fakePreferences);

      // Check that BROKE is not unlocked again (preferences value remains true).
      assertTrue(
          fakePreferences.getBoolean(AchievementManager.Achievement.BROKE.name(), false),
          "BROKE should remain unlocked.");
      // Ensure no additional event call since it's already unlocked.
      verify(dummyEventHandler, never()).callEvent(EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG);
    }
  }
}
