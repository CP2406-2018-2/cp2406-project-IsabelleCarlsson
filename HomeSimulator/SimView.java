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
        int x = 0;
        int y = 0;
        for (Room room : home.getRoomList()) {
            graphics.setColor(room.getColor());
            graphics.fillRect(x, y, room.size, room.size);
            x += room.size;
            if (getWidth() <= x) {
                y += room.size;
                x = 0;
            }
        }
    }
}