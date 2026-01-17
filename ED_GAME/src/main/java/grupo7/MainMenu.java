package grupo7;

import java.util.Scanner;

/**
 * The entry point of the Mission Simulation Game.
 *
 * This class provides a console-based main menu for the game, allowing users to:
 * Start a new game
 * View top scores
 * Read game instructions
 * Exit the game
 */
public class MainMenu {
    private Scanner scanner;
    private MissionManager missionManager;
    private SimulationManager simulationManager;
    private ResultManager resultManager;

    public MainMenu() {
        scanner = new Scanner(System.in);
        missionManager = new MissionManager(scanner);
        simulationManager = new SimulationManager(scanner);
        resultManager = new ResultManager(scanner, missionManager);
    }

    /**
     * Displays the main menu and handles user input.
     */
    public void displayMenu() {
        int choice = -1;
        while (choice != 4) {
            System.out.println("\n=== Welcome to the Mission Simulation Game ===");
            System.out.println("1. Start New Game");
            System.out.println("2. View Top Scores");
            System.out.println("3. View Instructions");
            System.out.println("4. Exit");
            System.out.print("Please enter your choice (1-4): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        simulationManager.startNewGame(missionManager);
                        break;
                    case 2:
                        resultManager.viewTopScores();
                        break;
                    case 3:
                        viewInstructions();
                        break;
                    case 4:
                        exitGame();
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Displays game instructions to the user.
     */
    private void viewInstructions() {
        System.out.println("\n--- Game Instructions ---");
        System.out.println("1. Navigate through the mission by choosing rooms.");
        System.out.println("2. Engage in combat with enemies encountered.");
        System.out.println("3. Collect items to enhance your abilities.");
        System.out.println("4. Reach the target room and return to the entry point to complete the mission.");
        System.out.println("5. Manage your health carefully to survive.");
    }

    /**
     * Exits the game gracefully.
     */
    private void exitGame() {
        System.out.println("\nThank you for playing! Goodbye!");
        scanner.close();
        System.exit(0);
    }

    /**
     * Main method to launch the game menu.
     */
    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.displayMenu();
    }
}