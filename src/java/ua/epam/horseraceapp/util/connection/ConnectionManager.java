package ua.epam.horseraceapp.util.connection;

import java.sql.Connection;

/**
 * Abstract class to manage database connection.
 *
 * @author Koroid Daniil
 */
public abstract class ConnectionManager {

    /**
     * Get available connection.
     *
     * @return connection instance
     */
    public abstract Connection getConnection();
}