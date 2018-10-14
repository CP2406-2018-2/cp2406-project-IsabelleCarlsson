public class RoomTest {
    public static void main(String[] args) {
        Home home = new Home();
        home.loadConfig();

        for (Room room : home.getRoomList()) {
            room.displayStatus();
        }
    }
}
