package ua.epam.horseraceapp.controller.command;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.ContestantHorseDao;
import ua.epam.horseraceapp.util.dao.entity.ContestantHorse;

/**
 * Class that represents command that creates result for race with given
 * identificator if this race has no results yet.
 *
 * @see ContestantHorseDao#setResults(java.util.List)
 * @author Koroid Daniil
 */
public class CreateResultCommand extends AbstractCommand {

    /**
     * Create result for race with given identificator command.
     */
    static final String COMMAND = "create_result";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer raceId = Integer.valueOf(request.getParameter(RACE_ID));
        List<ContestantHorse> unresultedHorses = getAllHorsesWithoutResultInRace(raceId);
        if (unresultedHorses.isEmpty()) {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_CREATING_RESULT_FOR_RACE_WITH_RESULT);
            return getCommand(GetUnresultedRacesCommand.COMMAND).execute(request, response);
        }
        List<ContestantHorse> resultedHorses = createResults(unresultedHorses);
        boolean resultsSeted = setResults(resultedHorses);
        if (resultsSeted) {
            request.setAttribute(RACE_ID, raceId);
            return getCommand(GetRaceInfoCommand.COMMAND).execute(request, response);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_CREATING_RESULT);
            return getCommand(GetUnresultedRacesCommand.COMMAND).execute(request, response);
        }
    }

    /**
     * Gets all horses without result in race with given identificator.
     * <p>
     * If in this race all horses have results - returns empty list. If there is
     * no race with such identificator - returns empty list.
     * </p>
     *
     * @param raceId race identificator
     * @return list of horses in race with given identificator
     * @see
     * ContestantHorseDao#findAllHorsesWithoutResultByRaceId(java.lang.Integer)
     */
    private List<ContestantHorse> getAllHorsesWithoutResultInRace(Integer raceId) {
        ContestantHorseDao contestantHorseDao = factory.createContestantHorseDao();
        return contestantHorseDao.findAllHorsesWithoutResultByRaceId(raceId);
    }

    /**
     * Creates result for this race and places horses in ascending position
     * order.
     *
     * @param horses horses without result
     * @return list of horses in ascending position order
     */
    private List<ContestantHorse> createResults(List<ContestantHorse> horses) {
        Collections.shuffle(horses);
        return horses;
    }

    /**
     * Accepts given list of horses as list of horses in ascending positions
     * order and sets this positions as results of this horses in particular
     * race.
     *
     * @param resultedHorses list of horses to set results
     * @return <code>true</code> if results were successfully set. Otherwise
     * <code>false</code>
     * @see ContestantHorseDao#setResults(java.util.List)
     */
    private boolean setResults(List<ContestantHorse> resultedHorses) {
        ContestantHorseDao contestantHorseDao = factory.createContestantHorseDao();
        return contestantHorseDao.setResults(resultedHorses);
    }
}
