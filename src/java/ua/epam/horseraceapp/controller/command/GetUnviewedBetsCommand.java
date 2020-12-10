package ua.epam.horseraceapp.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.BetDao;
import ua.epam.horseraceapp.util.dao.entity.Bet;

/**
 * Class that represents command to find all unviewed bets.
 * <p>
 * Unviewed bets are such bets that need to be viewed by bookmaker
 * {@link UserType#BOOKMAKER}.
 * </p>
 * 
 * @author Koroid Daniil
 */
public class GetUnviewedBetsCommand extends AbstractCommand {

    /**
     * Command to find all unviewed bets.
     */
    static final String COMMAND = "unviewed_bets";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Bet> unviewedBets = getAllUnviewedBets();
        request.setAttribute(UNVIEWED_BETS, unviewedBets);
        request.setAttribute(REQ_ATTRIBUTE, UNVIEWED_BETS);
        request.setAttribute(COM_ATTRIBUTE, COMMAND);

        return UNVIEWED_BETS_PAGE;
    }

    /**
     * Get all unviewed bets.
     *
     * @return all unviewed bets
     * @see BetDao#findUnviewedBets()
     */
    private List<Bet> getAllUnviewedBets() {
        BetDao betDao = factory.createBetDao();
        return betDao.findUnviewedBets();
    }
}
