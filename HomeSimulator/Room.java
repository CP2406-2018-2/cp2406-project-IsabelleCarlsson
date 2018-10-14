import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private String name;
    private boolean isActive;
    private String time;
    private double temperature;
    private double sunlight;
    private List<Device> deviceList = new ArrayList<>();
    private double electUsage;

    int size;
    int red = random.nextInt(256);
    int green = random.nextInt(256);
    int blue = random.nextInt(256);
    private Color color = new Color(red, green, blue);
    private static Random random = new Random();

    public Room() {
        name = "room";
        isActive = false;
        size = 50;
    }

    public Room(String name) {
        this.name = name;
        isActive = false;
        size = 50;
    }

    public Color getColor() {
        return color;
    }

    public void displayDevices() {
        for (Device device : deviceList) {
            device.displayStatus();
        }
    }

    public void updateEnvironVars(String time, double temperature, double sunlight) {
        // Updates environmental variables
        setTime(time);
        setTemperature(temperature);
        setSunlight(sunlight);
    }

    public void updateDevices(String time, double temperature, double sunlight) {
        for (Device device : deviceList) {
            device.updateEnvironVars(time, temperature, sunlight);
        }
    }

    public String getName() {
        return name;
    }

    private void setTime(String time) {
        this.time = time;
    }

    private void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    private void setSunlight(double sunlight) {
        this.sunlight = sunlight;
    }

    public String getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getSunlight() {
        return sunlight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void displayStatus() {
        System.out.println("Room: " + name + "\nTemperature: " + getTemperature() + "\nUsage: " + getElectUsage() +
                "\nActive: " + isActive + "\n");
    }

    public void addDevice(Device device) {
        deviceList.add(device);
    }

    public double getElectUsage() {
        for (Device device : deviceList) {
            electUsage += device.getElectUsage();
        }
        return electUsage;
    }

    public void toggleActive() {
        this.isActive = (!isActive);
    }
}
