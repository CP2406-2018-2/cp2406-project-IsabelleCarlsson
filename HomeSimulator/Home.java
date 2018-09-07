import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Home {
    private String configPath;
    private List<String> roomNameList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();

    private Home() {
        configPath = "config.txt";
        loadConfig();
    }

    public Home(String configPath) {
        this.configPath = configPath;
        loadConfig();
    }

    public Room getRoomByName(String roomName) {
        for (Room room : roomList) {
            if (room.getName().toLowerCase().equals(roomName.toLowerCase()))
                return room;
        }
        return null;
    }

    public void updateRooms(String time, double temperature, double sunlight) {
        for (Room room : roomList) {
            room.updateDevices(time, temperature, sunlight);
        }
    }

    // E.g. a rule for controlling the sprinkler might look like this:
    //
    // sprinkler on: time = 3pm and garden < 30% moister, off: time = 4pm

    private void loadConfig() {
        Path path = Paths.get(configPath);
        String[] deviceArray;
        String line;
        String delimiter = ",";

        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println();
            line = reader.readLine();
            while (line != null) {
                if (line.contains("rules")) {
                    line = reader.readLine();

                } else {
                    System.out.println("Not Empty");
                    deviceArray = line.split(delimiter);
                    String roomName = deviceArray[deviceArray.length - 1];
                    Room room = new Room(roomName);
                    roomList.add(room);
                    for (int i = 0; i < deviceArray.length - 1; i++) {
                        Device device = new Device(deviceArray[i]);
                        room.addDevice(device);
                    }
                    line = reader.readLine();
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Message: " + e);
        }
    }

    public void displayDevices() {
        for (Room room : roomList) {
            room.displayDevices();
        }
    }

    public void start() {
        double temperature = 18;
        double sunlight = 0;
        int hourCount = 0;
        int hours = 5;
        int minutes = 0;
        String currentTime;
        while (hourCount <= 23){
            System.out.printf("\nTemp: %.2f °C", temperature);
            System.out.printf("\nLight: %.2f %%" , sunlight);
            currentTime = String.format("\nTime: %d:%02d", hours, minutes);
            updateRooms(currentTime, temperature, sunlight);
            System.out.println(currentTime);
            minutes++;

            if (hourCount < 6) {
                temperature+= 0.03;
            } else if (hourCount > 6) {
                temperature-= 0.0131;
            }

            if (hourCount == 0){
                sunlight += 1.6666666666666666666666666666667;
            } else if (hourCount == 12) {
                sunlight -= 1.6666666666666666666666666666667;
            }

            if (hours > 23) {
                hours = 0;
            }
            if (minutes > 59) {
                minutes = 0;
                hours++;
                hourCount++;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }
    }
}