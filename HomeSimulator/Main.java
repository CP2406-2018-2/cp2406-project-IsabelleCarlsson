import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int state;
        HomeSimulator home = new HomeSimulator("TestHouse", "res/config.txt.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Smart Home Automation Simulator!");
        do {
            System.out.println("[1] Start Simulator");
            System.out.println("[2] Configuration");
            System.out.println("[3] Quit");
            System.out.print("\nPlease, select your menu option: ");
            state = scanner.nextInt();

            switch (state) {
                case 1:
                    home.start();
                case 2:
                    configMenu();
                case 3:
                    break;
                default:
                    System.out.println("The selection was invalid!");
            }
        } while (state != 3);
    }

    private static void configMenu() {
        int state;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("[1] Configure Rooms");
            System.out.println("[2] Configure Smart Devices");
            System.out.println("[3] Exit");

            System.out.print("\nPlease, select your menu option: ");
            state = scanner.nextInt();

            switch (state) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("The selection was invalid!");
            }
        } while (state != 3);
    }
}
