import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HabitManager {
    private final ArrayList<Habit> habits = new ArrayList<>();

    public void saveToFile(String fileName) {
        try(FileWriter writer = new FileWriter(fileName)){
            writer.write(dayCounter + "\n");
            for(Habit habit : habits){
                writer.write(habit.getName() + "," +
                                habit.isDone() + "," +
                                habit.getStreak() + "," +
                                habit.getTotalDone() + "\n");
            }
            System.out.println("Habits saved successfully!");
        }
        catch (IOException e){
            System.out.println("Error saving habits to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName){
        habits.clear();

        try(Scanner scanner = new Scanner(new File(fileName))){
            if(scanner.hasNextLine()){
                dayCounter = Integer.parseInt(scanner.nextLine().trim());
            }
            while(scanner.hasNextLine()){
                String input = scanner.nextLine();
                String[] data = input.split(",");

                if(data.length == 4){
                    Habit habit = new Habit(data[0]);
                    if(Boolean.parseBoolean(data[1])){
                        habit.setDone();
                    }habit.setStreak(Integer.parseInt(data[2]));


                    habit.setTotalDone(Integer.parseInt(data[3]));


                    habits.add(habit);
                }

            } System.out.println("Habit loaded successfully!");

        }catch(IOException e){
            System.out.println("Error loading habits from file: " + e.getMessage());
        }
    }

    public void addHabit(String habitName){
        if(habitInList(habitName) == null){
            habits.add(new  Habit(habitName));
            System.out.println("\nAdded habit: " + habitName + "\n");
        }
        else{
            System.out.println("\nThis habit has already been added\n");
        }
    }

    public Habit habitInList(String habitName){
        Habit habitToRemove = null;
        if(!habits.isEmpty()) {
            for (Habit habit : habits) {
                if (habit.getName().equalsIgnoreCase(habitName)) {
                    habitToRemove = habit;
                }
            }
        }
        return habitToRemove;
    }

    public void removeHabit(String habitName){

        if(habitInList(habitName) != null){
            habits.remove(habitInList(habitName));
            System.out.println("\nRemoved habit: " + habitName + "\n");
        }
        else if(habits.isEmpty()){
            System.out.println("There are no habits added");
        }
        else{
            System.out.println("Please enter a valid habit name");
        }
    }

    public void markHabitAsComplete(String habitName){
        Habit currentHabit = habitInList(habitName);
        if(currentHabit != null){
            currentHabit.setDone();
        }
        else{
            System.out.println("Please enter a valid habit name");
        }
    }

    protected int dayCounter = 1;

    public int getDayCounter() {
        return dayCounter;
    }

    public void nextDay(){
        for(Habit habit : habits) {
            if(!habit.isDone()){
                habit.addStreakToList(habit.getStreak());
                habit.setStreak(0);
            }
            habit.unDone();
        }
        dayCounter++;
        System.out.println("Day " + dayCounter + "\n");
    }

    private final StringBuilder summary = new StringBuilder();

    public StringBuilder getSummary() {
        return summary;
    }

    public void weeklySummary(){
        summary.setLength(0);
        if(habits.isEmpty()){
            summary.append("No habits tracked this week");
            return;
        }
        int max = Integer.MIN_VALUE;
        String maxHabitName = "";
        for(Habit habit : habits){
            int maxStreak = Integer.MIN_VALUE;
            for(int streak : habit.getStreakList()){
                if(streak > maxStreak){
                    maxStreak = streak;
                }
            }
            if(maxStreak > max){
                max = maxStreak;
                maxHabitName = habit.getName();
            }
            summary.append(habit.getName()).append(" | ").append(habit.getTotalDone()).append(" days out of 7").append(" | ").append((int) Math.round(((double)habit.getTotalDone()/7)*100)).append("% completion\n");

        }
        summary.append("Longest habit streak: ").append(maxHabitName).append(" | ").append("Streak ").append(Math.max(max, 0));
        clearTotalDone();
        clearStreakList();
    }

    public void clearStreakList(){
        for(Habit habit : habits){
            habit.getStreakList().clear();
        }
    }

    public void clearTotalDone(){
        for(Habit habit : habits){
            habit.setTotalDone(0);
        }
    }


    public void showAllHabits(){

        int counter = 1;
        for(Habit habit : habits){
            System.out.println(counter + "." + " " + habit.getName() + " | " + "Streak = " + habit.getStreak() + "\n");
            counter++;
        }
    }

    public boolean isEmpty(){
        return habits.isEmpty();
    }

    public ArrayList<Habit> getHabits() {
        return habits;
    }
}


