package models.mission;

import dataStructures.lists.DoubleOrderedLinkedList;

/**
 * Represents a mission within the game.
 *
 * This class encapsulates the properties and behaviors of a mission, including its code,
 * target type, associated versions, and simulation results. A mission consists of one or
 * more versions, each potentially differing in complexity or objectives.
 *
 */
public class Mission implements MissionInterface {
    private String code;
    private String targetType;
    private DoubleOrderedLinkedList<MissionVersion> missionVersions;

    /**
     * Constructs a new Mission with an initialized list of mission versions.
     */
    public Mission() {
        missionVersions = new DoubleOrderedLinkedList<>();
    }

    /**
     * Retrieves the code of the mission.
     *
     * @return the mission code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code of the mission.
     *
     * @param code the mission code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retrieves the target type of the mission.
     *
     * @return the target type
     */
    public String getTarget() {
        return targetType;
    }

    /**
     * Sets the target type of the mission.
     *
     * @param target the target type to set
     */
    public void setTarget(String target) {
        this.targetType = target;
    }

    /**
     * Adds a version to the mission.
     *
     * @param missionVersion the version of the mission to add
     */
    public void addMissionVersion(MissionVersion missionVersion) {
        missionVersions.add(missionVersion);
    }

    /**
     * Selects a specific version of the mission based on its version number.
     *
     * @param gameVersion the version number to select
     * @return the selected {@link MissionVersion} if found, otherwise {@code null}
     */
    public MissionVersion selectGameVersion(int gameVersion) {
        for (MissionVersion missionVersion : missionVersions) {
            if (missionVersion.getVersion() == gameVersion) {
                return missionVersion;
            }
        }
        return null;
    }

    /**
     * Retrieves all versions of the mission.
     *
     * @return a {@link DoubleOrderedLinkedList} containing all mission versions
     */
    public DoubleOrderedLinkedList<MissionVersion> getMissionVersions() {
        return missionVersions;
    }
}