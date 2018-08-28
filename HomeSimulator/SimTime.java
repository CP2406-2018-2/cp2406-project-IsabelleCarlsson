import java.util.Timer;
import java.util.TimerTask;

public class SimTime {
    private Timer timer;
    private int tickRateMs; // The tick rate in seconds for each simulated minute
    private String currentTime;

    public SimTime() {
        tickRateMs = 1000;
        timer = new Timer();
        timer.schedule(new RepeatTask(), 0, tickRateMs);
    }

    public SimTime(int tickRateMs) {
        this.tickRateMs = tickRateMs;
        timer = new Timer();
        timer.schedule(new RepeatTask(), 0, tickRateMs);
    }

    public String getTime() {
        return currentTime;
    }

    public void stopTime() {
        timer.cancel();
    }

    class RepeatTask extends TimerTask {
        int hrsRunning = 24; // How long the simulated time should run
        int hours = 5;
        int minutes = 0;
        int hourCount = 0;

        public void run() {
            // Method that updates the simulated clock hours and minutes
            if (hourCount < hrsRunning) {
                currentTime = String.format("%d:%02d", hours, minutes);
                // For testing purposes
                // System.out.println(currentTime);
                minutes++;
            } else {
                System.out.println("Simulator finished!");
                stopTime();
            }
            // Set hours back to 0 for 24 hour clock
            if (hours > 23) {
                hours = 0;
            }
            // Set minutes back to 0 for 60 minute clock
            if (minutes > 59) {
                minutes = 0;
                hours++;
                hourCount++;
            }
        }
    }
}

