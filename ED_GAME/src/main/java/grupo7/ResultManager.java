package grupo7;

import dataStructures.lists.DoubleOrderedLinkedList;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.mission.Mission;
import models.mission.SimulationResult;
import utils.jsonHandlers.SimulationResultManager;

import java.util.Scanner;

/**
 * Manages the display and handling of simulation results for missions.
 * This class allows users to view top scores for a selected mission by reading simulation results
 * from a file and displaying them in an ordered manner.
 */
public class ResultManager {
    private Scanner scanner;
    private MissionManager missionManager;

    /**
     * Constructs a ResultManager with the given scanner and mission manager.
     *
     * @param scanner       the scanner used for user input
     * @param missionManager the mission manager to handle mission-related operations
     */
    public ResultManager(Scanner scanner, MissionManager missionManager) {
        this.scanner = scanner;
        this.missionManager = missionManager;
    }

    /**
     * Displays the top scores for a selected mission.
     * Only manual simulation results are displayed, ordered by player health.
     */
    public void viewTopScores() {
        System.out.println("\n--- View Top Scores ---");

        DoubleUnorderedLinkedList<Mission> missions = missionManager.importAllMissions();
        if (missions == null || missions.isEmpty()) {
            System.out.println("No missions were loaded. Returning to main menu.");
            return;
        }

        Mission selectedMission = selectMission(missions);
        if (selectedMission == null) return;

        System.out.println("\nSimulation Results for Mission: " + selectedMission.getCode());
        DoubleOrderedLinkedList<SimulationResult> results = SimulationResultManager.loadResults(
                selectedMission.getCode(), 0
        );

        if (results.isEmpty()) {
            System.out.println("No simulation results found for this mission.");
            return;
        }

        displayResults(results);
    }

    /**
     * Allows the user to select a mission from the list of imported missions.
     *
     * @param missions the list of missions available for selection
     * @return the selected mission, or null if no valid selection was made
     */
    private Mission selectMission(DoubleUnorderedLinkedList<Mission> missions) {
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
     * Displays the simulation results in a formatted manner.
     *
     * @param results the list of simulation results to display
     */
    private void displayResults(DoubleOrderedLinkedList<SimulationResult> results) {
        System.out.println("Manual Simulation Results (Ordered by Remaining Health):");

        int resultIndex = 1;
        for (SimulationResult result : results) {
            System.out.printf("%d. Version: %d | Player: %s | Health: %d | Path Taken: %s%n",
                    resultIndex, result.getMissionVersion(), result.getPlayerName(),
                    result.getPlayerHealth(), result.getPathAsString());
            resultIndex++;
        }
    }
}