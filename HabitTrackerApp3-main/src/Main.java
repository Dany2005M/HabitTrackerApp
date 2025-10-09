import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HabitManager habits = new HabitManager();
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Day 1\n");
        while (flag) {
            System.out.print("""
                    Options Menu
                    1. Add Habit
                    2. Remove Habit
                    3. List Habits
                    4. Mark habit as done
                    5. Go to the next day
                    6. Show a weekly summary
                    7. Exit
                    """);
            input = scanner.nextLine();
            if((habits.getDayCounter()) % 7 == 0){
                habits.weeklySummary();
                System.out.println(habits.getSummary());
            }
            switch (input) {
                case "1" -> {
                    System.out.println("Enter the habit's name: ");
                    input = scanner.nextLine();
                    habits.addHabit(input);
                }
                case "2" -> {
                    System.out.println("Enter the habit's name: ");
                    input = scanner.nextLine();
                    habits.removeHabit(input);
                }
                case "3" -> {
                    if (!habits.isEmpty()) {
                        habits.showAllHabits();
                    } else {
                        System.out.println("You have to add at least one habit");
                    }
                }
                case "4" -> {
                    if (habits.isEmpty()) {
                        System.out.println("There are no habits added");
                    } else {
                        System.out.println("Please enter a habit");
                        input = scanner.nextLine();
                        habits.markHabitAsComplete(input);
                        System.out.println("Habit marked as done");
                    }
                }
                case "5" -> habits.nextDay();
                case "6" -> {
                    if (habits.getDayCounter() == 7) {
                        habits.weeklySummary();
                    } else {
                        System.out.println("You need to finish a week");
                    }

                }
                case "7" -> flag = false;
                default -> System.out.println("Please enter a valid number for the desired option\n");
            }
        }
    }
}

