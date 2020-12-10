package ua.epam.horseraceapp.util.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ua.epam.horseraceapp.util.connection.ConnectionManager;
import ua.epam.horseraceapp.util.connection.MySqlConnectionManager;
import ua.epam.horseraceapp.util.dao.ContestantHorseDao;
import ua.epam.horseraceapp.util.dao.entity.ContestantHorse;
import ua.epam.horseraceapp.util.dao.entity.Race;

/**
 * Implementation of {@link ContestantHorseDao} for MySQL database.
 *
 * @author Koroid Daniil
 */
public class MySqlContestantHorseDao implements ContestantHorseDao {

    /**
     * Query that is used to select all contestant horses in specified race.
     * <p>
     * Selects all contestant horses that are participating in race with
     * specified identificator. Must be used as a prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Race identificator {@link Race#id}</li>
     * </ul>
     * </p>
     * <p>
     * Returns such fields:
     * <ul>
     * <li>1. Contestant horse id {@link ContestantHorse#id}</li>
     * <li>2. Horse name {@link ContestantHorse#horseName}</li>
     * <li>3. Race start time {@link Race#startTime}</li>
     * <li>4. Race place {@link Race#place}</li>
     * <li>5. Race distance {@link Race#distance}</li>
     * <li>6. Contestant horse position in this race
     * {@link ContestantHorse#position}</li>
     * <li>7. Contestant horse coefficient
     * {@link ContestantHorse#coefficient}</li>
     * </ul>
     * </p>
     *
     */
    private final String SELECT_ALL_HORSES_BY_RACE_ID_QUERY = "SELECT ch.id, "
            + "horse.name, race.start_time, race.place, race.distance, ch.position, "
            + "ch.coefficient FROM contestant_horse AS ch JOIN race ON "
            + "ch.race_id = race.id JOIN horse ON ch.horse_id = horse.id "
            + "WHERE race.id = ?";

    /**
     * Query that is used to select all contestant horses in specified race that
     * don't have result.
     * <p>
     * Selects all contestant horses that are participating in race with
     * specified identificator and don't have result. Must be used as a prepared
     * statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Race identificator {@link Race#id}</li>
     * </ul>
     * </p>
     * <p>
     * Returns such fields:
     * <ul>
     * <li>1. Contestant horse id {@link ContestantHorse#id}</li>
     * <li>2. Horse name {@link ContestantHorse#horseName}</li>
     * <li>3. Race start time {@link Race#startTime}</li>
     * <li>4. Race place {@link Race#place}</li>
     * <li>5. Race distance {@link Race#distance}</li>
     * <li>6. Contestant horse position in this race
     * {@link ContestantHorse#position}</li>
     * <li>7. Contestant horse coefficient
     * {@link ContestantHorse#coefficient}</li>
     * </ul>
     * </p>
     *
     */
    private final String SELECT_ALL_HORSES_WITHOUT_RESULT_BY_RACE_ID_QUERY = "SELECT ch.id, "
            + "horse.name, race.start_time, race.place, race.distance, ch.position, "
            + "ch.coefficient FROM contestant_horse AS ch JOIN race ON "
            + "ch.race_id = race.id JOIN horse ON ch.horse_id = horse.id "
            + "WHERE race.id = ? AND ch.position IS NULL";

    /**
     * Query that is used to set contestant horse position, where contestant
     * horse is chosen with given identificator.
     * <p>
     * This query is used to be executed in batches.
     * </p>
     * <p>
     * Sets contestant horse's position with given position. Contestant horse to
     * set position is selected by given identificator. Must be used as a
     * prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Position to set (detailed info can be read there ->)
     * {@link ContestantHorse#position}</li>
     * <li>2. Contestant horse identificator {@link ContestantHorse#id}</li>
     * </ul>
     * </p>
     */
    private final String SET_RESULT_QUERY = "UPDATE contestant_horse SET "
            + "position = ? WHERE id = ?";

    @Override
    public List<ContestantHorse> findAllHorsesByRaceId(Integer raceId) {
        List<ContestantHorse> horses = new ArrayList<>();
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HORSES_BY_RACE_ID_QUERY)) {
                statement.setInt(1, raceId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Integer id = rs.getInt(1);
                    String horseName = rs.getString(2);
                    Timestamp raceTime = rs.getTimestamp(3);
                    String racePlace = rs.getString(4);
                    Integer raceDistance = rs.getInt(5);
                    Integer position = rs.getInt(6);
                    Double coefficient = rs.getDouble(7);

                    ContestantHorse horse = new ContestantHorse(id, horseName, raceTime, racePlace, raceDistance, position, coefficient);

                    horses.add(horse);
                }
            }
        } catch (SQLException ex) {
        }
        return horses;
    }

    @Override
    public List<ContestantHorse> findAllHorsesWithoutResultByRaceId(Integer raceId) {
        List<ContestantHorse> horses = new ArrayList<>();
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HORSES_WITHOUT_RESULT_BY_RACE_ID_QUERY)) {
                statement.setInt(1, raceId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Integer id = rs.getInt(1);
                    String horseName = rs.getString(2);
                    Timestamp raceTime = rs.getTimestamp(3);
                    String racePlace = rs.getString(4);
                    Integer raceDistance = rs.getInt(5);
                    Integer position = rs.getInt(6);
                    Double coefficient = rs.getDouble(7);

                    ContestantHorse horse = new ContestantHorse(id, horseName, raceTime, racePlace, raceDistance, position, coefficient);

                    horses.add(horse);
                }
            }
        } catch (SQLException ex) {
        }
        return horses;
    }

    @Override
    public boolean setResults(List<ContestantHorse> horsesInPositionOrder) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SET_RESULT_QUERY)) {
                Iterator<ContestantHorse> iterator = horsesInPositionOrder.iterator();
                int currentPosition = 1;
                while (iterator.hasNext()) {
                    ContestantHorse next = iterator.next();
                    Integer contestantHorseId = next.getId();
                    Integer position = currentPosition++;
                    statement.setInt(1, position);
                    statement.setInt(2, contestantHorseId);
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
        }
        return false;
    }
}
