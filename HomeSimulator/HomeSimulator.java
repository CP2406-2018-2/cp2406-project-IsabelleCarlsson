import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HomeSimulator {
    private String houseName;
    private String configPath;
    private List<SmartDevice> deviceList = new ArrayList<>();
    private List<String> roomNameList = new ArrayList<>();
    private List<SimRoom> roomList = new ArrayList<>();

    private HomeSimulator() {
        configPath = "config.txt";
        houseName = "DefaultHouse";
        loadConfig();
        loadRooms();
    }

    public HomeSimulator(String houseName, String configPath) {
        this.configPath = configPath;
        this.houseName = houseName;
        loadConfig();
        loadRooms();
    }

    public SimRoom getRoomByName(String roomName) {
        for (SimRoom room : roomList) {
            if (room.getName().toLowerCase().equals(roomName.toLowerCase()))
                return room;
        }
        return null;
    }

    private void loadConfig() {
//      File config.txt = new File(configPath);
//      Path path = config.txt.toAbsolutePath();
        Path path = Paths.get(configPath);
        String[] deviceArray;
        String s;
        String delimiter = ",";
        String deviceName;
        String deviceRoom;
        int wattage;
        String deviceActive;
        SmartDevice device;

        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println();
            s = reader.readLine();
            while (s != null) {
                deviceArray = s.split(delimiter);
                deviceName = deviceArray[0];
                deviceRoom = deviceArray[1];
                if (!(roomNameList.contains(deviceRoom)))
                    roomNameList.add(deviceRoom);
                wattage = Integer.parseInt(deviceArray[2]);
                deviceActive = deviceArray[3];
                device = new SmartDevice(deviceName, deviceRoom, wattage, Boolean.parseBoolean(deviceActive));
                deviceList.add(device);
                s = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Message: " + e);
        }
    }

    private void loadRooms() {
        for (String room : roomNameList) {
            roomList.add(new SimRoom(room));
        }
        for (SimRoom room : roomList) {
            for (SmartDevice device : deviceList) {
                if (room.getName().equals(device.getRoomName())) {
                    room.addDevice(device);
                }
            }
        }
    }

    public void displayRooms() {
        for (SimRoom room : roomList) {
            room.displayStatus();
        }
    }

    public void displayDevices() {
        for (SmartDevice device : deviceList) {
            device.displayStatus();
        }
    }

    public void start() {
        int hrsRunning = 0;
        int hours = 5;
        int minutes = 0;

        while (hrsRunning <= 23){
            System.out.printf("%d:%02d\n", hours, minutes);
            minutes++;
            if (hours > 23) {
                hours = 0;
            }
            if (minutes > 59) {
                minutes = 0;
                hours++;
                hrsRunning++;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Exception " + e);
            }
        }
    }
}
