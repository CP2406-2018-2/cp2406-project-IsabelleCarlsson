import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Home {
    private String configPath;
    private List<Room> roomList = new ArrayList<>();
    protected String time;
    protected double temperature;
    protected double sunlight;

    public Home() {
        configPath = "res/config.txt";
        loadConfig();
    }

    public Home(String configPath) {
        this.configPath = configPath;
        loadConfig();
    }

    private void updateRooms(String time, double temperature, double sunlight) {
        for (Room room : roomList) {
            room.updateEnvironVars(time, temperature, sunlight);
        }
    }

    private void updateDevices(String time, double temperature, double sunlight) {
        for (Room room : roomList) {
            room.updateDevices(time, temperature, sunlight);
        }
    }

    public String getTime() {
        return time;
    }

    public double getSunlight() {
        return sunlight;
    }

    public double getTemperature() {
        return temperature;
    }

    public void loadConfig() {
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
                if (line.contains("room rules")) {
                    while (line != null) {
                        line = reader.readLine();
                    }
                } else if (line.contains("device rules")) {
                    while (line != null) {
                        line = reader.readLine();
                    }
                } else {
                    deviceArray = line.split(delimiter);
                    String roomName = deviceArray[deviceArray.length - 1];
                    Room room = new Room(roomName);
                    roomList.add(room);
                    for (int i = 0; i < deviceArray.length - 1; i++) {
                        Device device = new Device(deviceArray[i]);
                        device.setRoomName(roomName);
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

    public void displayRooms() {
        for (Room room : roomList) {
            room.displayStatus();
        }
    }

    public void start() {
        temperature = 18;
        sunlight = 0;
        int hourCount = 0;
        int hours = 5;
        int minutes = 0;

        // Environmental variables
        while (hourCount <= 23) {
            time = String.format("\nTime: %d:%02d", hours, minutes);
            updateRooms(time, temperature, sunlight);
            updateDevices(time, temperature, sunlight);
            minutes++;

            if (hourCount < 7) {
                temperature += 0.03;
            } else if (hourCount > 7) {
                temperature -= 0.0131;
            }

            if (hourCount == 0) {
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
                Thread.sleep(1);
            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }
    }
}
