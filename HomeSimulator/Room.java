import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private boolean isActive;
    private String time;
    private double temperature;
    private double sunlight;
    private List<Device> deviceList = new ArrayList<>();

    public Room() {
        name = "room";
        isActive = false;
    }

    public Room(String name) {
        this.name = name;
        isActive = false;
    }

    public void displayDevices(){
        for (Device device: deviceList) {
            device.displayStatus();
        }
    }

    public void updateEnvironVars(String time, double temperature, double sunlight) {
        // Updates environmental variables
        setTime(time);
        setTemperature(temperature);
        setSunlight(sunlight);
    }

    public void updateDevices(String time, double temperature, double sunlight){
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void displayStatus() {
        System.out.println("Room: " + name + "\nUsage: " + getElectUsage() + "\nActive: " + isActive + "\n");
    }

    public Device getDeviceByName(String deviceName) {
        for (Device device : deviceList) {
            if (device.getName().toLowerCase().equals(deviceName.toLowerCase()))
                return device;
        }
        return null;
    }

    public void addDevice(Device device) {
        deviceList.add(device);
    }

    public double getElectUsage() {
        double roomElectUsage = 0;
        for (Device device : deviceList) {
            roomElectUsage += device.getElectUsage();
        }
        return roomElectUsage;
    }

    public void toggleActive() {
        this.isActive = (!isActive);
    }
}
