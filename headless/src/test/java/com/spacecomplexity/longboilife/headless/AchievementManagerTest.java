//package com.spacecomplexity.longboilife.game.utils;
//
//import com.spacecomplexity.longboilife.game.building.BuildingType;
//import com.spacecomplexity.longboilife.game.globals.GameState;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//
//class AchievementManagerTest {
//    private GameState gameState;
//
//    @BeforeEach
//    void setUp() {
//        gameState = GameState.TEST_CreateInstance();
//        gameState.reset();
//    }
//
//
//    @Test
//    void testBrokeAchievement() {
//        // Create test objects
//        AchievementManager achievementManager = new AchievementManager();
//        gameState.money = 0;
//
//
//
//        // Mock the static calls
//        try (MockedStatic<GameState> gameStateMockedStatic = mockStatic(GameState.class);
//             MockedStatic<EventHandler> eventHandlerMockedStatic = mockStatic(EventHandler.class)) {
//
//            // Return our mockGameState whenever GameState.getState() is called
//            gameStateMockedStatic.when(GameState::getState).thenReturn(mockGameState);
//
//            // Create and return a mock EventHandler
//            EventHandler mockEventHandler = mock(EventHandler.class);
//            eventHandlerMockedStatic.when(EventHandler::getEventHandler).thenReturn(mockEventHandler);
//
//            // Call the method under test
//            achievementManager.poll();
//
//            // Verify the "broke" flag is set
//            assertTrue(achievementManager.broke, "AchievementManager should set broke=true when money=0");
//            // Verify the correct event was fired
//            verify(mockEventHandler).callEvent(EventHandler.Event.BROKE_ACHIEVEMENT_DIALOG);
//        }
//    }
//
//    @Test
//    void testSatisfiedAchievement() {
//        AchievementManager achievementManager = new AchievementManager();
//        GameState mockGameState = new GameState();
//        // Trigger "satisfied" by setting satisfactionScore = 1
//        mockGameState.satisfactionScore = 1;
//
//        try (MockedStatic<GameState> gameStateMockedStatic = mockStatic(GameState.class);
//             MockedStatic<EventHandler> eventHandlerMockedStatic = mockStatic(EventHandler.class)) {
//
//            gameStateMockedStatic.when(GameState::getState).thenReturn(mockGameState);
//
//            EventHandler mockEventHandler = mock(EventHandler.class);
//            eventHandlerMockedStatic.when(EventHandler::getEventHandler).thenReturn(mockEventHandler);
//
//            achievementManager.poll();
//
//            assertTrue(achievementManager.satisfied,
//                "AchievementManager should set satisfied=true when satisfactionScore=1");
//            verify(mockEventHandler).callEvent(EventHandler.Event.SATISFIED_ACHIEVEMENT_DIALOG);
//        }
//    }
//
//    @Test
//    void testFitnessAchievement() {
//        AchievementManager achievementManager = new AchievementManager();
//        // We will mock the getBuildingCount(...) calls
//        GameState mockGameState = mock(GameState.class);
//
//        // Return 3 when asked for GYM building count, to satisfy fitness achievement
//        when(mockGameState.getBuildingCount(BuildingType.GYM)).thenReturn(3);
//
//        try (MockedStatic<GameState> gameStateMockedStatic = mockStatic(GameState.class);
//             MockedStatic<EventHandler> eventHandlerMockedStatic = mockStatic(EventHandler.class)) {
//
//            gameStateMockedStatic.when(GameState::getState).thenReturn(mockGameState);
//
//            EventHandler mockEventHandler = mock(EventHandler.class);
//            eventHandlerMockedStatic.when(EventHandler::getEventHandler).thenReturn(mockEventHandler);
//
//            achievementManager.poll();
//
//            assertTrue(achievementManager.fitness,
//                "AchievementManager should set fitness=true when 3 gyms exist.");
//            verify(mockEventHandler).callEvent(EventHandler.Event.FITNESS_ACHIEVEMENT_DIALOG);
//        }
//    }
//
//    @Test
//    void testAcademicAchievement() {
//        AchievementManager achievementManager = new AchievementManager();
//        // Mock GameState again
//        GameState mockGameState = mock(GameState.class);
//
//        // Return 3 when asked for LIBRARY building count, to satisfy academic achievement
//        when(mockGameState.getBuildingCount(BuildingType.LIBRARY)).thenReturn(3);
//
//        try (MockedStatic<GameState> gameStateMockedStatic = mockStatic(GameState.class);
//             MockedStatic<EventHandler> eventHandlerMockedStatic = mockStatic(EventHandler.class)) {
//
//            gameStateMockedStatic.when(GameState::getState).thenReturn(mockGameState);
//
//            EventHandler mockEventHandler = mock(EventHandler.class);
//            eventHandlerMockedStatic.when(EventHandler::getEventHandler).thenReturn(mockEventHandler);
//
//            achievementManager.poll();
//
//            assertTrue(achievementManager.academic,
//                "AchievementManager should set academic=true when 3 libraries exist.");
//            verify(mockEventHandler).callEvent(EventHandler.Event.ACADEMIC_ACHIEVEMENT_DIALOG);
//        }
//    }
//
//    @Test
//    void testFoodieAchievement() {
//        AchievementManager achievementManager = new AchievementManager();
//        // Mock GameState again
//        GameState mockGameState = mock(GameState.class);
//
//        // Return 3 when asked for GREGGS building count, to satisfy foodie achievement
//        when(mockGameState.getBuildingCount(BuildingType.GREGGS)).thenReturn(3);
//
//        try (MockedStatic<GameState> gameStateMockedStatic = mockStatic(GameState.class);
//             MockedStatic<EventHandler> eventHandlerMockedStatic = mockStatic(EventHandler.class)) {
//
//            gameStateMockedStatic.when(GameState::getState).thenReturn(mockGameState);
//
//            EventHandler mockEventHandler = mock(EventHandler.class);
//            eventHandlerMockedStatic.when(EventHandler::getEventHandler).thenReturn(mockEventHandler);
//
//            achievementManager.poll();
//
//            assertTrue(achievementManager.foodie,
//                "AchievementManager should set foodie=true when 3 Greggs exist.");
//            verify(mockEventHandler).callEvent(EventHandler.Event.FOODIE_ACHIEVEMENT_DIALOG);
//        }
//    }
//}
