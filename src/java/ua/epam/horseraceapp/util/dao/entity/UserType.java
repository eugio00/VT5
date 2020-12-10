package ua.epam.horseraceapp.util.dao.entity;

/**
 * Enumeration that containes types of users.
 * <p>
 * This enumeration contains such user types:
 * <li>{@link #USER} user</li>
 * <li>{@link #ADMIN} admin</li>
 * <li>{@link #BOOKMAKER} bookmaker</li>
 * </p>
 *
 * @author Koroid Daniil
 */
public enum UserType {

    /**
     * Simple user.
     * <p>
     * User can make stakes on horses in different races.
     * </p>
     */
    USER,
    /**
     * Administator.
     * <p>
     * Administator can make stakes and determine results of each horse in
     * specified race.
     * </p>
     */
    ADMIN,
    /**
     * Bookmaker.
     * <p>
     * Bookmaker can make stakes and perform operations with user stakes.
     * </p>
     */
    BOOKMAKER,
}
