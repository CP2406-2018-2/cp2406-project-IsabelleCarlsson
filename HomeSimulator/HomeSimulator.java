public class HomeSimulator {
    private String houseName;

    private HomeSimulator() {
        houseName = "DefaultHouse";
    }

    public HomeSimulator(String houseName) {
        this.houseName = houseName;
    }
}
