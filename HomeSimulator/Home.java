import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.SwingWorker;

public class Home {
    private final int RUNNING_TIME = 24; // Running time in simulated hours
    private static final String CONFIG_PATH = "res/config.csv";
    private AtomicBoolean isStarted = new AtomicBoolean(false);
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private AtomicBoolean isDone = new AtomicBoolean(false);
    private List<Room> roomList = new ArrayList<>();
    private int[] motionSensors = new int[99];
    private int hourCount;
    private int hours;
    private int minutes;
    private String time;
    private double temperature;
    private double sunlight;
    private String errorMessage = "";
    private boolean isConfigLoaded;
    private int electUsage;

    public Home() {
        isConfigLoaded = false;
    }

    private void updateRooms(String time, double temperature, double sunlight) {
        for (Room room : roomList) {
            room.updateRoom(time, temperature, sunlight);
        }
    }

    public void updateDevices(String time, double temperature, double sunlight) {
        for (Room room : roomList) {
            room.updateDevices(time, temperature, sunlight);

        }
    }

    private void checkConditions() {
        for (Room room : roomList) {
            for (Device device : room.getDeviceList()) {
                if (device.isTimeControlled()) {
                    if (time.equals(device.getOnCondition())) {
                        device.toggleActive();
                        device.setColor(device.getColor().brighter());
                    } else if (time.equals(device.getOffCondition())) {
                        device.toggleActive();
                        device.setColor(device.getColor().darker());
                    }
                } else if (device.isTempControlled()) {
                    if (temperature == Double.parseDouble(device.getOnCondition())) {
                        device.toggleActive();
                        device.setColor(device.getColor().brighter());
                    } else if (temperature == Double.parseDouble(device.getOffCondition())) {
                        device.toggleActive();
                        device.setColor(device.getColor().darker());
                    }
                } else if (device.isMotionControlled()) {
                /*
                if motion sensor device is on
                    turn on device
                else if motion sensor device is off
                    turn off device
                 */
                }
            }
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

    public List<Room> getRoomList() {
        return roomList;
    }

    public Room getRoomByName(String roomName) {
        for (Room room : roomList) {
            if (room.getName().equals(roomName))
                return room;
        }
        return null;
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    public void loadConfig() {
        Path path = Paths.get(CONFIG_PATH);
        String[] deviceArray;
        String line;
        String delimiter = ",";
        roomList.clear();
        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            reader.readLine(); // skip first line in csv file
            line = reader.readLine();
            while (line != null) {
                deviceArray = line.split(delimiter, 11);
                String roomName = deviceArray[0];
                Device device = new Device(deviceArray[1], roomName, Boolean.parseBoolean(deviceArray[2]),
                        Integer.parseInt(deviceArray[3]));
                Room newRoom = new Room(roomName);

                // Set device trigger controller and condition
                if (!(deviceArray[4].isEmpty())) {
                    device.setTimeControlled(true);
                    device.setOnCondition(deviceArray[9]);
                    device.setOffCondition(deviceArray[10]);
                } else if (!(deviceArray[5].isEmpty())) {
                    device.setTempControlled(true);
                    device.setOnCondition(deviceArray[9]);
                    device.setOffCondition(deviceArray[10]);
                } else if (!(deviceArray[7].isEmpty())) {
                    device.setMotionControlled(true);
                    device.setMotionSensor(deviceArray[8]);
                    device.setOnCondition(deviceArray[9]);
                    device.setOffCondition(deviceArray[10]);
                }

                // Check if device is a light
                if (!(deviceArray[6].isEmpty()))
                    device.isLight(true);

                // Create new room if not existent
                if (getRoomByName(roomName) == null) {
                    newRoom.addDevice(device);
                    roomList.add(newRoom);
                } else {
                    Room existingRoom = getRoomByName(roomName);
                    existingRoom.addDevice(device);
                }
                line = reader.readLine();
            }
            reader.close();
            isConfigLoaded = true;
        } catch (Exception e) {
            errorMessage = "Error: Invalid Config File";
        }
    }

    public void run() {
        isRunning.set(true);
        if (!isStarted.get()) {
            isDone.set(false);
            isStarted.set(true);
            time = null;
            hours = 5;
            minutes = 0;
            hourCount = 0;
            temperature = 18;
            sunlight = 0;
            time = String.format("%d:%02d", hours, minutes);
            final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    while (!isDone.get()) {
                        if (isRunning.get()) {
                            try {
                                Thread.sleep(1000); // pause for a second
                                minutes++;

                                if (hourCount < 7) { // Decrease by
                                    temperature += 0.03;
                                } else if (hourCount > 7) {
                                    temperature -= 0.0131;
                                }

                                if (hourCount == 0) { // 0 to 100% during first hour of simulation
                                    sunlight += 1.6666666666666666666666666666667;
                                } else if (hourCount == 12) { // 100% to 0 at hour 17 of simulation
                                    sunlight -= 1.6666666666666666666666666666667;
                                }

                                // Reset hours when over 23
                                if (hours > 23) {
                                    hours = 0;
                                }

                                // Reset minutes when over 59
                                if (minutes > 59) {
                                    minutes = 0;
                                    hours++;
                                    hourCount++;
                                }

                                // Limit the simulation time
                                if (hourCount >= RUNNING_TIME) {
                                    stop();
                                }

                                // Update all variables of the simulation
                                time = String.format("%d:%02d", hours, minutes);
                                updateRooms(time, temperature, sunlight);
                                updateDevices(time, temperature, sunlight);
                                checkConditions();
                                calcElectUsage();
                            } catch (Exception e) {
                                errorMessage = "Error: " + e;
                            }
                        }
                    }
                    return null;
                }
            };
            worker.execute();
        }
    }

    public void pause() {
        this.isRunning.set(false);
    }

    public void stop() {
        isDone.set(true);
        isStarted.set(false);
        isRunning.set(false);
        time = null;
    }

    public boolean isDone() {
        return isDone.get();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void calcElectUsage() {
        electUsage = 0;
        for (Room room : roomList) {
            electUsage += room.getElectUsage();
        }
    }

    public int getElectUsage() {
        return electUsage;
    }

    public boolean isConfigLoaded() {
        return isConfigLoaded;
    }
}
