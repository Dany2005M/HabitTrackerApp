import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HabitManager habits = new HabitManager();
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Day 1\n");
        while (flag) {
            StringBuilder sb = new StringBuilder();
            sb.append("Options Menu\n").append("1. Add Habit\n").append("2. Remove Habit\n").append("3. List Habits\n").append("4. Mark habit as done\n").append("5. Go to the next day\n").append("6. Show a weekly summary\n").append("7. Exit\n");
            System.out.print(sb);
            input = scanner.nextLine();
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
                case "5" -> {
                    habits.nextDay();
                }
                case "6" -> {
                    if (habits.getDaycounter() == 7) {
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

