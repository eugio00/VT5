package ua.epam.horseraceapp.util.dao;

import ua.epam.horseraceapp.util.dao.entity.User;

/**
 * Interface to represent DAO for User.
 *
 * @see User
 * @author Koroid Daniil
 */
public interface UserDao {

    /**
     * Registers new User in database.
     * <p>
     * To be registered new user ({@link User}) must have such parameters set:
     * <ul>
     * <li>1. User first name {@link User#firstName}</li>
     * <li>2. User last name {@link User#lastName}</li>
     * <li>3. User email {@link User#email}</li>
     * <li>4. User password {@link User#password}</li>
     * <li>5. User balance {@link User#balance}</li>
     * <li>6. User type {@link User#type}</li>
     * </ul>
     * If user with equal parameters is already in database - don't adds new
     * user and returns <code>false</code>. Otherwise adds new user to database
     * and returns <code>true</code>.
     * </p>
     * <p>
     * If some errors occure during this process - new user is not added to
     * database and <code>false</code> is returned.
     * </p>
     *
     * @param user user to be added to database
     * @return <code>true</code> if user is successfully added to database.
     * Otherwise <code>false</code>
     * @see User
     */
    boolean registerUser(User user);

    /**
     * Find user in database by his email.
     * <p>
     * Tries to find user in database with such email {@link User#email}. If
     * such user is found - returns him. Otherwise returns <code>null</code>.
     * </p>
     * <p>
     * If some errors occure during this process - <code>null</code> is
     * returned.
     * </p>
     *
     * @param email user email {@link User#email}
     * @return user in database if found such. Otherwise <code>null</code>.
     * @see User
     */
    User getUserByEmail(String email);

    /**
     * Find user in database by his identificator.
     * <p>
     * Tries to find user in database with such identificator {@link User#id}.
     * If such user is found - returns him. Otherwise returns <code>null</code>.
     * </p>
     * <p>
     * If some errors occure during this process - <code>null</code> is
     * returned.
     * </p>
     *
     * @param userId user identificator {@link User#id}
     * @return user in database if found such. Otherwise <code>null</code>.
     * @see User
     */
    User getUserById(Integer userId);

    /**
     * Find user balance in database by his identificator.
     * <p>
     * Tries to find user balance in database with given user identificator
     * {@link User#id}. If found - returns his balance. Otherwise returns
     * <code>null</code>.
     * </p>
     * <p>
     * If some errors occure during this process - <code>null</code> is
     * returned.
     * </p>
     *
     * @param userId user identificator {@link User#id}
     * @return user balance in database if found such. Otherwise
     * <code>null</code>.
     * @see User
     */
    Integer getUserBalance(Integer userId);

    /**
     * Recharge user balance with given sum amount.
     * <p>
     * Recharges balance of user that can be found by his identificator with
     * given sum amount.
     * </p>
     * <p>
     * If recharging is successfull - returns <code>true</code>. Otherwise -
     * balance is not recharged and returns <code>false</code>.
     * </p>
     * <p>
     * If some errors occure during this process - <code>false</code> is
     * returned.
     * </p>
     *
     * @param userId identificator of user which balance has to be recharged
     * {@link User#id}
     * @param rechargeAmount amount of recharge
     * @return <code>true</code> if user balance is successfully recharged.
     * Otherwise <code>false</code>.
     */
    boolean rechargeBalance(Integer userId, Integer rechargeAmount);
}
