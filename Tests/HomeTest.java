public class HomeTest {
    public static void main(String[] args) {
        Home homeDefault = new Home();
        homeDefault.displayDevices();
        homeDefault.displayRooms();
        Home home = new Home("res/config.txt");
        home.displayDevices();
        home.displayRooms();
        home.start();
    }
}
