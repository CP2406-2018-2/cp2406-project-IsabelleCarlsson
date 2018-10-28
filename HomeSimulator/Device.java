import java.awt.*;
import java.util.Random;

public class Device {
    private String name;
    private int electUsage;
    private boolean isOn;
    private String roomName;
    private String time;
    private double temperature;
    private double sunlight;
    private boolean timeControlled;
    private boolean tempControlled;
    private boolean isLight;
    private boolean isMotionSensor;
    private boolean motionControlled;
    private String onCondition;
    private String offCondition;
    private boolean isFixture;
    private int size;
    private int red = random.nextInt(100);
    private int green = random.nextInt(100);
    private int blue = random.nextInt(256);
    private Color color = new Color(red, green, blue);
    private static Random random = new Random();

    public Device(String name, String roomName, boolean isFixture, int electUsage) {
        this.name = name;
        this.roomName = roomName;
        this.electUsage = electUsage;
        this.isFixture = isFixture;
        isOn = false;
        isLight = false;
        isMotionSensor = false;
        timeControlled = false;
        tempControlled = false;
        motionControlled = false;
        onCondition = null;
        offCondition = null;
        size = 40;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void setTimeControlled(boolean timeControlled) {
        this.timeControlled = timeControlled;
    }

    public void setTempControlled(boolean tempControlled) {
        this.tempControlled = tempControlled;
    }

    public void setMotionControlled(boolean motionControlled) {
        this.motionControlled = motionControlled;
    }

    public void isLight(boolean isLight) {
        this.isLight = isLight;
    }

    public boolean isLight() {
        return this.isLight;
    }

    public void isMotionSensor(boolean isMotionSensor) {
        this.isMotionSensor = isMotionSensor;
    }

    public void setOnCondition(String onCondition) {
        this.onCondition = onCondition;
    }

    public void setOffCondition(String offCondition) {
        this.offCondition = offCondition;
    }

    public void update(String time, double temperature, double sunlight) {
        // Updates environmental variables & checks triggering conditions
        setTime(time);
        setTemperature(temperature);
        setSunlight(sunlight);
    }

    public boolean isTempControlled() {
        return tempControlled;
    }

    public boolean isTimeControlled() {
        return timeControlled;
    }

    public boolean isMotionControlled() {
        return motionControlled;
    }

    public String getOnCondition() {
        return onCondition;
    }

    public String getOffCondition() {
        return offCondition;
    }

    public void setColor(Color color) {
        this.color = color;
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
        return roomName;
    }

    public void setRoomName(String room) {
        this.roomName = room;
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

    public boolean isOn() {
        return isOn;
    }


    public void setOn(boolean isActive) {
        this.isOn = isActive;
    }

    public void displayStatus() {
        System.out.println("Device: " + name + "\nRoom: " + roomName + "\nFixture: " + isFixture + "\nUsage: " + electUsage
                + "\nActive: " + isOn + "\n");
    }

    public boolean isFixture() {
        return isFixture;
    }

}
