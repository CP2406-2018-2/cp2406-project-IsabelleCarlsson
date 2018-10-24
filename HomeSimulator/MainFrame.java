import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.*;

public class MainFrame extends JFrame implements ActionListener {
    JMenuBar menuBar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenu menu2 = new JMenu("Simulation");
    JMenu menu3 = new JMenu("Help");
    JMenuItem load = new JMenuItem("Load Configuration");
    JMenuItem exit = new JMenuItem("Exit Program");
    JMenuItem info = new JMenuItem("Hide Information");
    JMenuItem run = new JMenuItem("Run");
    JMenuItem pause = new JMenuItem("Pause");
    JMenuItem stop = new JMenuItem("Stop");
    JMenuItem about = new JMenuItem("About");
    JMenuItem guide = new JMenuItem("User Guide");

    JPanel mainDisplay = new JPanel();
    JPanel infoPane = new JPanel();
    JPanel graphicsPane = new JPanel();
    JPanel simView;

    JLabel status = new JLabel();

    private Timer timer;
    private Home home;
    private JTextArea infoOutput;

    public MainFrame() {
        setTitle("Smart Home Automation Simulator");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLocationRelativeTo(null);

        setVisible(true);
        setLayout(new BorderLayout());

        home = new Home();

        Border eBorder = BorderFactory.createEtchedBorder();

        // gridBagConstraints
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = gridBagConstraints.gridy = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = gridBagConstraints.gridheight = 1;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = gridBagConstraints.weighty = 100;

        // infoOutput
        infoOutput = new JTextArea(10, 29);
        infoOutput.setFont(new Font("Calibri", Font.PLAIN, 24));
        infoOutput.setEditable(false);
        infoOutput.setVisible(true);

        // infoPane
        infoPane.setBorder(BorderFactory.createTitledBorder(eBorder, "Information"));
        infoPane.add(new JScrollPane(infoOutput));

        // simView
        simView = new SimView(home);
        simView.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        // graphicsPane
        graphicsPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        graphicsPane.setBorder(BorderFactory.createTitledBorder(eBorder, ""));
        graphicsPane.add(simView);

        // mainDisplay
        mainDisplay.setLayout(new GridBagLayout());
        mainDisplay.add(infoPane, gridBagConstraints);
        mainDisplay.add(graphicsPane, gridBagConstraints);
        mainDisplay.add(infoPane);

        add(mainDisplay);
        add(status, BorderLayout.SOUTH);

        // Add actionListeners to menu items
        load.addActionListener(this);
        exit.addActionListener(this);
        info.addActionListener(this);
        run.addActionListener(this);
        pause.addActionListener(this);
        stop.addActionListener(this);
        about.addActionListener(this);
        guide.addActionListener(this);

        // Add menu items
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

        // Disable pause and stop on initialisation
        pause.setEnabled(false);
        stop.setEnabled(false);

        // Test status label
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        status.setText("Program Load Success: " + dtf.format(now));

        // Create a timer object for infoOutput
        timer = new Timer(1000, evt -> {
            infoOutput.setText(String.format("\n%s\nTemp: %.2f°C\nLight: %.2f%%", home.getTime(),
                    home.getTemperature(), home.getSunlight()));

            if (home.isDone()) {
                run.setEnabled(true);
                pause.setEnabled(false);
                stop.setEnabled(false);
                timer.stop();
            }
        });
        pack();
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
        } else if (source == info) {
            if (infoPane.isVisible()) {
                infoPane.setVisible(false);
                info.setText("Show Information");
            } else {
                infoPane.setVisible(true);
                info.setText("Hide Information");
            }
        } else if (source == about) {
            JOptionPane.showMessageDialog(this, "Isabelle Carlsson\n© James Cook University" +
                    "\nVersion: 2.07 ");
        } else if (source == guide) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/CP2406-2018-2/cp2406-project-" +
                        "IsabelleCarlsson/blob/master/README.md").toURI());
            } catch (Exception exception) {
                status.setText(String.format("Error: %s", exception));
            }
        }
        status.setText(home.getErrorMessage());
        repaint();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
