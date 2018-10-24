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
            int width = getWidth();
            if (getWidth() <= x+room.size) {
                y += room.size;
                x = 0;
            }
            graphics.setColor(room.getColor());
            graphics.fillRect(x, y, room.size, room.size);
            x += room.size;
        }
    }
}