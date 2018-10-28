import javax.swing.*;
import java.awt.*;

public class SimView extends JPanel {
    private Home home;

    public SimView(Home home) {
        super();
        this.home = home;
        setPreferredSize(new Dimension(552, 400));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        int roomX = 0;
        int roomY = 0;
        int deviceX = 0;
        int deviceY = 0;
        String deviceName;
        int deviceNameLength;

        // Draw background
        graphics.setColor(new Color(0,100,0));
        graphics.fillRect(0, 0, getWidth(), getHeight());

        // Draw rooms
        for (Room room : home.getRoomList()) {
            if (getWidth() <= roomX + room.getSize()) {
                roomY += room.getSize();
                roomX = 0;
            }
            graphics.setColor(room.getColor());
            graphics.fillRect(roomX, roomY, room.getSize(), room.getSize());

            // Draw devices
            deviceX = roomX;
            deviceY = roomY + 30;
            for (Device device : room.getDeviceList()) {
                if (roomX+room.getSize() <= deviceX) {
                    deviceY += roomY + device.getSize();
                    deviceX = roomX;
                }
                graphics.setColor(device.getColor());
                graphics.fillRect(deviceX, deviceY, device.getSize(), device.getSize());
                graphics.setFont(new Font("Arial", Font.PLAIN, 9));
                graphics.setColor(Color.WHITE);
                deviceName = device.getName().replaceAll("\\s+","");
                deviceNameLength = deviceName.length();
                if (deviceNameLength > 7) {
                    graphics.drawString(deviceName.substring(0,7), deviceX + 2, deviceY + 10);
                    graphics.drawString(deviceName.substring(7, deviceNameLength), deviceX + 3
                            , deviceY + 20);
                } else {
                    graphics.drawString(device.getName(), deviceX + 1, deviceY + 10);
                }
                deviceX += device.getSize();
            }

            // Label rooms
            graphics.setFont(new Font("Arial Black", Font.BOLD, 18));
            graphics.setColor(Color.WHITE);
            graphics.drawString(room.getName(), roomX + 10, roomY + 20);
            roomX += room.getSize();
        }
    }
}