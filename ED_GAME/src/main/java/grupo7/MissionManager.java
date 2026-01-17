package grupo7;

import dataStructures.lists.DoubleUnorderedLinkedList;
import models.mission.Mission;
import models.mission.MissionVersion;
import utils.jsonHandlers.MissionImporter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Manages the import and retrieval of mission data.
 *
 * This class handles the process of loading mission files from a specified directory,
 * parsing mission data, and providing access to individual missions. It is responsible
 * for organizing and filtering mission files and ensuring they are correctly processed.
 */
public class MissionManager {
    private Scanner scanner;

    public MissionManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Imports all missions from the IMF folder.
     */
    public DoubleUnorderedLinkedList<Mission> importAllMissions() {
        String userHome = System.getProperty("user.home");
        Path imfPath = Paths.get(userHome, "Documents", "IMF");

        if (!Files.exists(imfPath) || !Files.isDirectory(imfPath)) {
            System.out.println("The IMF directory does not exist in your Documents folder.");
            return null;
        }

        return importAllMissionsFromFolder(imfPath.toString());
    }

    /**
     * Imports all missions from a specified folder path.
     */
    private DoubleUnorderedLinkedList<Mission> importAllMissionsFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] jsonFiles = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".json") && name.matches(".+_v\\d+\\.json")
        );

        if (jsonFiles == null || jsonFiles.length == 0) {
            System.out.println("No mission JSON files found in the specified folder.");
            return null;
        }

        DoubleUnorderedLinkedList<String> uniqueMissionNames = new DoubleUnorderedLinkedList<>();
        DoubleUnorderedLinkedList<Mission> missions = new DoubleUnorderedLinkedList<>();
        Pattern pattern = Pattern.compile("^(.+)_v(\\d+)\\.json$", Pattern.CASE_INSENSITIVE);

        for (File file : jsonFiles) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.matches()) {
                String missionBaseName = matcher.group(1);

                boolean exists = false;
                for (String name : uniqueMissionNames) {
                    if (name.equalsIgnoreCase(missionBaseName)) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    uniqueMissionNames.addToRear(missionBaseName);
                }
            }
        }

        for (String missionBaseName : uniqueMissionNames) {
            Mission mission = new Mission();
            mission.setCode(missionBaseName);

            for (File file : jsonFiles) {
                Matcher matcher = pattern.matcher(file.getName());
                if (matcher.matches() && matcher.group(1).equalsIgnoreCase(missionBaseName)) {
                    Mission importedMission = MissionImporter.importMission(file.getAbsolutePath());
                    if (importedMission != null && !importedMission.getMissionVersions().isEmpty()) {
                        for (MissionVersion version : importedMission.getMissionVersions()) {
                            mission.addMissionVersion(version);
                        }
                    }
                }
            }

            missions.addToRear(mission);
        }

        return missions;
    }

    /**
     * Retrieves a mission at the specified index.
     */
    public Mission getMissionAt(DoubleUnorderedLinkedList<Mission> missions, int index) {
        int currentIndex = 0;
        for (Mission mission : missions) {
            if (currentIndex == index) {
                return mission;
            }
            currentIndex++;
        }
        return null;
    }
}