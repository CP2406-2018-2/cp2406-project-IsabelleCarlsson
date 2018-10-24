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
            deviceY = roomY;
            for (Device device : room.getDeviceList()) {
                if (room.getSize() <= deviceX+device.getSize()) {
                    deviceY += device.getSize();
                    deviceX = 0;
                }
                graphics.setColor(device.getColor());
                graphics.fillRect(deviceX + 2, deviceY + 2, device.getSize(), device.getSize());
                deviceX += device.getSize();
            }

            // Label rooms
            graphics.setFont(new Font("Calibri", Font.PLAIN, 18));
            graphics.setColor(Color.WHITE);
            graphics.drawString(room.getName(), roomX + 10, roomY + 20);
            roomX += room.getSize();
        }
    }
}