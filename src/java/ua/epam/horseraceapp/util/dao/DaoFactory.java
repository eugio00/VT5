package ua.epam.horseraceapp.util.dao;

/**
 * Abstract factory to get proper DAO.
 *
 * @author Koroid Daniil
 */
public abstract class DaoFactory {

    /**
     * Enumeration that contains types of supported databases.
     * <p>
     * Currently supported databases are:
     * <ul>
     * <li>{@link #MySQL} MySQL database</li>
     * </ul>
     * </p>
     */
    public static enum DaoType {

        /**
         * Type that represents MySQL database.
         */
        MySQL("ua.epam.horseraceapp.util.dao.mysql.MySqlDaoFactory"),;

        /**
         * Path to DAO factory.
         */
        private final String pathToClass;

        /**
         * Creates enumeration element that represents database type and path to
         * apropriate DAO factory class.
         *
         * @param pathToClass path to appropriate DAO factory class
         */
        DaoType(String pathToClass) {
            this.pathToClass = pathToClass;
        }
    }

    /**
     * Static method to get instance of DAO factory for given database type.
     *
     * @param type type of database
     * @return instance of DAO factory for given database type if this database
     * is supported. If not - returns <code>null</code>
     */
    public static DaoFactory getInstance(DaoType type) {
        switch (type) {
            case MySQL:
                try {
                    Class<?> clazz = Class.forName(type.pathToClass);
                    return (DaoFactory) clazz.newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    return null;
                }
            default:
                return null;
        }
    }

    /**
     * Create ContestantHorse DAO.
     *
     * @return ContestantHorse DAO
     */
    public abstract ContestantHorseDao createContestantHorseDao();

    /**
     * Create Race DAO.
     *
     * @return Race DAO
     */
    public abstract RaceDao createRaceDao();

    /**
     * Create Bet DAO.
     *
     * @return Bet DAO
     */
    public abstract BetDao createBetDao();

    /**
     * Create User DAO.
     *
     * @return User DAO
     */
    public abstract UserDao createUserDao();

}
