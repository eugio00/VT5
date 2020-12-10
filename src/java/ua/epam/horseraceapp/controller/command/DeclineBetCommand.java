package ua.epam.horseraceapp.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.BetDao;

/**
 * Class that represents command that declines bet.
 *
 * @see BetDao#declineBet(java.lang.Integer)
 * @author Koroid Daniil
 */
public class DeclineBetCommand extends AbstractCommand {

    /**
     * Command to decline bet.
     */
    static final String COMMAND = "decline_bet";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer betId = Integer.valueOf(request.getParameter(BET_ID));
        boolean betDeclined = declineBet(betId);
        if (betDeclined) {
            request.setAttribute(MESSAGE_ATTRIBUTE, MSG_BET_SUCCESSFULLY_DECLINED);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_FAILED_DECLINE_STAKE);
        }
        return getCommand(GetUnviewedBetsCommand.COMMAND).execute(request, response);
    }

    /**
     * Declines bet.
     * <p>
     * Bet to decline is choosed by given bet identificator.
     * </p>
     * <p>
     * Bet can be successfully declined only when:
     * <ul>
     * <li>Bet previously had state
     * {@link ua.epam.horseraceapp.util.dao.entity.BetState#WAITING_FOR_ACCEPT}</li>
     * <li>Users money were successfully returned to him</li>
     * <li>Bet state was successfully changed to
     * {@link ua.epam.horseraceapp.util.dao.entity.BetState#DECLINED}</li>
     * </ul>
     * For more information - see {@link BetDao#declineBet(java.lang.Integer)}.
     * </p>
     *
     * @param betId identificator of bet to decline
     * @return <code>true</code> if bet is successfully declined. Otherwise
     * <code>false</code>
     * @see BetDao#declineBet(java.lang.Integer)
     */
    private boolean declineBet(Integer betId) {
        BetDao betDao = factory.createBetDao();
        return betDao.declineBet(betId);
    }
}
