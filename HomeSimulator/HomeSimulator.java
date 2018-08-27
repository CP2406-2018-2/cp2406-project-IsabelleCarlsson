import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        configPath = "file.txt";
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

    private void loadConfig() {
//      Path inputPath = Paths.get("file");
//      Path file = inputPath.toAbsolutePath();
        Path file = Paths.get(configPath);
        String[] deviceArray;
        String s;
        String delimiter = ",";
        String deviceName;
        String deviceRoom;
        int wattage;
        String deviceActive;
        SmartDevice device;

        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(file));
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
        for (String room: roomNameList) {
            roomList.add(new SimRoom(room));
        }
        for (SimRoom room: roomList){
            for (SmartDevice device: deviceList) {
                if (room.getRoomName().equals(device.getRoomName())) {
                    room.addDevice(device);
                }
            }
        }
    }

    public void runSimTime(double simMinPerSec){
        int pauseFreqMs = 10000;
        int simRunHrs = 24;
        int hours = 5;
        int minutes = 0;
        int hourCount = 0;
        int pauseFreqSec = pauseFreqMs/1000;
        simMinPerSec = simMinPerSec * pauseFreqSec;
        while (hourCount < simRunHrs) {
            //Print simulated time
            System.out.printf("%d:%02d\n", hours, minutes);
            //Pause for updateFreqMs amount of time
            try {
                Thread.sleep(pauseFreqMs);
            } catch (Exception e) {
                System.out.println("Message: " + e);
            }
            minutes+= simMinPerSec;
            // Set hours back to 0 for 24 hour clock
            if (hours > 23) {
                hours = 0;
            }
            // Set minutes back to 0 for 60 minute clock
            if (minutes > 59){
                minutes = minutes%60;
                hours++;
                hourCount++;
            }
        }
    }

    public void displayRooms(){
        for (SimRoom room: roomList) {
            System.out.println(room.getRoomName());
        }
    }

    public void displayDevices(){
        for (SmartDevice device: deviceList) {
            device.displayStatus();
        }
    }
}
