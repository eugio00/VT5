package ua.epam.horseraceapp.util.dao;

import java.util.List;
import ua.epam.horseraceapp.util.dao.entity.ContestantHorse;

/**
 * Interface to represent DAO for ContestantHorse.
 *
 * @see ContestantHorse
 * @author Koroid Daniil
 */
public interface ContestantHorseDao {

    /**
     * Finds all horses in race with given identificator.
     * <p>
     * If there are no horses in this race - returns empty list.
     * </p>
     *
     * @param raceId race identificator {@link Race#id}
     * @return list with all horses({@link ContestantHorse}) in that race
     */
    List<ContestantHorse> findAllHorsesByRaceId(Integer raceId);

    /**
     * Finds all horses without result in race with given identificator.
     * <p>
     * If there are no horses without result in this race - returns empty list.
     * </p>
     *
     * @param raceId race identificator {@link Race#id}
     * @return list with all horses({@link ContestantHorse}) in that race
     */
    List<ContestantHorse> findAllHorsesWithoutResultByRaceId(Integer raceId);

    /**
     * Sets results to horses, that are given.
     * <p>
     * Sets results to horses that are given in list. Horses in list must be
     * given in position order (1 position horse -> 2 position horse -> ... and
     * so on).
     * </p>
     * <p>
     * Returns <code>true</code> if results were successfully seted. Otherwise,
     * if something went wrong, <code>false</code> is returned. In this case
     * none of results is seted.
     * </p>
     *
     * @param horsesInPositionOrder horses in position order (1->2->3 and so on)
     * @return <code>true</code> if results were successfully seted. Otherwise
     * <code>false</code>
     */
    boolean setResults(List<ContestantHorse> horsesInPositionOrder);
}
