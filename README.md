# longboi-life

*University of York ENG1 Project.*

Short single-player game that allows the player to build their own university
campus trying to reach the highest student satisfaction possible.

A [libGDX](https://libgdx.com/) project.

## Running the game
This game uses Java version 17 and higher, please ensure that you have a Java version that is compatible with this.
One distribution of Java that works well with the game can be found here: https://adoptium.net/temurin/releases/?package=jdk&version=17
This link takes you to a download of the JDK (needed to run the game) for a version 17 distribution. You may use a newer version too.

If you want to run the game through the command line please use this syntax:
`java -jar longboi-life-{version}.jar`
Otherwise, if you are trying to run it on Windows and you are experienceing trouble by double clicking the .jar file, try right clicking and ensuring it is opening the .jar with the correct Java version.

If there are any issues, please create an issue on the GitHub.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should
be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## DevUtils

`/DevUtils/` contains useful scripts (not necessarily in Java) for developing the project.
