package ua.epam.horseraceapp.util.connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * Implementation of connection manager to manage MySQL connections.
 *
 * @author Koroid Daniil
 */
public class MySqlConnectionManager extends ConnectionManager {

    /**
     * Component's environment-related bindings subtree.
     */
    private static final String JAVA_COMP_ENV = "java:comp/env";
    /**
     * Connection pool name.
     * <p>
     * Used to get connection pool that can be found by looking up in
     * component's environment-related bindings subtree.
     * </p>
     */
    private static final String CONNECTION_POOL_NAME = "jdbc/HorseracePool";

    /**
     * Initial context.
     */
    private Context initContext;
    /**
     * Environment naming context.
     */
    private Context envContext;
    /**
     * Connection pool for this application.
     */
    private DataSource dataSource;
    /**
     * Instance of MySQL connection manager.
     */
    private static MySqlConnectionManager instance;

    /**
     * Constructs MySQL connection manager.
     * <p>
     * Looks up for connection pool (data source) for this application.
     * </p>
     */
    private MySqlConnectionManager() {
        try {
            initContext = new InitialContext();
            envContext = (Context) initContext.lookup(JAVA_COMP_ENV);
            dataSource = (DataSource) envContext.lookup(CONNECTION_POOL_NAME);
        } catch (NamingException ex) {
            Logger log = Logger.getLogger(MySqlConnectionManager.class);
            log.error("Error while creating DataSource");
        }
    }

    /**
     * Static method to get single instance of this object.
     *
     * @return instance of MySQL connection manager
     */
    public static MySqlConnectionManager getInstance() {
        if (instance == null) {
            instance = new MySqlConnectionManager();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger log = Logger.getLogger(MySqlConnectionManager.class);
            log.error("Can't get connection from DataSource");   
        }

        return connection;
    }

}
