package ua.epam.horseraceapp.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.RaceDao;
import ua.epam.horseraceapp.util.dao.entity.Race;

/**
 * Class that represents command that gets all races.
 *
 * @see RaceDao#findAll()
 * @author Koroid Daniil
 */
public class GetAllRacesCommand extends AbstractCommand {

    /**
     * Command to get all races.
     */
    static final String COMMAND = "get_races";
    

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Race> allRaces = getAllRaces();
        request.setAttribute(RACES, allRaces);
        request.setAttribute(REQ_ATTRIBUTE, RACES);
        request.setAttribute(COM_ATTRIBUTE, COMMAND);
        return RACES_PAGE;
    }

    /**
     * Get all races.
     *
     * @return list of all races
     * @see RaceDao#findAll() 
     */
    private List<Race> getAllRaces() {
        RaceDao raceDao = factory.createRaceDao();
        return raceDao.findAll();
    }

}
