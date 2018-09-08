public class FixtureTest {
    public static void main(String[] args) {
        Fixture fixture = new Fixture("Sprinklers");
        fixture.displayStatus();
        System.out.println("\n");
        fixture.setWaterUsage(10);
        fixture.displayStatus();
    }
}
