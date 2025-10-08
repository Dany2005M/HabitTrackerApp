import java.util.ArrayList;

public class HabitManager {
    private ArrayList<Habit> habits = new ArrayList<>();

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

    protected int daycounter = 1;

    public int getDaycounter() {
        return daycounter;
    }

    public void nextDay(){
        for(Habit habit : habits) {
            if(!habit.isDone()){
                habit.setStreak(0);
            }
            habit.unDone();
        }
        daycounter++;
        System.out.println("Day " + daycounter + "\n");
    }

    private StringBuilder summary = new StringBuilder();

    public StringBuilder getSummary() {
        return summary;
    }

    public void weeklySummary(){
        int max = Integer.MIN_VALUE;
        String maxHabitName = "";
        for(Habit habit : habits){
            if(habit.getStreak() > max){
                max = habit.getStreak();
                maxHabitName = habit.getName();
            }

            summary.append(habit.getName()).append(" | ").append(habit.getTotaldone()).append(" days out of 7").append(" | ").append((int) Math.round(((double)habit.getTotaldone()/7)*100)).append("% completion\n");

            //System.out.println(habit.getName() + " | " + habit.getTotaldone() + " days out of 7" + " | " + Integer.parseInt(String.format("%.2f",(double)habit.getTotaldone()/7))*100 + "% completion\n");
        }
        summary.append("Longest habit streak: ").append(maxHabitName).append(" | ").append("Streak ").append(max);
        //System.out.println("Longest habit streak: " + maxHabitName + " | " + "Streak " + max);

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


