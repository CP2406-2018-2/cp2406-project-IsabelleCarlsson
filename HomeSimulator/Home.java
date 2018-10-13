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
    private static final String CONFIG_PATH = "res/config.txt";
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

    public List<Room> getRoomList() {
        return roomList;
    }

    public void loadConfig() {
        Path path = Paths.get(CONFIG_PATH);
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
