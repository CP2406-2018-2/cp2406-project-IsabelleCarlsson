public class SmartDevice {
    private String deviceName;
    private int electUsage;
    private boolean isActive;

    public SmartDevice() {
        deviceName = "device";
        electUsage = 1;
        isActive = false;
    }

    public SmartDevice(String deviceName, int electUsage, boolean isActive) {
        this.deviceName = deviceName;
        this.electUsage = electUsage;
        this.isActive = isActive;
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
        System.out.println("Device: " + deviceName + "\nUsage: " + electUsage + "\nActive: " + isActive);
    }
}
