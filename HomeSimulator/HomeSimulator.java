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

    private HomeSimulator() {
        configPath = "file.txt";
        houseName = "DefaultHouse";
        loadConfig();
    }

    public HomeSimulator(String houseName, String configPath) {
        this.configPath = configPath;
        this.houseName = houseName;
    }

    private void loadConfig() {
//      Path inputPath = Paths.get("file");
//      Path file = inputPath.toAbsolutePath();
        Path file = Paths.get(configPath);
        String[] array;
        String s;
        String delimiter = ",";
        String deviceName;
        int wattage;
        String deviceActive;
        SmartDevice device;

        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(file));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println();
            s = reader.readLine();
            while (s != null) {
                array = s.split(delimiter);
                deviceName = array[0];
                wattage = Integer.parseInt(array[1]);
                deviceActive = array[3];
                device = new SmartDevice(deviceName, wattage, Boolean.parseBoolean(deviceActive));
                deviceList.add(device);
                s = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Message: " + e);
        }
    }
}
