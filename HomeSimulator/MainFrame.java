import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFrame extends JFrame implements ActionListener {
    JMenuBar menuBar = new JMenuBar();
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
    JLabel status = new JLabel();

    private Timer timer;
    private Home home;
    private JTextArea infoOutput;

    public MainFrame() {
        setTitle("Smart Home Automation Simulator");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        home = new Home();
        setJMenuBar(menuBar);
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
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

        infoOutput = new JTextArea(20, 50);
        infoOutput.setEditable(false);
        infoOutput.setVisible(true);
        add(status, BorderLayout.SOUTH);
        pause.setEnabled(false);
        stop.setEnabled(false);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        status.setText("Program Load Success: " + dtf.format(now));
        add(new JScrollPane(infoOutput), BorderLayout.CENTER);

        // Create a timer object that displays info
        timer = new Timer(1000, evt -> {
            String s = home.getTime();
            if (s != null) {
                infoOutput.append(s + String.format("\nTemp: %.2f°C\nLight: %.2f%%\n", home.getTemperature(),
                        home.getSunlight()));
                infoOutput.setCaretPosition(infoOutput.getDocument().getLength());
            }

            if (home.isDone()) {
                run.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(false);
                timer.stop();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == exit) {
            System.exit(0);
        } else if (source == load) {
            home.loadConfig();
        } else if (source == run) {
            run.setEnabled(false);
            pause.setEnabled(true);
            stop.setEnabled(true);
            home.run();
            timer.start();
        } else if (source == pause) {
            run.setEnabled(true);
            pause.setEnabled(false);
            home.pause();
            timer.stop();
        } else if (source == stop) {
            run.setEnabled(true);
            pause.setEnabled(false);
            stop.setEnabled(false);
            home.stop();
            timer.stop();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new MainFrame();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();
    }
}
