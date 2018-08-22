public class SimRoom {
    private String roomName;
    private boolean isActive;
    private SmartDevice[] devices;

    public SimRoom() {
        roomName = "room";
        isActive = false;
    }

    public SimRoom(String roomName) {
        this.roomName = roomName;
        this.isActive = false;
    }

    public void toggleActive() {
        if (isActive) {
            this.isActive = false;
        } else {
            this.isActive = true;
        }
    }

    public void addDevice() {
        // add devices to room
    }

    public void displayStatus() {
        System.out.println("Room: " + roomName + "\nUsage: " + "electUsage" + "\nActive: " + isActive);
    }
}
