package com.spacecomplexity.longboilife.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.spacecomplexity.longboilife.Main;

/**
 * Launches the desktop (LWJGL3) application.
 *
 * <p>This class serves as the entry point for starting the LWJGL3 desktop application using the
 * LibGDX backend. It handles platform-specific startup logic, such as ensuring proper JVM usage on
 * macOS, and sets up the default application configuration before creating and launching the
 * application instance.
 *
 * <p><strong>Main Responsibilities:</strong>
 *
 * <ul>
 *   <li>Checks if a new JVM startup is required for macOS support and exits early if needed.
 *   <li>Configures the LWJGL3 application window settings, including title, resolution, Vsync, FPS
 *       limits, and window icons.
 *   <li>Creates and launches the LWJGL3 application using the main application class ({@link
 *       Main}).
 * </ul>
 *
 * <p><strong>Usage:</strong>
 *
 * <pre>{@code
 * public static void main(String[] args) {
 *     Lwjgl3Launcher.main(args);
 * }
 * }</pre>
 *
 * <p>Launch this class to start the desktop version of the application.
 *
 * @see com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
 * @see com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
 * @see com.spacecomplexity.longboilife.Main
 */
public class Lwjgl3Launcher {
  /**
   * The main entry point for the application.
   *
   * <p>Checks if a new JVM should be started (specific to macOS support) before creating the
   * application. If a new JVM is required, the current process exits early.
   *
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    if (StartupHelper.startNewJvmIfRequired()) {
      return; // This handles macOS support and helps on Windows.
    }
    createApplication();
  }

  /**
   * Creates and returns a new {@link Lwjgl3Application} instance configured with the default
   * settings.
   *
   * @return a new instance of {@link Lwjgl3Application}
   */
  private static Lwjgl3Application createApplication() {
    return new Lwjgl3Application(new Main(), getDefaultConfiguration());
  }

  /**
   * Builds and returns the default {@link Lwjgl3ApplicationConfiguration} for the application.
   *
   * <p>This configuration includes settings for window title, Vsync, FPS limit, windowed mode size,
   * window size limits, and window icons.
   *
   * @return the default {@link Lwjgl3ApplicationConfiguration}
   */
  private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
    Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
    configuration.setTitle("longboi-life");
    //// Vsync limits the frames per second to what your hardware can display, and helps eliminate
    //// screen tearing. This setting doesn't always work on Linux, so the line after is a
    // safeguard.
    configuration.useVsync(true);
    //// Limits FPS to the refresh rate of the currently active monitor, plus 1 to try to match
    // fractional
    //// refresh rates. The Vsync setting above should limit the actual FPS to match the monitor.
    configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
    //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can
    // be
    //// useful for testing performance, but can also be very stressful to some hardware.
    //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen
    // tearing.
    configuration.setWindowedMode(640, 480);
    configuration.setWindowSizeLimits(640, 480, 99999, 99999);
    //// You can change these files; they are in lwjgl3/src/main/resources/ .
    configuration.setWindowIcon("icon128.png", "icon64.png", "icon32.png", "icon16.png");
    return configuration;
  }
}
