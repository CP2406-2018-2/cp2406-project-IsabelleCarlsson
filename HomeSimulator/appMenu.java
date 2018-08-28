import java.util.Scanner;

public class appMenu {

    public appMenu mainMenu(appMenu appMenu) {
        int state;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("[1] Start Simulator");
            System.out.println("[2] Configuration");
            System.out.println("[3] Quit");

            System.out.print("\nPlease, select your menu option: ");
            state = scanner.nextInt();

            switch (state) {
                case 1:
                    return appMenu.SimMenu(appMenu);
                case 2:
                    return appMenu.configMenu(appMenu);
                case 3:
                    break;
                default:
                    System.out.println("The selection was invalid!");
            }
        } while (state != 3);
        return appMenu;
    }

    public appMenu SimMenu(appMenu appMenu) {
        String input;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nStarting simulator...");
        HomeSimulator home = new HomeSimulator("TestHouse", "C:\\Users\\Isabelle\\Desktop\\SP2\\CP2406 Programming III\\Assignment 1\\HomeSimulator\\file");
        home.startSimulator();
        do {
            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "stop":
                    home.stopSimulator();
                    break;
                case "time":
                    home.displayTime();
                    break;
                case "devices":
                    home.displayDevices();
                    break;
                case "rooms":
                    home.displayRooms();
                    break;
                default:
                    System.out.println("Invalid input");
            }
        } while (!input.equals("stop"));

        return appMenu.mainMenu(appMenu);
    }

    private appMenu configMenu(appMenu appMenu) {
        return appMenu;
    }
}
