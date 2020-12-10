package ua.epam.horseraceapp.util.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ua.epam.horseraceapp.util.connection.ConnectionManager;
import ua.epam.horseraceapp.util.connection.MySqlConnectionManager;
import ua.epam.horseraceapp.util.dao.UserDao;
import ua.epam.horseraceapp.util.dao.entity.User;
import ua.epam.horseraceapp.util.dao.entity.UserType;

/**
 * Implementation of {@link UserDao} for MySQL database.
 *
 * @author Koroid Daniil
 */
public class MySqlUserDao implements UserDao {

    /**
     * Query that is used to register new user.
     * <p>
     * Adds new user to database. Must be used as prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. User first name {@link User#firstName}</li>
     * <li>2. User last name {@link User#lastName}</li>
     * <li>3. User email {@link User#email}</li>
     * <li>4. User password {@link User#password}</li>
     * <li>5. User balance {@link User#balance}</li>
     * <li>6. Name of user's type {@link UserType#name()}</li>
     * </ul>
     */
    private final String REGISTER_USER_QUERY = "INSERT INTO user (first_name, "
            + "last_name, email, password, balance, type_id) VALUES "
            + "(?, ?, ?, ?, ?, "
            + "(SELECT user_type.id FROM user_type WHERE user_type.user_type = ?)"
            + ")";

    /**
     * Query that is used to get all info about user by his email.
     * <p>
     * Selects all information about user by his email. Must be used as prepared
     * statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. User email {@link User#email}</li>
     * </ul>
     * Returns such fields:
     * <ul>
     * <li>1. User id {@link User#id}</li>
     * <li>2. User first name {@link User#firstName}</li>
     * <li>3. User last name {@link User#lastName}</li>
     * <li>4. User password {@link User#password}</li>
     * <li>5. User balance {@link User#balance}</li>
     * <li>6. User type {@link User#type}</li>
     * </ul>
     */
    private final String GET_USER_BY_EMAIL_QUERY = "SELECT user.id, user.first_name, "
            + "user.last_name, user.password, user.balance, user_type.user_type "
            + "FROM user JOIN user_type ON user.type_id = user_type.id "
            + "WHERE user.email = ?";

    /**
     * Query that is used to get all info about user by his identificator.
     * <p>
     * Selects all information about user by his identificator. Must be used as
     * prepared statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. User identificator {@link User#id}</li>
     * </ul>
     * Returns such fields:
     * <ul>
     * <li>1. User first name {@link User#firstName}</li>
     * <li>2. User last name {@link User#lastName}</li>
     * <li>3. User email {@link User#email}</li>
     * <li>4. User password {@link User#password}</li>
     * <li>5. User balance {@link User#balance}</li>
     * <li>6. User type {@link User#type}</li>
     * </ul>
     */
    private final String GET_USER_BY_ID_QUERY = "SELECT user.first_name, "
            + "user.last_name, user.email, user.password, user.balance, "
            + "user_type.user_type FROM user JOIN user_type ON "
            + "user.type_id = user_type.id WHERE user.id = ?";

    /**
     * Query that is used to get user balance by user identificator.
     * <p>
     * Selects user balance by his identificator. Must be used as prepared
     * statement.
     * </p>
     * <p>
     * As a prepared statement has next elements to be seted to work properly:
     * <ul>
     * <li>1. User identificator {@link User#id}</li>
     * </ul>
     * Returns such fields:
     * <ul>
     * <li>1. User balance {@link User#balance}</li>
     * </ul>
     */
    private final String GET_USER_BALANCE_BY_USER_ID_QUERY = "SELECT "
            + "user.balance FROM user WHERE user.id = ?";

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
    private final String INCREASE_USER_BALANCE_QUERY = "UPDATE user SET balance "
            + "= balance + ? WHERE id = ?";

    @Override
    public boolean registerUser(User user) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(REGISTER_USER_QUERY)) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                statement.setString(4, user.getPassword());
                statement.setInt(5, user.getBalance());
                statement.setString(6, user.getType().name());

                int result = statement.executeUpdate();

                if (result != 0) {
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
    public User getUserByEmail(String email) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_QUERY)) {
                statement.setString(1, email);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Integer userId = rs.getInt(1);
                    String firstName = rs.getString(2);
                    String last_name = rs.getString(3);
                    String password = rs.getString(4);
                    Integer balance = rs.getInt(5);
                    UserType userType = UserType.valueOf(rs.getString(6));

                    User user = new User(userId, firstName, last_name, email, password, balance, userType);

                    return user;
                }
            }
        } catch (SQLException ex) {
        }

        return null;
    }

    @Override
    public User getUserById(Integer userId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID_QUERY)) {
                statement.setInt(1, userId);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    String firstName = rs.getString(1);
                    String last_name = rs.getString(2);
                    String email = rs.getString(3);
                    String password = rs.getString(4);
                    Integer balance = rs.getInt(5);
                    UserType userType = UserType.valueOf(rs.getString(6));

                    User user = new User(userId, firstName, last_name, email, password, balance, userType);

                    return user;
                }
            }
        } catch (SQLException ex) {
        }

        return null;
    }

    @Override
    public Integer getUserBalance(Integer userId) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_USER_BALANCE_BY_USER_ID_QUERY)) {
                statement.setInt(1, userId);

                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Integer userBalance = rs.getInt(1);
                    return userBalance;
                }
            }
        } catch (SQLException ex) {
        }
        return null;
    }

    @Override
    public boolean rechargeBalance(Integer userId, Integer rechargeAmount) {
        ConnectionManager connectionManager = MySqlConnectionManager.getInstance();

        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(INCREASE_USER_BALANCE_QUERY)) {
                statement.setInt(1, rechargeAmount);
                statement.setInt(2, userId);

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
}
