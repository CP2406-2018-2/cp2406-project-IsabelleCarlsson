import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private Timer timer;
    JMenuBar mainBar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenu menu2 = new JMenu("Simulation");
    JMenu menu3 = new JMenu("Help");
    JMenuItem load = new JMenuItem("Load Configuration");
    JMenuItem exit = new JMenuItem("Exit Program");
    JMenuItem info = new JMenuItem("Show/Hide Information");
    JMenuItem run = new JMenuItem("Run");
    JMenuItem pause = new JMenuItem("Pause");
    JMenuItem stop = new JMenuItem("Stop");
    JMenuItem about = new JMenuItem("About");
    JMenuItem guide = new JMenuItem("User Guide");
    JLabel main = new JLabel();
    JLabel status = new JLabel();
    Home home = new Home("res/config.txt");

    public MainFrame() {
        setTitle("Smart Home Automation Simulator");
        timer = new Timer(10, this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(mainBar);
        mainBar.add(menu1);
        mainBar.add(menu2);
        mainBar.add(menu3);
        menu1.add(load);
        menu1.add(exit);
        menu2.add(info);
        menu2.add(run);
        menu2.add(pause);
        menu2.add(stop);
        menu3.add(about);
        menu3.add(guide);
        load.addActionListener(this);
        exit.addActionListener(this);
        info.addActionListener(this);
        run.addActionListener(this);
        pause.addActionListener(this);
        stop.addActionListener(this);
        about.addActionListener(this);
        guide.addActionListener(this);
        add(main);
        main.setFont(new Font("Arial", Font.ITALIC, 16));
        add(status, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String text = "";
        if (source == exit)
            System.exit(0);
        else if (source == info)
            System.out.println((String.format("Time: %s, Temp: %.2f °C, SunL: %.2f %%", home.getTime(),
                    home.getTemperature(), home.getSunlight())));
        else if (source == load) {
                home.loadConfig();
        } else if (source == run) {
            timer.start();
            home.start();
        }
        else if (source == pause) {
            timer.stop();
        } else if (source == stop)
            text = "stop";
        else if (source == about)
            text = "about";
        else if (source == guide)
            text = "guide";
        status.setText(text);
        repaint();
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

