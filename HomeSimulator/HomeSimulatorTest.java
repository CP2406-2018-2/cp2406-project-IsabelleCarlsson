public class HomeSimulatorTest {

    public static void main(String[] args) {
        System.out.println("Welcome to the Smart Home Automation Simulator!");
        appMenu appMenu = new appMenu();
        appMenu.mainMenu(appMenu);
        System.out.println("Application has been shut down");
    }
}
