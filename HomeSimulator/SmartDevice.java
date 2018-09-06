public class SmartDevice {
    private String deviceName;
    private int electUsage;
    private boolean isActive;
    private String roomName;

    public SmartDevice() {
        deviceName = "device";
        electUsage = 1;
        isActive = false;
        roomName = "";
    }

    public SmartDevice(String deviceName, String roomName, int electUsage, boolean isActive) {
        this.deviceName = deviceName;
        this.electUsage = electUsage; // in watts
        this.isActive = isActive;
        this.roomName = roomName;
    }

    public String getName() {
        return deviceName;
    }

    public int getElectUsage() {
        return electUsage;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setElectUsage(int electUsage) {
        this.electUsage = electUsage;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void toggleActive() {
        if (isActive) {
            this.isActive = false;
        } else {
            this.isActive = true;
        }
    }

    public void displayStatus() {
        System.out.println("Device: " + deviceName + "\nRoom: " + roomName + "\nUsage: " + electUsage + "\nActive: " + isActive + "\n");
    }
}
