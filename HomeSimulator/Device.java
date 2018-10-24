import java.awt.*;
import java.util.Random;

public class Device {
    private String name;
    private int electUsage;
    private boolean isActive;
    private String roomName;
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
    private boolean isFixture;
    private int size;
    private int red = random.nextInt(256);
    private int green = random.nextInt(256);
    private int blue = random.nextInt(256);
    private Color color = new Color(red, green, blue);
    private static Random random = new Random();

    public Device(String name, String roomName, boolean isFixture, int electUsage) {
        this.name = name;
        this.electUsage = electUsage;
        isActive = false;
        this.roomName = roomName;
        timeControlled = false;
        tempControlled = false;
        lightControlled = false;
        motionControlled = false;
        motionSensor = null;
        onCondition = null;
        offCondition = null;
        this.isFixture = isFixture;
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

    public void update(String time, double temperature, double sunlight) {
        // Updates environmental variables & triggering conditions
        setTime(time);
        setTemperature(temperature);
        setSunlight(sunlight);
        checkConditions();
    }

    private void setTime(String time) {
        this.time = time;
    }

    private void checkConditions() {
        if (timeControlled) {
            if (time.equals(onCondition)) {
                isActive = true;
            } else if (time.equals(offCondition)) {
                isActive = false;
            }
        } else if (tempControlled) {
            if (temperature == Double.parseDouble(onCondition)) {
                isActive = true;
            } else if (temperature == Double.parseDouble(offCondition)) {
                isActive = false;
            }
        } else if (lightControlled) {
            if (temperature == Double.parseDouble(onCondition)) {
                isActive = true;
            } else if (temperature == Double.parseDouble(offCondition)) {
                isActive = false;
            }
        }
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

    public boolean isActive() {
        return isActive;
    }

    public void toggleActive() {
        this.isActive = (!isActive);
    }

    public void displayStatus() {
        System.out.println("Device: " + name + "\nRoom: " + roomName + "\nFixture: " + isFixture + "\nUsage: " + electUsage
                + "\nActive: " + isActive + "\n");
    }

    public boolean isFixture() {
        return isFixture;
    }

    public void toggleFixture() {
        if (isFixture)
            isFixture = !isFixture;
        else
            isFixture = !isFixture;

    }
}
