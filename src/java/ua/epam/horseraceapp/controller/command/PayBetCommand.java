package ua.epam.horseraceapp.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.BetDao;

/**
 * Class that represents command to pay bet.
 *
 * @author Koroid Daniil
 */
public class PayBetCommand extends AbstractCommand {

    /**
     * Command to pay bet.
     */
    static final String COMMAND = "pay_bet";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer betId = Integer.valueOf(request.getParameter(BET_ID));
        boolean betPayed = payBet(betId);
        if (betPayed) {
            request.setAttribute(MESSAGE_ATTRIBUTE, MSG_BET_SUCCESSFULLY_PAID);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_FAILED_PAYED_BET);
        }
        return getCommand(GetUnviewedBetsCommand.COMMAND).execute(request, response);
    }

    /**
     * Pays money to bet owner and changes bet state.
     * <p>
     * To pay bet it's state previously must be only
     * {@link BetState#WON_WAITING_FOR_PAY}. If it is not - returnes
     * <code>false</code>. If something went wrong while changing state - bet
     * state don't changes and <code>false</code> is returned. If something went
     * wrong with paying - bet state don't changes and <code>false</code> is
     * returned. If bet state changing to {@link BetState#WON_PAYED} was
     * successful and bet on win amount ware paid - returns <code>true</code>.
     * </p>
     *
     * @param betId identificator of bet to change state
     * @return <code>true</code> if bet paying off was successful. Otherwise
     * <code>false</code>
     */
    private boolean payBet(Integer betId) {
        BetDao betDao = factory.createBetDao();
        return betDao.payBet(betId);
    }

}
