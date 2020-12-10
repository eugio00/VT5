package ua.epam.horseraceapp.util.dao.entity;

import java.util.List;

/**
 * Class that represents race and all horses that participate in it.
 * <p>
 * Such information can be stored:
 * <ul>
 * <li>Official race information {@link #race}</li>
 * <li>List of horses that participate in given race {@link #horses}</li>
 * </ul>
 * </p>
 *
 * @see Race
 * @see ContestantHorse
 * @author Koroid Daniil
 */
public class RaceInfo {

    /**
     * Official race information.
     */
    private Race race;
    /**
     * List of horses that participate in given race.
     */
    private List<ContestantHorse> horses;

    /**
     * Create empty RaceInfo object.
     */
    public RaceInfo() {
    }

    /**
     * Create RaceInfo object with given parameters.
     *
     * @param race official race information
     * @param horses list of horses that participate in given race
     */
    public RaceInfo(Race race, List<ContestantHorse> horses) {
        this.race = race;
        this.horses = horses;
    }

    /**
     * Retrieves official race information.
     *
     * @return official race information
     */
    public Race getRace() {
        return race;
    }

    /**
     * Set official race information.
     *
     * @param race official race information to set
     */
    public void setRace(Race race) {
        this.race = race;
    }

    /**
     * Retrieves list of horses that participate in given race.
     *
     * @return list of horses that participate in given race
     */
    public List<ContestantHorse> getHorses() {
        return horses;
    }

    /**
     * Set list of horses that participate in given race.
     *
     * @param horses list of horses that participate in given race to set
     */
    public void setHorses(List<ContestantHorse> horses) {
        this.horses = horses;
    }
}
