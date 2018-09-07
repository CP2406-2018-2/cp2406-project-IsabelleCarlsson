public class Appliance extends Device {
    public Appliance(String name) {
        super(name);
    }

    public void setRoom(String newRoom){
        super.room = newRoom;
    }
}
