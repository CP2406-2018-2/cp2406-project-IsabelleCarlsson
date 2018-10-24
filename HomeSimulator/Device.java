public class Device {
    private String name;
    private int electUsage;
    private boolean isActive;
    protected String room;
    private String time;
    private double temperature;
    private double sunlight;
    private boolean timeControlled;
    private boolean tempControlled;
    private boolean lightControlled;
    private boolean motionControlled;
    private String motionSensor;
    private String onCondition;
    private String offCondition;

    public Device() {
        name = "device";
        electUsage = 1;
        isActive = false;
        room = "";
        timeControlled = false;
        tempControlled = false;
        lightControlled = false;
        motionControlled = false;
        motionSensor = null;
        onCondition = null;
        offCondition = null;
    }

    public void setTimeControlled(boolean timeControlled) {
        this.timeControlled = timeControlled;
    }

    public void setTempControlled(boolean tempControlled) {
        this.tempControlled = tempControlled;
    }

    public void setLightControlled(boolean lightControlled) {
        this.lightControlled = lightControlled;
    }

    public void setMotionControlled(boolean motionControlled) {
        this.motionControlled = motionControlled;
    }

    public void setMotionSensor(String motionSensor) {
        this.motionSensor = motionSensor;
    }

    public void setOnCondition(String onCondition) {
        this.onCondition = onCondition;
    }

    public void setOffCondition(String offCondition) {
        this.offCondition = offCondition;
    }

    public Device(String name) {
        this.name = name;
        electUsage = 1;
        isActive = false;
        room = "";
    }

    public void updateEnvironVars(String time, double temperature, double sunlight) {
        // Updates environmental variables
        setTime(time);
        setTemperature(temperature);
        setSunlight(sunlight);
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

    public String getName() {
        return name;
    }

    public String getRoomName() {
        return room;
    }

    public void setRoomName(String room) {
        this.room = room;
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

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
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
        System.out.println("Device: " + name + "\nRoom: " + room + "\nUsage: " + electUsage + "\nActive: " + isActive +
                "\n");
    }
}
