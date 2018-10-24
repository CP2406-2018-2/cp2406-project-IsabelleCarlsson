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
    private int size;
    private int red = random.nextInt(256);
    private int green = random.nextInt(256);
    private int blue = random.nextInt(256);
    private Color color = new Color(red, green, blue);
    private static Random random = new Random();

    public Room(String name) {
        this.name = name;
        isActive = false;
        size = 160;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void displayDevices() {
        for (Device device : deviceList) {
            device.displayStatus();
        }
    }

    public void updateRoom(String time, double temperature, double sunlight) {
        // Updates environmental variables and electricity usage
        setTime(time);
        setTemperature(temperature);
        setSunlight(sunlight);
        calcElectUsage();
    }

    public void updateDevices(String time, double temperature, double sunlight) {
        for (Device device : deviceList) {
            device.update(time, temperature, sunlight);
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

    public void calcElectUsage() {
        electUsage = 0;
        for (Device device : deviceList) {
            if (device.isActive()) {
                electUsage += device.getElectUsage();
            }
        }
    }

    public double getElectUsage() {
        return electUsage;
    }

    public void toggleActive() {
        this.isActive = (!isActive);
    }
}
