public class RoomTest {
    public static void main(String[] args) {
        Room room = new Room("Bedroom");
        System.out.println("Electric Usage: " + room.getElectUsage());
        Device device = new Device("Lamp");
        room.addDevice(device);
        room.displayDevices();
        room.setName("Living Room");
        room.displayStatus();
        room.toggleActive();
        room.displayStatus();
    }
}
