Project development guidelines for HabitTrackerApp

Audience: This document targets contributors familiar with Java SE who will extend or debug this repository. It focuses on project-specific details rather than generic Java information.

Repository overview
- Language/Toolchain: Java SE (tested with javac 25). No Maven/Gradle; compile with javac directly.
- Packages: None. All classes are in the default package.
- Source layout:
  - src/Habit.java — data model for a habit (name, done flag, streak, totaldone)
  - src/HabitManager.java — manages a list of Habit and provides operations (add/remove/mark-done/advance day/summary)
  - src/Main.java — intended interactive CLI, but the current source does not expose a standard public static void main(String[]). Treat as non-canonical for running; see notes below.
- Binaries: out/production/HabitTrackerApp holds compiled .class files.

Build and configuration
Because there is no build system, compile with javac and run with java.
- Clean compile (without Main.java):
  - javac -d out/production/HabitTrackerApp src/Habit.java src/HabitManager.java
- If you need to attempt compilation of all sources:
  - javac -d out/production/HabitTrackerApp src/*.java
  - Note: As of this commit, Main.java may not compile on some JDKs due to missing class wrapper and imports; prefer compiling specific sources unless you have fixed Main.

Running
- There is no guaranteed entry point class. The existing Main.java is not a conventional Java entry point. For ad-hoc runs, create a small runner with a public static void main(String[]) as shown in the Testing section.

Testing
The project has no formal test framework configured. Use lightweight ad-hoc tests compiled and run with javac/java. Java assertions (-ea) are sufficient for simple invariants.

How to create and run a simple test (verified)
1) Create a temporary test class (e.g., src/HabitAppTest.java):

   import java.util.*;
   
   public class HabitAppTest {
       public static void main(String[] args) {
           HabitManager manager = new HabitManager();
           manager.addHabit("Drink Water");
           manager.addHabit("Exercise");
           manager.addHabit("Drink Water"); // duplicate, should not add
   
           manager.markHabitAsComplete("Drink Water");
           manager.nextDay();
           manager.markHabitAsComplete("Drink Water");
           manager.markHabitAsComplete("Exercise");
   
           boolean foundDrinkWater = false;
           boolean foundExercise = false;
           for (Habit h : manager.habits) { // direct access due to simple setup
               if (h.getName().equals("Drink Water")) {
                   foundDrinkWater = true;
                   assert h.getStreak() >= 1 : "Drink Water streak should be >=1";
               }
               if (h.getName().equals("Exercise")) {
                   foundExercise = true;
                   assert h.getStreak() >= 1 : "Exercise streak should be >=1 after day 2";
               }
           }
           assert foundDrinkWater && foundExercise : "Both habits should exist";
           System.out.println("[TEST PASSED] Basic HabitManager flow works");
       }
   }

2) Compile:
   - javac -d out/production/HabitTrackerApp src/Habit.java src/HabitManager.java src/HabitAppTest.java

3) Run with assertions enabled:
   - java -ea -cp out/production/HabitTrackerApp HabitAppTest

Expected output (verified):
   Added habit: Drink Water
   Added habit: Exercise
   This habit has already been added
   Day 2
   [TEST PASSED] Basic HabitManager flow works

4) Clean up: Remove the temporary test file after use to keep the repo clean.
   - rm src/HabitAppTest.java

Guidelines for adding more tests
- Keep tests package-less to match the current source layout.
- Prefer assertion-based checks with clearly printed PASS/FAIL messages.
- Compile tests together with the production sources into the existing out/production/HabitTrackerApp.
- If you introduce JUnit or another framework in the future, add a simple build tool (Gradle/Maven) to manage dependencies, and move sources into standard directories (src/main/java, src/test/java) with a base package.

Development notes and pitfalls
- HabitManager.habits visibility: The list is package-private (default) and thus directly accessible from tests or other classes in the default package. For stricter encapsulation, consider making it private and exposing read-only views or query methods.
- Duplicate detection: addHabit uses a case-insensitive name check (equalsIgnoreCase). It prints a message and does not add duplicates.
- Day and streak logic:
  - markHabitAsComplete sets Habit.done=true, increments streak and totaldone once per day; repeated marking in the same day has no effect (guarded by done flag).
  - nextDay resets streak to 0 for habits not completed that day, clears done flags for all habits, and increments daycounter.
- weeklySummary formatting bug: The percentage calculation
    Integer.parseInt(String.format("%.2f", (double) habit.getTotaldone()/7)) * 100
  is fragile and can throw NumberFormatException (decimal not an integer). Prefer using double math and formatting properly in future changes, e.g.,
    double pct = habit.getTotaldone() / 7.0 * 100.0;
    System.out.printf("%.2f%%", pct);
- Main.java caveats: The current Main.java in the repo appears to be an IDE snippet rather than a standard Java entry point (no class wrapper, missing import java.util.Scanner). If you intend to ship a CLI, replace it with a conventional class:

   import java.util.Scanner;
   
   public class Main {
       public static void main(String[] args) {
           // existing menu logic adapted here
       }
   }

Code style and conventions
- Stay consistent with current naming and default-package approach until a refactor introduces packages.
- Use explicit access modifiers (private/protected/public) going forward; many fields/methods default to package-private.
- Avoid printing from core model classes in future design; keep I/O in UI or service layers.
- Target JDK 17+ (verified with JDK 25). Avoid preview features unless documented.

Environment notes
- Verified with: javac 25, java 25.
- out/production/HabitTrackerApp is used as the class output directory by IntelliJ; keep using it for consistency until a build tool is introduced.

Cleanup policy
- Do not commit ad-hoc test helpers or runners. Create them temporarily to verify changes, run them, and delete them before committing. The only persistent documentation file added by this guideline is .junie/guidelines.md.
