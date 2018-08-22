import java.util.Scanner;

public class HomeSimulatorTest {
    private static void startMenu(){
        boolean quitMenu = false;
        Scanner menuInput = new Scanner(System.in);
        do {
            System.out.print("(1) Start Simulator\n(2) Configuration\n(3) Quit\n\nPlease, select your menu option: ");
            char userChoice = menuInput.next().charAt(0);
            if(userChoice == Character.toLowerCase('1')) {
                System.out.println("Starting simulator...\n");
            } else if(userChoice == Character.toLowerCase('2')) {
                System.out.println("Loading configurations...\n");
            } else if(userChoice == Character.toLowerCase('3')) {
                System.out.println("Quitting HomeSimulator...");
                quitMenu = true;
            } else {
                System.out.println("Invalid user choice");
            }
        } while(!quitMenu);
    }


    public static void main(String[] args) {
        System.out.println("Welcome to the Smart Home Automation Simulator!");
        startMenu();
    }
}
