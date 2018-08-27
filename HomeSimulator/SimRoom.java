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

    public String getRoomName(){
        return roomName;
    }

    public boolean isActive(){
        return isActive;
    }

    public List<SmartDevice> getDeviceList(){
        return deviceList;
    }

    public void displayStatus() {
        System.out.println("Room: " + roomName + "\nUsage: " + getElectUsage() + "\nActive: " + isActive);
    }

    public void addDevice(SmartDevice device) {
        deviceList.add(device);
    }

    public double getElectUsage(){
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
