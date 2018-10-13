import javax.swing.*;

public class HomeTest {
    public static void main(String[] args) {
        Home home = new Home();
        home.loadConfig();
        System.out.printf("Time: %s\nTemp: %.2f°C\nLight: %.2f%%\n", home.getTime(), home.getTemperature(),
                home.getSunlight());
        home.run();
        System.out.println(home.isDone());
        home.pause();
        System.out.println(home.isDone());
        home.run();
        System.out.printf("Time: %s\nTemp: %.2f°C\nLight: %.2f%%\n", home.getTime(), home.getTemperature(),
                home.getSunlight());
        home.stop();
        System.out.println(home.isDone());
        for (Room room : home.getRoomList()) {
            room.displayDevices();
            room.displayStatus();
        }
    }
}
