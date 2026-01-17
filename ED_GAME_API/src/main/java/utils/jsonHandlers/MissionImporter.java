package utils.jsonHandlers;

import dataStructures.lists.DoubleOrderedLinkedList;
import dataStructures.lists.DoubleUnorderedLinkedList;
import models.mission.Mission;
import models.mission.MissionVersion;
import models.mission.SimulationResult;
import models.world.GameNetwork;
import models.world.Room;
import models.entities.Enemy;
import models.items.Item;
import models.items.Kevlar;
import models.items.MedKit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * A utility class for importing missions from JSON files.
 *
 * This class parses mission data stored in JSON files and converts it into the game’s internal structures,
 * including missions, rooms, enemies, items, and connections. It also links simulation results to missions
 * and marks key attributes like entrances and target rooms.
 */
public class MissionImporter {

    /**
     * A helper class to map room names to their corresponding objects.
     */
    private static class RoomMapping {
        private String name;
        private Room room;

        public RoomMapping(String name, Room room) {
            this.name = name;
            this.room = room;
        }

        public String getName() {
            return name;
        }

        public Room getRoom() {
            return room;
        }
    }

    /**
     * Parses a JSON file to create and return a Mission object.
     *
     * @param filePath the path to the JSON file
     * @return the imported {@code Mission} object, or {@code null} if an error occurs
     */
    public static Mission importMission(String filePath) {
        JSONParser parser = new JSONParser();

        try {
            FileReader reader = new FileReader(filePath);
            JSONObject missionJson = (JSONObject) parser.parse(reader);

            Mission mission = new Mission();

            mission.setCode((String) missionJson.get("cod-missao"));

            int versionNumber = ((Long) missionJson.get("versao")).intValue();

            JSONArray edificioArray = (JSONArray) missionJson.get("edificio");
            int numRooms = edificioArray.size();
            GameNetwork gameNetwork = new GameNetwork(numRooms);

            DoubleUnorderedLinkedList<RoomMapping> roomMappings = new DoubleUnorderedLinkedList<>();
            parseGameNetwork(edificioArray, roomMappings, gameNetwork);

            JSONArray ligacoesArray = (JSONArray) missionJson.get("ligacoes");
            setupConnections(gameNetwork, ligacoesArray, roomMappings);

            JSONArray enemiesArray = (JSONArray) missionJson.get("inimigos");
            assignEnemiesToRooms(roomMappings, enemiesArray);

            JSONArray itemsArray = (JSONArray) missionJson.get("itens");
            assignItemsToRooms(roomMappings, itemsArray);

            JSONArray entradasSaidasArray = (JSONArray) missionJson.get("entradas-saidas");
            markEntrancesExits(roomMappings, entradasSaidasArray);

            JSONObject alvoJson = (JSONObject) missionJson.get("alvo");
            markTargetRoom(roomMappings, alvoJson);

            String targetRoomName = (String) alvoJson.get("divisao");
            mission.setTarget(targetRoomName);

            MissionVersion missionVersion = new MissionVersion(versionNumber, gameNetwork);

            DoubleOrderedLinkedList<SimulationResult> results = SimulationResultManager.loadResults(mission.getCode(), missionVersion.getVersion());
            missionVersion.setSimulationResults(results);

            mission.addMissionVersion(missionVersion);

            return mission;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses the game network and initializes room mappings.
     *
     * @param edificioArray the JSON array containing room names
     * @param roomMappings  the list of room mappings to populate
     * @param gameNetwork   the game network to populate with vertices
     */
    private static void parseGameNetwork(JSONArray edificioArray, DoubleUnorderedLinkedList<RoomMapping> roomMappings, GameNetwork gameNetwork) {
        for (Object obj : edificioArray) {
            String roomName = (String) obj;
            Room room = new Room(roomName);
            gameNetwork.addVertex(room);

            roomMappings.addToRear(new RoomMapping(roomName, room));
        }
    }

    /**
     * Sets up connections between rooms in the game network based on JSON data.
     *
     * @param gameNetwork   the game network to populate with edges
     * @param ligacoesArray the JSON array containing room connections
     * @param roomMappings  the list of room mappings to reference
     */
    private static void setupConnections(GameNetwork gameNetwork, JSONArray ligacoesArray, DoubleUnorderedLinkedList<RoomMapping> roomMappings) {
        for (Object obj : ligacoesArray) {
            JSONArray connection = (JSONArray) obj;
            String room1Name = (String) connection.get(0);
            String room2Name = (String) connection.get(1);

            Room room1 = findRoomByName(room1Name, roomMappings);
            Room room2 = findRoomByName(room2Name, roomMappings);

            if (room1 != null && room2 != null) {
                gameNetwork.addEdge(room1, room2, 1.0);
            }
        }
    }

    /**
     * Assigns enemies to rooms based on JSON data.
     *
     * @param roomMappings the list of room mappings to reference
     * @param enemiesArray the JSON array containing enemy data
     */
    private static void assignEnemiesToRooms(DoubleUnorderedLinkedList<RoomMapping> roomMappings, JSONArray enemiesArray) {
        for (Object obj : enemiesArray) {
            JSONObject enemyJson = (JSONObject) obj;

            String name = (String) enemyJson.get("nome");
            int firePower = ((Long) enemyJson.get("poder")).intValue();
            String roomName = (String) enemyJson.get("divisao");

            Room room = findRoomByName(roomName, roomMappings);
            if (room != null) {
                Enemy enemy = new Enemy(name, firePower, room);
                room.addEnemy(enemy);
            }
        }
    }

    /**
     * Assigns items to rooms based on JSON data.
     *
     * @param roomMappings the list of room mappings to reference
     * @param itemsArray   the JSON array containing item data
     */
    private static void assignItemsToRooms(DoubleUnorderedLinkedList<RoomMapping> roomMappings, JSONArray itemsArray) {
        for (Object obj : itemsArray) {
            JSONObject itemJson = (JSONObject) obj;

            String roomName = (String) itemJson.get("divisao");
            String type = (String) itemJson.get("tipo");

            Room room = findRoomByName(roomName, roomMappings);
            if (room != null) {
                Item item;
                if ("kit de vida".equalsIgnoreCase(type)) {
                    int recoveryPoints = ((Long) itemJson.get("pontos-recuperados")).intValue();
                    item = new MedKit(type, recoveryPoints);
                } else if ("colete".equalsIgnoreCase(type)) {
                    int extraPoints = ((Long) itemJson.get("pontos-extra")).intValue();
                    item = new Kevlar(type, extraPoints);
                } else {
                    continue;
                }
                room.addItem(item);
            }
        }
    }

    /**
     * Marks rooms as entrances or exits based on JSON data.
     *
     * @param roomMappings        the list of room mappings to reference
     * @param entradasSaidasArray the JSON array containing entrance/exit room names
     */
    private static void markEntrancesExits(DoubleUnorderedLinkedList<RoomMapping> roomMappings, JSONArray entradasSaidasArray) {
        for (Object obj : entradasSaidasArray) {
            String roomName = (String) obj;
            Room room = findRoomByName(roomName, roomMappings);
            if (room != null) {
                room.setExistEntry(true);
            }
        }
    }

    /**
     * Marks the target room for the mission based on JSON data.
     *
     * @param roomMappings the list of room mappings to reference
     * @param alvoJson     the JSON object containing the target room name
     */
    private static void markTargetRoom(DoubleUnorderedLinkedList<RoomMapping> roomMappings, JSONObject alvoJson) {
        String roomName = (String) alvoJson.get("divisao");
        Room room = findRoomByName(roomName, roomMappings);
        if (room != null) {
            room.setTarget(true);
        }
    }

    /**
     * Finds a {@link Room} object by its name using room mappings.
     *
     * @param name         the name of the room to find
     * @param roomMappings the list of room mappings to search
     * @return the matching {@code Room} object, or {@code null} if not found
     */
    private static Room findRoomByName(String name, DoubleUnorderedLinkedList<RoomMapping> roomMappings) {
        for (RoomMapping mapping : roomMappings) {
            if (mapping.getName().equals(name)) {
                return mapping.getRoom();
            }
        }
        return null;
    }
}
