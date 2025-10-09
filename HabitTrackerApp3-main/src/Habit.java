import java.util.ArrayList;

public class Habit {

    private boolean done;
    private final String name;
    private int streak;
    private int totalDone = 0;
    private ArrayList<Integer> streaks = new ArrayList<>();
    public Habit(String name) {
        this.done = false;
        this.name = name;
        this.streak = 0;
    }

    public void setDone() {
        if (!done) {
            done = true;
            streak++;
            totalDone++;
        }
    }

    public void unDone(){
        done = false;
    }

    public String getName() {
        return name;
    }

    public int getStreak() {
        return streak;
    }

    public boolean isDone() {
        return done;
    }

    public void addStreakToList(int streak){
        streaks.add(streak);
    }

    public ArrayList<Integer> getStreakList() {
        return streaks;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getTotalDone() {
        return totalDone;
    }

    public void setTotalDone(int totalDone) {
        this.totalDone = totalDone;
    }

}