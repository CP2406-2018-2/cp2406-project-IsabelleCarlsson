public class ApplianceTest {
    public static void main(String[] args) {
        Appliance appliance = new Appliance("Tesla Car");
        appliance.displayStatus();
        appliance.setRoom("Garage");
        appliance.displayStatus();
    }
}
