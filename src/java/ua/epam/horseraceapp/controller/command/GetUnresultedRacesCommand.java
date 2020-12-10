package ua.epam.horseraceapp.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.RaceDao;
import ua.epam.horseraceapp.util.dao.entity.Race;

/**
 * Class that represents command that gets all unresulted races.
 *
 * @author Koroid Daniil
 */
public class GetUnresultedRacesCommand extends AbstractCommand {

    /**
     * Command that gets all unresulted races.
     */
    static final String COMMAND = "unresulted_races";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Race> unresultedRaces = getAllUnresultedRaces();
        request.setAttribute(ERROR_ATTRIBUTE, request.getAttribute(ERROR_ATTRIBUTE));
        request.setAttribute(UNRESULTED_RACES, unresultedRaces);
        request.setAttribute(REQ_ATTRIBUTE, UNRESULTED_RACES);
        request.setAttribute(COM_ATTRIBUTE, COMMAND);
        return UNRESULTED_RACES_PAGE;
    }

    /**
     * Get all unresulted races.
     *
     * @return all unresulted races
     * @see RaceDao#findUnresultedRaces()
     */
    private List<Race> getAllUnresultedRaces() {
        RaceDao raceDao = factory.createRaceDao();
        return raceDao.findUnresultedRaces();
    }

}
