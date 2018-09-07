import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomName;
    private boolean isActive;
    private String time;
    private List<Device> deviceList = new ArrayList<>();

    public Room() {
        roomName = "room";
        isActive = false;
    }

    public Room(String roomName) {
        this.roomName = roomName;
        isActive = false;
    }

    public void displayDevices(){
        for (Device device: deviceList) {
            device.displayStatus();
        }
    }

    public void updateDevices(String time, double temperature, double sunlight){
        for (Device device : deviceList) {
            device.update(time, temperature, sunlight);
        }
    }

    public String getName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void displayStatus() {
        System.out.println("Room: " + roomName + "\nUsage: " + getElectUsage() + "\nActive: " + isActive + "\n");
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
