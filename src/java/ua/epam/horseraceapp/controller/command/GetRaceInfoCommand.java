package ua.epam.horseraceapp.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.ContestantHorseDao;
import ua.epam.horseraceapp.util.dao.RaceDao;
import ua.epam.horseraceapp.util.dao.entity.ContestantHorse;
import ua.epam.horseraceapp.util.dao.entity.Race;
import ua.epam.horseraceapp.util.dao.entity.RaceInfo;

/**
 * Class that represents command that gets race information.
 * 
 * @see RaceInfo
 * @author Koroid Daniil
 */
public class GetRaceInfoCommand extends AbstractCommand {

    /**
     * Command to get race information.
     * <p>
     * Race information contains such information:
     * <ul>
     * <li>Official race information {@link RaceInfo#race}</li>
     * <li>List of horses that participate in given race
     * {@link RaceInfo#horses}</li>
     * </ul>
     * </p>
     *
     * @see RaceInfo
     */
    static final String COMMAND = "race_info";

    /**
     * Identificator of race which information was processed last.
     */
    private static Integer lastRaceId = null;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer raceId = getRaceId(request);
        List<ContestantHorse> horsesInRace = getHorsesInRace(raceId);
        Race race = getRaceById(raceId);
        RaceInfo raceInfo = new RaceInfo(race, horsesInRace);
        request.setAttribute(RACE_INFO, raceInfo);
        request.setAttribute(REQ_ATTRIBUTE, RACE_INFO);
        request.setAttribute(COM_ATTRIBUTE, COMMAND);

        return RACE_INFO_PAGE;
    }

    /**
     * Get race identificator from request.
     * <p>
     * This method is needed to correctly handle getting race identificator from
     * request.
     * </p>
     * <p>
     * This is because race identificator can be:
     * <ul>
     * <li>As parameter - if got from JSP</li>
     * <li>As attribute - if got from other command</li>
     * <li>Cached - if got from JSP with changing language</li>
     * </ul>
     * </p>
     *
     * @param request request that has race identificator
     * @return race identificator
     */
    private Integer getRaceId(HttpServletRequest request) {
        Integer raceId = null;
        try {
            //If got from JSP
            raceId = Integer.valueOf(request.getParameter(RACE_ID));
        } catch (NumberFormatException ex) {
            //If got from other command
            raceId = (Integer) request.getAttribute(RACE_ID);
        }

        if (raceId == null) {
            //If can't get race identificator - than got it from 'changing language'
            raceId = lastRaceId;
        } else {
            //cache race identificator
            lastRaceId = raceId;
        }
        return raceId;
    }

    /**
     * Get horses that participated in race with given identificator.
     *
     * @param raceId race identificator
     * @return horses that participated in race with given identificator
     * @see ContestantHorse
     * @see ContestantHorseDao#findAllHorsesByRaceId(java.lang.Integer)
     */
    private List<ContestantHorse> getHorsesInRace(Integer raceId) {
        ContestantHorseDao contestantHorseDao = factory.createContestantHorseDao();
        return contestantHorseDao.findAllHorsesByRaceId(raceId);
    }

    /**
     * Get race by given identificator.
     *
     * @param raceId race identificator
     * @return race object
     * @see Race
     * @see RaceDao#findRaceById(java.lang.Integer)
     */
    private Race getRaceById(Integer raceId) {
        RaceDao raceDao = factory.createRaceDao();
        return raceDao.findRaceById(raceId);
    }

}
