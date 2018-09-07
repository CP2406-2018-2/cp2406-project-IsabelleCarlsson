public class Fixture extends Device {
    private int waterUsage;

    public Fixture(String name) {
        super(name);
        this.waterUsage = 1;
    }

    public void setWaterUsage(int waterUsage) {
        this.waterUsage = waterUsage;
    }

    public int getWaterUsage() {
        return waterUsage;
    }
}
