import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String state;
        Home home = new Home("res/config.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Smart Home Automation Simulator!");
        do {
            System.out.println("\n[1] Run Simulator");
            System.out.println("[2] List House Contents");
            System.out.println("[3] Quit");
            System.out.print("\nPlease, select your menu option: ");
            state = scanner.nextLine();

            switch (state) {
                case "1":
                    home.start();
                    break;
                case "2":
                    home.displayDevices();
                    break;
                case "3":
                    System.out.println("Quitting Application...");
                    break;
                default:
                    System.out.println("The selection was invalid!");
                    break;
            }
        } while (!(state.equals("3")));
    }
}
