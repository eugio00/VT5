package ua.epam.horseraceapp.util.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import ua.epam.horseraceapp.util.connection.ConnectionManager;
import ua.epam.horseraceapp.util.connection.MySqlConnectionManager;
import ua.epam.horseraceapp.util.dao.BetDao;
import ua.epam.horseraceapp.util.dao.DaoFactory;
import ua.epam.horseraceapp.util.dao.UserDao;
import ua.epam.horseraceapp.util.dao.entity.Bet;
import ua.epam.horseraceapp.util.dao.entity.BetState;
import ua.epam.horseraceapp.util.dao.entity.User;

/**
 * Implementation of {@link BetDao} for MySQL database.
 *
 * @author Koroid Daniil
 */
public class MySqlBetDao implements BetDao {

    /**
     * Query that is used to select all user bets.
     * <p>
     * Selects all bets that user with specified identificator owns, ordered by
     * race start time. Must be used as a prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. User identificator {@link User#id}</li>
     * </ul>
     * Returns such fields:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * <li>2. Bet state {@link Bet#state}</li>
     * <li>3. Bet coefficient {@link Bet#coefficient}</li>
     * <li>4. Bet amount {@link Bet#amount}</li>
     * <li>5. Beted horse name {@link Bet#horseName}</li>
     * <li>6. Beted race place {@link Bet#racePlace}</li>
     * <li>7. Beted race start time {@link Bet#raceStartTime}</li>
     * <li>8. Bet place time {@link Bet#betPlaceTime}</li>
     * <li>9. Beted horse position in race {@link Bet#horsePosition}</li>
     * </ul>
     * </p>
     */
    private final String SELECT_ALL_BETS_BY_USER_ID_QUERY = "SELECT bet.id, bet_state.state, "
            + "ch.coefficient, bet.amount, horse.name, race.place, "
            + "race.start_time, bet.place_time, ch.position FROM bet JOIN bet_state ON bet.state_id "
            + "= bet_state.id JOIN contestant_horse AS ch ON bet.contestant_horse_id "
            + "= ch.id JOIN horse ON ch.horse_id = "
            + "horse.id JOIN race ON ch.race_id = race.id JOIN "
            + "user ON bet.owner_id = user.id WHERE user.id = ? "
            + "ORDER BY race.start_time";

    /**
     * Query that is used to select all unviewed bets.
     * <p>
     * Selects all unviewed bets. Unviewed bets are such bets that need to be
     * viewed by bookmaker. Must be used as a statement.
     * </p>
     * <p>
     * Returns such fields:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * <li>2. Bet state {@link Bet#state}</li>
     * <li>3. Bet owner identificator {@link User#id}</li>
     * <li>4. Bet amount {@link Bet#amount}</li>
     * <li>5. Bet place time {@link Bet#betPlaceTime}</li>
     * <li>6. Bet coefficient {@link Bet#coefficient}</li>
     * <li>7. Beted race place {@link Bet#racePlace}</li>
     * <li>8. Beted race start time {@link Bet#raceStartTime}</li>
     * <li>9. Beted horse name {@link Bet#horseName}</li>
     * <li>10. Beted horse position in race {@link Bet#horsePosition}</li>
     * </ul>
     * </p>
     */
    private final String GET_UNVIEWED_BETS_QUERY = "SELECT bet.id, bs.state, "
            + "bet.owner_id, bet.amount, bet.place_time, ch.coefficient, "
            + "race.place, race.start_time, horse.name, ch.position FROM bet JOIN "
            + "bet_state AS bs ON bet.state_id = bs.id JOIN "
            + "contestant_horse AS ch ON bet.contestant_horse_id = ch.id "
            + "JOIN horse ON ch.horse_id = horse.id JOIN race ON ch.race_id = "
            + "race.id WHERE bs.state IN ('WAITING_FOR_ACCEPT', "
            + "'WON_WAITING_FOR_PAY') OR ((bs.state IN ('ACCEPTED')) AND "
            + "ch.position IS NOT NULL)";

    /**
     * Query that is used to create bet.
     * <p>
     * Creates bet with given values. Must be used as a prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet state {@link Bet#state}</li>
     * <li>2. Bet owner identificator {@link User#id}</li>
     * <li>3. Bet amount {@link Bet#amount}</li>
     * <li>4. Beted contestant horse identificator
     * {@link ContestantHorse#id}</li>
     * </ul>
     * </p>
     */
    private final String CREATE_BET_QUERY = "INSERT INTO bet (state_id, "
            + "owner_id, amount, contestant_horse_id) VALUES ( (SELECT bet_state.id "
            + "FROM bet_state WHERE bet_state.state = ?), ?, ?, ?)";

    /**
     * Query that is used to set bet state from
     * {@link BetState#WAITING_FOR_ACCEPT} to {@link BetState#ACCEPTED} in bet
     * that is selected by it's identificator.
     * <p>
     * Changes bet state from {@link BetState#WAITING_FOR_ACCEPT} to
     * {@link BetState#ACCEPTED}. Changed bet is selected by it's identificator
     * that is given as parameter. If bet with given identificator has state
     * that differs from {@link BetState#WAITING_FOR_ACCEPT} - nothing performs.
     * Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String ACCEPT_BET_BY_ID_QUERY = "UPDATE bet SET "
            + "bet.state_id = (SELECT bs.id FROM bet_state AS bs WHERE "
            + "state = 'ACCEPTED') WHERE bet.id = ? AND bet.state_id = "
            + "(SELECT bs.id FROM bet_state AS bs WHERE state = "
            + "'WAITING_FOR_ACCEPT')";

    /**
     * Query that is used to set bet state from
     * {@link BetState#WAITING_FOR_ACCEPT} to {@link BetState#DECLINED} in bet
     * that is selected by it's identificator.
     * <p>
     * Changes bet state from {@link BetState#WAITING_FOR_ACCEPT} to
     * {@link BetState#DECLINED}. Changed bet is selected by it's identificator
     * that is given as parameter. If bet with given identificator has state
     * that differs from {@link BetState#WAITING_FOR_ACCEPT} - nothing performs.
     * Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String DECLINE_BET_BY_ID_QUERY = "UPDATE bet SET "
            + "bet.state_id = (SELECT bs.id FROM bet_state AS bs WHERE "
            + "state = 'DECLINED') WHERE bet.id = ? AND bet.state_id = "
            + "(SELECT bs.id FROM bet_state AS bs WHERE state = "
            + "'WAITING_FOR_ACCEPT')";

    /**
     * Query that is used to set bet state from {@link BetState#ACCEPTED} to
     * {@link BetState#LOSE} in bet that is selected by it's identificator.
     * <p>
     * Changes bet state from {@link BetState#ACCEPTED} to
     * {@link BetState#LOSE}. Changed bet is selected by it's identificator that
     * is given as parameter. If bet with given identificator has state that
     * differs from {@link BetState#ACCEPTED} - nothing performs. If trying to
     * change state of bet that currently can't be changed (no known results) -
     * nothing performs. Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String LOSE_BET_BY_ID_QUERY = "UPDATE bet SET "
            + "bet.state_id = (SELECT bs.id FROM bet_state AS bs WHERE "
            + "state = 'LOSE') WHERE bet.id = ? AND bet.state_id = "
            + "(SELECT bs.id FROM bet_state AS bs WHERE state = "
            + "'ACCEPTED') AND (SELECT ch.position FROM contestant_horse AS ch "
            + "WHERE ch.id = bet.contestant_horse_id) IS NOT NULL";

    /**
     * Query that is used to set bet state from {@link BetState#ACCEPTED} to
     * {@link BetState#WON_WAITING_FOR_PAY} in bet that is selected by it's
     * identificator.
     * <p>
     * Changes bet state from {@link BetState#ACCEPTED} to
     * {@link BetState#WON_WAITING_FOR_PAY}. Changed bet is selected by it's
     * identificator that is given as parameter. If bet with given identificator
     * has state that differs from {@link BetState#ACCEPTED} - nothing performs.
     * If trying to change state of bet that currently can't be changed (no
     * known results) - nothing performs. Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String UPDATE_TO_WON_WAITING_FOR_PAY_BET_BY_ID_QUERY = "UPDATE bet SET "
            + "bet.state_id = (SELECT bs.id FROM bet_state AS bs WHERE "
            + "state = 'WON_WAITING_FOR_PAY') WHERE bet.id = ? AND bet.state_id = "
            + "(SELECT bs.id FROM bet_state AS bs WHERE state = "
            + "'ACCEPTED') AND (SELECT ch.position FROM contestant_horse AS ch "
            + "WHERE ch.id = bet.contestant_horse_id) IS NOT NULL";

    /**
     * Query that is used to set bet state from
     * {@link BetState#WON_WAITING_FOR_PAY} to {@link BetState#WON_PAYED} in bet
     * that is selected by it's identificator.
     * <p>
     * Changes bet state from {@link BetState#WON_WAITING_FOR_PAY} to
     * {@link BetState#WON_PAYED}. Changed bet is selected by it's identificator
     * that is given as parameter. If bet with given identificator has state
     * that differs from {@link BetState#WON_WAITING_FOR_PAY} - nothing
     * performs. Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String WON_PAYED_BET_BY_ID_QUERY = "UPDATE bet SET "
            + "bet.state_id = (SELECT bs.id FROM bet_state AS bs WHERE "
            + "state = 'WON_PAYED') WHERE bet.id = ? AND bet.state_id = "
            + "(SELECT bs.id FROM bet_state AS bs WHERE state = "
            + "'WON_WAITING_FOR_PAY')";

    /**
     * Query that is used to get amount on bet win.
     * <p>
     * Gets amount that must be payed to bet owner on bet win. This amount is
     * calculated by multiplying bet amount {@link Bet#amount} by bet
     * coefficient {@link Bet#coefficient} and flooring that value. Bet that has
     * to be calculated is choosed by given identificator. Must be used as
     * prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String GET_BET_ON_WIN_AMOUNT_BY_BET_ID_QUERY = "SELECT "
            + "floor(bet.amount * ch.coefficient) FROM bet JOIN "
            + "contestant_horse AS ch ON bet.contestant_horse_id = ch.id "
            + "WHERE bet.id = ?";

    /**
     * Query that is used to get bet amount.
     * <p>
     * Gets bet amount. Bet is choosed by given identificator. Must be used as
     * prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String GET_BET_AMOUNT_BY_BET_ID = "SELECT amount FROM "
            + "bet WHERE id = ?";

    /**
     * Query that is used to get bet owner identificator.
     * <p>
     * Gets bet owner identificator. Bet is choosed by given identificator. Must
     * be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Bet identificator {@link Bet#id}</li>
     * </ul>
     * </p>
     */
    private final String GET_BET_OWNER_ID_BY_BET_ID_QUERY = "SELECT owner_id "
            + "FROM bet WHERE id = ?";

    /**
     * Query that is used to increase user balance with given value, user is
     * selected by user identificator.
     * <p>
     * Increases user balance with given value. User is selected by user
     * identificator. Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Balance increase value (must be positive) {@link Integer}</li>
     * <li>2. User identificator {@link User#id}</li>
     * </ul>
     * </p>
     */
    private final String INCREASE_USER_BALANCE_QUERY = "UPDATE user SET "
            + "balance = balance + ? WHERE id = ?";

    /**
     * Query that is used to decrease user balance with given value, user is
     * selected by user identificator.
     * <p>
     * Decreases user balance with given value. User is selected by user
     * identificator. Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. Balance decrease value (must be positive) {@link Integer}</li>
     * <li>2. User identificator {@link User#id}</li>
     * </ul>
     * </p>
     */
    private final String DECREASE_USER_BALANCE_QUERY = "UPDATE user SET "
            + "balance = balance - ? WHERE id = ?";

    @Override
    public List<Bet> findUserBets(Integer userId) {
        List<Bet> userBets = new ArrayList<>();
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BETS_BY_USER_ID_QUERY)) {
                statement.setInt(1, userId);

                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Integer betId = rs.getInt(1);
                    User betOwner = getUserByUserId(userId);
                    BetState betState = BetState.valueOf(rs.getString(2));
                    Double coefficient = rs.getDouble(3);
                    Integer amount = rs.getInt(4);
                    String horseName = rs.getString(5);
                    String racePlace = rs.getString(6);
                    Timestamp raceTime = rs.getTimestamp(7);
                    Timestamp betPlaceTime = rs.getTimestamp(8);
                    Integer horsePosition = rs.getInt(9);
                    horsePosition = horsePosition == 0 ? null : horsePosition;

                    Bet bet = new Bet(betId, betOwner, betState, horseName, coefficient, amount, racePlace, raceTime, betPlaceTime, horsePosition);

                    userBets.add(bet);
                }
            }
        } catch (SQLException ex) {
        }
        return userBets;
    }

    @Override
    public List<Bet> findUnviewedBets() {
        List<Bet> unviewedBets = new ArrayList<>();
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();
        try (Connection connection = connectionManager.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(GET_UNVIEWED_BETS_QUERY);

                while (rs.next()) {
                    Integer id = rs.getInt(1);
                    BetState state = BetState.valueOf(rs.getString(2));
                    User betOwner = getUserByUserId(rs.getInt(3));
                    Integer amount = rs.getInt(4);
                    Timestamp betPlaceTime = rs.getTimestamp(5);
                    Double coefficient = rs.getDouble(6);
                    String racePlace = rs.getString(7);
                    Timestamp raceTime = rs.getTimestamp(8);
                    String horseName = rs.getString(9);
                    Integer position = rs.getInt(10);
                    position = position == 0 ? null : position;

                    Bet bet = new Bet(id, betOwner, state, horseName,
                            coefficient, amount, racePlace, raceTime, betPlaceTime, position);

                    unviewedBets.add(bet);
                }
            }
        } catch (SQLException ex) {
        }
        return unviewedBets;
    }

    @Override
    public boolean makeBet(Integer userId, Integer amount, Integer contestantHorseId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        if (!checkUserBalance(userId, amount)) {
            return false;
        }

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement createBetStatement = connection.prepareStatement(CREATE_BET_QUERY);
                    PreparedStatement changeUserBalanceStatement = connection.prepareStatement(DECREASE_USER_BALANCE_QUERY)) {

                changeUserBalanceStatement.setInt(1, amount);
                changeUserBalanceStatement.setInt(2, userId);

                createBetStatement.setString(1, BetState.WAITING_FOR_ACCEPT.name());
                createBetStatement.setInt(2, userId);
                createBetStatement.setInt(3, amount);
                createBetStatement.setInt(4, contestantHorseId);

                int userBalanceChangeResult = changeUserBalanceStatement.executeUpdate();
                int createBetResult = createBetStatement.executeUpdate();

                if ((createBetResult > 0) && (userBalanceChangeResult > 0)) {
                    connection.commit();
                    return true;
                }
            } catch (SQLException ex) {
            }
            connection.rollback();
        } catch (SQLException ex) {
        }
        return false;
    }

    @Override
    public boolean acceptBet(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(ACCEPT_BET_BY_ID_QUERY)) {
                statement.setInt(1, betId);
                statement.executeUpdate();
                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    @Override
    public boolean declineBet(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement declineBetStatement = connection.prepareStatement(DECLINE_BET_BY_ID_QUERY);
                    PreparedStatement increaseUserBalanceStatement = connection.prepareStatement(INCREASE_USER_BALANCE_QUERY)) {

                declineBetStatement.setInt(1, betId);

                Integer betAmount = getBetAmount(betId);
                Integer betOwnerId = getBetOwnerId(betId);
                increaseUserBalanceStatement.setInt(1, betAmount);
                increaseUserBalanceStatement.setInt(2, betOwnerId);

                declineBetStatement.executeUpdate();
                increaseUserBalanceStatement.executeUpdate();

                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
            }
        } catch (SQLException ex) {
        }
        return false;
    }

    @Override
    public boolean loseBet(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(LOSE_BET_BY_ID_QUERY)) {
                statement.setInt(1, betId);
                int updateResult = statement.executeUpdate();
                if (updateResult != 0) {
                    connection.commit();
                    return true;
                }
            } catch (SQLException ex) {
            }
            connection.rollback();
        } catch (SQLException ex) {
        }
        return false;
    }

    @Override
    public boolean waitForPayBet(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_TO_WON_WAITING_FOR_PAY_BET_BY_ID_QUERY)) {
                statement.setInt(1, betId);
                int updateResult = statement.executeUpdate();
                if (updateResult != 0) {
                    connection.commit();
                    return true;
                }
            } catch (SQLException ex) {
            }
            connection.rollback();
        } catch (SQLException ex) {
        }
        return false;
    }

    @Override
    public boolean payBet(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement changeBetStateStatement = connection.prepareStatement(WON_PAYED_BET_BY_ID_QUERY);
                    PreparedStatement changeUserBalanceStatement = connection.prepareStatement(INCREASE_USER_BALANCE_QUERY)) {

                changeBetStateStatement.setInt(1, betId);

                Integer betOnWinAmount = getBetOnWinAmount(betId);
                Integer betOwnerId = getBetOwnerId(betId);
                changeUserBalanceStatement.setInt(1, betOnWinAmount);
                changeUserBalanceStatement.setInt(2, betOwnerId);

                int betStateChangeResult = changeBetStateStatement.executeUpdate();
                int userBalanceChangeResult = changeUserBalanceStatement.executeUpdate();

                if ((betStateChangeResult > 0) && (userBalanceChangeResult > 0)) {
                    connection.commit();
                    return true;
                }
            } catch (SQLException ex) {
            }
            connection.rollback();
        } catch (SQLException ex) {
        }
        return false;
    }

    @Override
    public Integer getBetOnWinAmount(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_BET_ON_WIN_AMOUNT_BY_BET_ID_QUERY)) {
                statement.setInt(1, betId);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Integer betOnWinAmount = rs.getInt(1);
                    return betOnWinAmount;
                }
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    @Override
    public Integer getBetAmount(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_BET_AMOUNT_BY_BET_ID)) {
                statement.setInt(1, betId);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Integer betAmount = rs.getInt(1);
                    return betAmount;
                }
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    @Override
    public Integer getBetOwnerId(Integer betId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_BET_OWNER_ID_BY_BET_ID_QUERY)) {
                statement.setInt(1, betId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    Integer userId = rs.getInt(1);
                    return userId;
                }
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    /**
     * Returns User by his identificator.
     * <p>
     * Uses {@link UserDao} to retrieve User.
     * </p>
     *
     * @param userId user to get
     * @return user by his identificator
     */
    private User getUserByUserId(Integer userId) {
        DaoFactory factory = DaoFactory.getInstance(DaoFactory.DaoType.MySQL);
        UserDao userDao = factory.createUserDao();
        User user = userDao.getUserById(userId);
        return user;
    }

    /**
     * Find user balance by given user identificator and check if it is greater
     * or equals to given sum.
     *
     * @param userId identificator of user whose balance to be checked
     * @param sum sum to assure on user's balance
     * @return <code>true</code> if found such or greater sum on user's balance.
     * Otherwise <code>false</code>
     */
    private boolean checkUserBalance(Integer userId, Integer sum) {
        DaoFactory factory = DaoFactory.getInstance(DaoFactory.DaoType.MySQL);
        UserDao userDao = factory.createUserDao();
        Integer userBalance = userDao.getUserBalance(userId);
        if (userBalance == null) {
            return false;
        }
        return userBalance >= sum;
    }
}
