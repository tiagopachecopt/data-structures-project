package utils.jsonHandlers;

import dataStructures.lists.DoubleOrderedLinkedList;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.mission.SimulationResult;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Manages the persistence and retrieval of simulation results for missions.
 *
 * This class handles saving simulation results to a JSON file and loading them
 * back into the system. It supports operations for both individual mission versions
 * and all versions of a given mission.
*/
public class SimulationResultManager {

    /**
     * The file where simulation results are stored.
     */
    private static final File RESULTS_FILE = new File(
            new File(new File(System.getProperty("user.home"), "Documents"), "IMF"),
            "simulationResults.json"
    );

    /**
     * Saves a simulation result for a specific mission and version.
     *
     * @param missionCode     the mission code
     * @param missionVersion  the mission version
     * @param result          the simulation result to save
     */
    public static void saveResult(String missionCode, int missionVersion, SimulationResult result) {
        JSONParser parser = new JSONParser();
        JSONArray allResults;

        try (FileReader reader = new FileReader(RESULTS_FILE)) {
            allResults = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            allResults = new JSONArray();
        }

        JSONObject missionEntry = null;
        for (Object obj : allResults) {
            JSONObject entry = (JSONObject) obj;
            if (entry.get("missionCode").equals(missionCode) &&
                    ((Long) entry.get("missionVersion")).intValue() == missionVersion) {
                missionEntry = entry;
                break;
            }
        }

        if (missionEntry == null) {
            missionEntry = new JSONObject();
            missionEntry.put("missionCode", missionCode);
            missionEntry.put("missionVersion", missionVersion);
            missionEntry.put("simulationResults", new JSONArray());
            allResults.add(missionEntry);
        }

        JSONArray simulationResults = (JSONArray) missionEntry.get("simulationResults");

        JSONObject resultJson = new JSONObject();
        resultJson.put("playerName", result.getPlayerName());
        resultJson.put("playerHealth", result.getPlayerHealth());
        resultJson.put("pathTaken", serializePath(result.getPathTaken()));
        simulationResults.add(resultJson);

        try (FileWriter writer = new FileWriter(RESULTS_FILE)) {
            writer.write(allResults.toJSONString());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes a player's path into a JSON array.
     *
     * @param path the player's path represented as a {@link DoubleUnorderedLinkedList}
     * @return the serialized path as a {@link JSONArray}
     */
    private static JSONArray serializePath(DoubleUnorderedLinkedList<String> path) {
        JSONArray pathArray = new JSONArray();
        for (String step : path) {
            pathArray.add(step);
        }
        return pathArray;
    }

    /**
     * Loads simulation results for a specific mission and version.
     * If the version is set to 0, results for all versions of the mission are loaded.
     *
     * @param missionCode     the mission code
     * @param missionVersion  the mission version (0 to load all versions)
     * @return a {@link DoubleOrderedLinkedList} containing the loaded simulation results
     */
    public static DoubleOrderedLinkedList<SimulationResult> loadResults(String missionCode, int missionVersion) {
        JSONParser parser = new JSONParser();
        DoubleOrderedLinkedList<SimulationResult> results = new DoubleOrderedLinkedList<>();

        try {
            if (!RESULTS_FILE.exists()) {
                // Ensure the file exists
                RESULTS_FILE.getParentFile().mkdirs();
                try (FileWriter writer = new FileWriter(RESULTS_FILE)) {
                    writer.write("[]");
                    writer.flush();
                }
            }

            FileReader reader = new FileReader(RESULTS_FILE);
            JSONArray allResults = (JSONArray) parser.parse(reader);

            for (Object obj : allResults) {
                JSONObject entry = (JSONObject) obj;

                if (entry.get("missionCode").toString().equalsIgnoreCase(missionCode)) {
                    int entryVersion = ((Long) entry.get("missionVersion")).intValue();
                    if (missionVersion == 0 || entryVersion == missionVersion) {
                        JSONArray simulationResults = (JSONArray) entry.get("simulationResults");

                        for (Object resultObj : simulationResults) {
                            JSONObject resultJson = (JSONObject) resultObj;
                            String playerName = (String) resultJson.get("playerName");
                            int playerHealth = ((Long) resultJson.get("playerHealth")).intValue();
                            DoubleUnorderedLinkedList<String> pathTaken = deserializePath((JSONArray) resultJson.get("pathTaken"));

                            SimulationResult result = new SimulationResult(playerName, missionCode, entryVersion, playerHealth, pathTaken);
                            results.add(result);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Deserializes a JSON array into a {@link DoubleUnorderedLinkedList} representing a player's path.
     *
     * @param pathArray the JSON array containing the path
     * @return the deserialized path as a {@link DoubleUnorderedLinkedList}
     */
    private static DoubleUnorderedLinkedList<String> deserializePath(JSONArray pathArray) {
        DoubleUnorderedLinkedList<String> path = new DoubleUnorderedLinkedList<>();
        for (Object step : pathArray) {
            path.addToRear((String) step);
        }
        return path;
    }
}