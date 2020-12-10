package ua.epam.horseraceapp.util.dao;

import java.util.List;
import ua.epam.horseraceapp.util.dao.entity.Race;

/**
 * Interface to represent DAO for Race.
 * 
 * @see Race
 * @author Koroid Daniil
 */
public interface RaceDao {

    /**
     * Finds all races in database.
     * <p>
     * If there are no races - returns empty list.
     * </p>
     *
     * @return all races
     * @see Race
     */
    List<Race> findAll();

    /**
     * Finds all races that have no results.
     * <p>
     * If there are no such races - returns empty list.
     * </p>
     *
     * @return all races that have no results
     * @see Race
     */
    List<Race> findUnresultedRaces();

    /**
     * Find race in database by its identificator.
     * <p>
     * Tries to find race in database with such identificator {@link Race#id}.
     * If such race is found - returns it. Otherwise returns <code>null</code>.
     * </p>
     * <p>
     * If some errors occure during this process - <code>null</code> is
     * returned.
     * </p>
     *
     * @param raceId race identificator {@link Race#id}
     * @return race in database if found such. Otherwise <code>null</code>.
     * @see Race
     */
    Race findRaceById(Integer raceId);

    /**
     * Find race in database by its identificator.
     * <p>
     * Tries to find race in database with such identificator {@link Race#id}.
     * If such race is found - returns it. Otherwise returns <code>null</code>.
     * </p>
     * <p>
     * If some errors occure during this process - <code>null</code> is
     * returned.
     * </p>
     *
     * @param contestantHorseId race identificator {@link Race#id}
     * @return race in database if found such. Otherwise <code>null</code>.
     * @see Race
     */
    Integer getRaceIdByContestantHorseId(Integer contestantHorseId);
}
