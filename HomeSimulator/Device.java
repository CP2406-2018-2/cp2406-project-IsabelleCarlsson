public class Device {
    private String deviceName;
    private int electUsage;
    private boolean isActive;
    private String time;
    private double temperature;
    private double sunlight;

    public Device() {
        deviceName = "device";
        electUsage = 1;
        isActive = false;
    }

    public Device(String deviceName) {
        this.deviceName = deviceName;
        electUsage = 1;
        isActive = false;
    }

    public void update(String time, double temperature, double sunlight) {
        this.time = time;
        this.temperature = temperature;
        this.sunlight = sunlight;
    }

    public String getName() {
        return deviceName;
    }

    public int getElectUsage() {
        return electUsage;
    }

    public double getSunlight() {
        return sunlight;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getTime() {
        return time;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setElectUsage(int electUsage) {
        this.electUsage = electUsage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void toggleActive() {
        this.isActive = (!isActive);

    }

    public void displayStatus() {
        System.out.println("Device: " + deviceName + "\nUsage: " + electUsage + "\nActive: " + isActive + "\n");
    }
}
