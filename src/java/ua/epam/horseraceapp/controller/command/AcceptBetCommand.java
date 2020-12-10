package ua.epam.horseraceapp.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.BetDao;

/**
 * Class that represents command that accepts bet.
 *
 * @see BetDao#acceptBet(java.lang.Integer)
 * @author Koroid Daniil
 */
public class AcceptBetCommand extends AbstractCommand {

    /**
     * Command to accept bet.
     */
    static final String COMMAND = "accept_bet";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer betId = Integer.valueOf(request.getParameter(BET_ID));
        boolean betAccepted = acceptBet(betId);
        if (betAccepted) {
            request.setAttribute(MESSAGE_ATTRIBUTE, MSG_BET_SUCCESSFULLY_ACCEPTED);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_FAILED_ACCEPT_STAKE);
        }
        return getCommand(GetUnviewedBetsCommand.COMMAND).execute(request, response);
    }

    /**
     * Accepts bet.
     * <p>
     * Bet to accept is choosed by given bet identificator.
     * </p>
     * <p>
     * Bet can be successfully accepted only when:
     * <ul>
     * <li>Bet previously had state
     * {@link ua.epam.horseraceapp.util.dao.entity.BetState#WAITING_FOR_ACCEPT}</li>
     * <li>Bet state was successfully changed to
     * {@link ua.epam.horseraceapp.util.dao.entity.BetState#ACCEPTED}</li>
     * </ul>
     * For more information - see {@link BetDao#acceptBet(java.lang.Integer)}.
     * </p>
     *
     * @param betId identificator of bet to accept
     * @return <code>true</code> if bet is successfully accepted. Otherwise
     * <code>false</code>
     * @see BetDao#acceptBet(java.lang.Integer)
     */
    private boolean acceptBet(Integer betId) {
        BetDao betDao = factory.createBetDao();
        return betDao.acceptBet(betId);
    }
}
