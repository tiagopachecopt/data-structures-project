package grupo7;

import Simulation.AutomaticSimulationStrategy;
import Simulation.ManualSimulationStrategy;
import dataStructures.exceptions.EmptyCollectionException;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.entities.Player;
import models.mission.Mission;
import models.mission.MissionVersion;

import java.util.Scanner;

/**
 * Manages the simulation of missions in the game.
 * This class allows the user to start a new game, select missions and versions, and run simulations.
 */
public class SimulationManager {
    private Scanner scanner;

    /**
     * Constructs a SimulationManager with the given scanner for user input.
     *
     * @param scanner the scanner used for reading user input
     */
    public SimulationManager(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * Starts a new game by allowing the user to select a mission and version,
     * and then runs the selected simulation type.
     *
     * @param missionManager the mission manager to handle mission-related operations
     */
    public void startNewGame(MissionManager missionManager) {
        System.out.println("\n--- Starting a New Game ---");

        Player player = selectPlayerByDifficulty();
        if (player == null) {
            System.out.println("Failed to create player. Returning to main menu.");
            return;
        }

        DoubleUnorderedLinkedList<Mission> missions = missionManager.importAllMissions();
        if (missions == null || missions.isEmpty()) {
            System.out.println("No missions were loaded. Returning to main menu.");
            return;
        }

        Mission selectedMission = selectMission(missionManager, missions);
        if (selectedMission == null){
            return;
        }

        MissionVersion selectedVersion = selectVersion(selectedMission);
        if (selectedVersion == null){
            return;
        }

        runSimulation(player, selectedMission, selectedVersion);
    }

    /**
     * Prompts the user for a difficulty, and creates a player given the option. The harder it is the
     * less life and less firepower. We also cap the med kit maximum lower.
     *
     * @return Player returns a player with specified firepower health and max med kits.
     */
    private Player selectPlayerByDifficulty() {
        System.out.println("\nChoose Difficulty Level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium (standard)");
        System.out.println("3. HardCore");

        int difficultyChoice = -1;
        while (difficultyChoice < 1 || difficultyChoice > 3) {
            System.out.print("Please enter your choice (1-3): ");
            if (scanner.hasNextInt()) {
                difficultyChoice = scanner.nextInt();
                scanner.nextLine();

                switch (difficultyChoice) {
                    case 1:
                        System.out.println("Easy mode selected.");
                        return new Player("Tó Cruz", 30, 150, 5);
                    case 2:
                        System.out.println("Medium mode selected.");
                        return new Player("Tó Cruz", 20, 100, 3);
                    case 3:
                        System.out.println("Hardcore mode selected.");
                        return new Player("Tó Cruz", 10, 1, 0);
                    default:
                        System.out.println("Invalid choice. Please select 1, 2, or 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return null;
    }

    /**
     * Allows the user to select a mission from a list of available missions.
     *
     * @param missionManager the mission manager to fetch the mission details
     * @param missions       the list of available missions
     * @return the selected mission, or null if no valid selection was made
     */
    private Mission selectMission(MissionManager missionManager, DoubleUnorderedLinkedList<Mission> missions) {
        System.out.println("\nAvailable Missions:");
        int missionIndex = 1;
        for (Mission mission : missions) {
            System.out.println(missionIndex + ". " + mission.getCode());
            missionIndex++;
        }

        int selectedMissionIndex = -1;
        while (selectedMissionIndex < 1 || selectedMissionIndex > missions.size()) {
            System.out.print("Please select a mission by entering the corresponding number (1-" + missions.size() + "): ");
            if (scanner.hasNextInt()) {
                selectedMissionIndex = scanner.nextInt();
                scanner.nextLine();

                if (selectedMissionIndex < 1 || selectedMissionIndex > missions.size()) {
                    System.out.println("Invalid selection. Please choose a valid mission number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        return missionManager.getMissionAt(missions, selectedMissionIndex - 1);
    }

    /**
     * Allows the user to select a version of the given mission.
     *
     * @param mission the mission whose versions are available for selection
     * @return the selected mission version, or null if no valid selection was made
     */
    private MissionVersion selectVersion(Mission mission) {
        System.out.println("\nAvailable Versions for Mission: " + mission.getCode());
        int versionIndex = 1;
        for (MissionVersion version : mission.getMissionVersions()) {
            System.out.println(versionIndex + ". Version " + version.getVersion());
            versionIndex++;
        }

        int selectedVersionIndex = -1;
        while (selectedVersionIndex < 1 || selectedVersionIndex > mission.getMissionVersions().size()) {
            System.out.print("Please select a version by entering the corresponding number (1-" + mission.getMissionVersions().size() + "): ");
            if (scanner.hasNextInt()) {
                selectedVersionIndex = scanner.nextInt();
                scanner.nextLine();

                if (selectedVersionIndex < 1 || selectedVersionIndex > mission.getMissionVersions().size()) {
                    System.out.println("Invalid selection. Please choose a valid version number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        return mission.getMissionVersions().get(selectedVersionIndex - 1);
    }

    /**
     * Runs the simulation for the given player, mission, and version.
     * The user can choose between a manual or automatic simulation.
     *
     * @param player  the player participating in the simulation
     * @param mission the mission to be simulated
     * @param version the version of the mission to be simulated
     */
    private void runSimulation(Player player, Mission mission, MissionVersion version) {
        int simulationChoice = -1;
        while (simulationChoice != 1 && simulationChoice != 2) {
            System.out.println("\nChoose Simulation Type:");
            System.out.println("1. Manual Simulation");
            System.out.println("2. Automatic Simulation");
            System.out.print("Please enter your choice (1 or 2): ");

            if (scanner.hasNextInt()) {
                simulationChoice = scanner.nextInt();
                scanner.nextLine();

                switch (simulationChoice) {
                    case 1:
                        ManualSimulationStrategy manualSimulation = new ManualSimulationStrategy();
                        try {
                            manualSimulation.runSimulation(player, mission, version);
                        } catch (EmptyCollectionException e) {
                            System.out.println("An error occurred during the simulation: " + e.getMessage());
                        }
                        break;
                    case 2:
                        AutomaticSimulationStrategy autoSimulation = new AutomaticSimulationStrategy();
                        try {
                            autoSimulation.runSimulation(player, mission, version);
                        } catch (EmptyCollectionException e) {
                            System.out.println("An error occurred during the simulation: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1 or 2.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}