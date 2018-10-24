public class DeviceTest {
    public static void main(String[] args) {
        Device device = new Device("Coffee Machine", "Kitchen", true, 100);
        System.out.println("Device: " + device.getName());
        device.setName("Microwave");
        System.out.println("Device: " + device.getName());
        device.setElectUsage(1000);
        System.out.println("Electric usage: " + device.getElectUsage());
        device.setRoomName("Kitchen");
        System.out.println("Room: " + device.getRoomName());
        device.displayStatus();
        device.toggleActive();
        device.displayStatus();
    }
}
