import java.util.ArrayList;
import java.util.List;

public class SimRoom {
    private String roomName;
    private boolean isActive;
    private List<SmartDevice> deviceList = new ArrayList<>();

    public SimRoom() {
        roomName = "room";
        isActive = false;
    }

    public SimRoom(String roomName) {
        this.roomName = roomName;
        this.isActive = false;
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

    public SmartDevice getDeviceByName(String deviceName) {
        for (SmartDevice device : deviceList) {
            if (device.getName().toLowerCase().equals(deviceName.toLowerCase()))
                return device;
        }
        return null;
    }

    public void addDevice(SmartDevice device) {
        deviceList.add(device);
    }

    public double getElectUsage() {
        double roomElectUsage = 0;
        for (SmartDevice device : deviceList) {
            roomElectUsage += device.getElectUsage();
        }
        return roomElectUsage;
    }

    public void toggleActive() {
        if (isActive) {
            this.isActive = false;
        } else {
            this.isActive = true;
        }
    }
}
