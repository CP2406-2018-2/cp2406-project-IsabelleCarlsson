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
    private int hourCount;
    private int hours;
    private int minutes;
    private String time;
    private double temperature;
    private double sunlight;

    public Home() {
    }

    private void updateRooms(String time, double temperature, double sunlight) {
        for (Room room : roomList) {
            room.updateEnvironVars(time, temperature, sunlight);
        }
    }

    public void updateDevices(String time, double temperature, double sunlight) {
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
                deviceArray = line.split(delimiter, 9);
                String roomName = deviceArray[0];
                Device device = new Device(deviceArray[1], roomName);
                Room newRoom = new Room(roomName);

                // Set device control conditions
                if (!(deviceArray[2].isEmpty())) {
                    device.setTimeControlled(true);
                    device.setOnCondition(deviceArray[7]);
                    device.setOffCondition(deviceArray[8]);
                } else if (!(deviceArray[3].isEmpty())) {
                    device.setTempControlled(true);
                    device.setOnCondition(deviceArray[7]);
                    device.setOffCondition(deviceArray[8]);
                } else if (!(deviceArray[4].isEmpty())) {
                    device.setLightControlled(true);
                    device.setOnCondition(deviceArray[7]);
                    device.setOffCondition(deviceArray[8]);
                } else if (!(deviceArray[5].isEmpty())) {
                    device.setMotionControlled(true);
                    device.setMotionSensor(deviceArray[6]);
                    device.setOnCondition(deviceArray[7]);
                    device.setOffCondition(deviceArray[8]);
                }

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
        } catch (Exception e) {
            System.out.println("Message: " + e);
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
            final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    while (!isDone.get()) {
                        if (isRunning.get()) {
                            try {
                                Thread.sleep(1000); // sleep for a second
                                minutes++;

                                if (hourCount < 7) {
                                    temperature += 0.03;
                                } else if (hourCount > 7) {
                                    temperature -= 0.0131;
                                }


                                if (hourCount == 0) { // 0 to 100 during first hour of simulation
                                    sunlight += 1.6666666666666666666666666666667;
                                } else if (hourCount == 12) { // 100 to 0 during first hour of simulation
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

                                if (hourCount >= RUNNING_TIME) {
                                    stop();
                                }

                                time = String.format("\nTime: %d:%02d", hours, minutes);
                                updateRooms(time, temperature, sunlight);
                                updateDevices(time, temperature, sunlight);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
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
}
