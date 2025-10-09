import java.util.ArrayList;

public class HabitManager {
    private final ArrayList<Habit> habits = new ArrayList<>();

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
}


