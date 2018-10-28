import java.awt.*;
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
    private List<Device> motionSensors = new ArrayList<>();
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
                    if (time.equals(device.getOnCondition()) && !device.isOn()) {
                        device.setOn(true);
                        device.setColor(device.getColor().brighter());
                    } else if (time.equals(device.getOffCondition()) && device.isOn()) {
                        device.setOn(false);
                        device.setColor(device.getColor().darker());
                    }
                } else if (device.isTempControlled()) {
                    if (temperature == Double.parseDouble(device.getOnCondition()) && !device.isOn()) {
                        device.setOn(true);
                        device.setColor(device.getColor().brighter());
                    } else if (temperature == Double.parseDouble(device.getOffCondition()) && device.isOn()) {
                        device.setOn(false);
                        device.setColor(device.getColor().darker());
                    }
                } else if (device.isMotionControlled()) {
                    for (Device motionSensor : motionSensors) {
                        if ((motionSensor.isOn() && !device.isOn()) &&
                                (motionSensor.getRoomName().equals(device.getRoomName()))) {
                            device.setOn(true);
                            device.setColor(device.getColor().brighter());
                        } else if ((!motionSensor.isOn() && device.isOn()) &&
                                (motionSensor.getRoomName().equals(device.getRoomName()))) {
                            device.setOn(false);
                            device.setColor(device.getColor().darker());
                        }
                    }
                }

                // Change colour of room when light is on
                if ((device.isLight() && device.isOn()) && (device.getRoomName().equals(room.getName()))) {
                    room.setColor(new Color(139,69,19));
                } else if ((device.isLight() && !device.isOn()) && (device.getRoomName().equals(room.getName()))) {
                    room.setColor(new Color(99,49,0));
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
        String deviceName;
        String roomName;
        String onCondition;
        String offCondition;
        int electUsage;
        boolean isFixture = false;
        roomList.clear();

        try {
            InputStream input = new BufferedInputStream(Files.newInputStream(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            reader.readLine(); // skip first line in csv file
            line = reader.readLine();
            while (line != null) {
                deviceArray = line.split(delimiter, 11);
                roomName = deviceArray[0];
                deviceName = deviceArray[1];
                electUsage = Integer.parseInt(deviceArray[2]);
                if (!(deviceArray[3] == null))
                    isFixture = true;
                onCondition = deviceArray[9];
                offCondition = deviceArray[10];
                Device device = new Device(deviceName, roomName, isFixture, electUsage);
                Room newRoom = new Room(roomName);

                // Set device trigger controller and condition
                if ((Boolean.parseBoolean(deviceArray[5]))) {
                    device.isLight(true);
                }
                if (Boolean.parseBoolean(deviceArray[6])) {
                    device.setTimeControlled(true);
                    device.setOnCondition(onCondition);
                    device.setOffCondition(offCondition);
                } else if (Boolean.parseBoolean(deviceArray[7])) {
                    device.setTempControlled(true);
                    device.setOnCondition(onCondition);
                    device.setOffCondition(offCondition);
                } else if (Boolean.parseBoolean(deviceArray[8])) {
                    device.setMotionControlled(true);
                }

                if (Boolean.parseBoolean(deviceArray[4])) {
                    device.isMotionSensor(true);
                    motionSensors.add(device);
                }

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

                                if (hourCount < 7) {
                                    temperature += 0.02857142857142857142857142857143; // 12°C / (7 h * 60 min)
                                } else if (hourCount > 7) {
                                    temperature -= 0.0125; // 12°C / (16 h * 60 min)
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
