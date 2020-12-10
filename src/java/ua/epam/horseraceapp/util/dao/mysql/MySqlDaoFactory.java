package ua.epam.horseraceapp.util.dao.mysql;

import ua.epam.horseraceapp.util.dao.BetDao;
import ua.epam.horseraceapp.util.dao.ContestantHorseDao;
import ua.epam.horseraceapp.util.dao.DaoFactory;
import ua.epam.horseraceapp.util.dao.RaceDao;
import ua.epam.horseraceapp.util.dao.UserDao;

/**
 * DAO Factory implementation for MySQL database.
 *
 * @see DaoFactory
 * @author Koroid Daniil
 */
public class MySqlDaoFactory extends DaoFactory {

    @Override
    public ContestantHorseDao createContestantHorseDao() {
        return new MySqlContestantHorseDao();
    }

    @Override
    public RaceDao createRaceDao() {
        return new MySqlRaceDao();
    }

    @Override
    public BetDao createBetDao() {
        return new MySqlBetDao();
    }

    @Override
    public UserDao createUserDao() {
        return new MySqlUserDao();
    }
}
