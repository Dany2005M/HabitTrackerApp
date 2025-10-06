public class Habit {

    private boolean done;
    private String name;
    private int streak;
    private int totaldone = 0;

    public Habit(String name) {
        this.done = false;
        this.name = name;
        this.streak = 0;
    }

    public void setDone() {
        if (!done) {
            done = true;
            streak++;
            totaldone++;
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

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getTotaldone() {
        return totaldone;
    }
}