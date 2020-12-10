package ua.epam.horseraceapp.controller.command;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.BetDao;

/**
 * Class represents command that determines bet result.
 *
 * @author Koroid Daniil
 */
public class DetermineBetResultCommand extends AbstractCommand {

    /**
     * Command to determine bet result.
     */
    static final String COMMAND = "determine_bet_result";

    /**
     * Position of winner in race.
     */
    static final Integer WINNER_POSITION = 1;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Integer betId = Integer.valueOf(request.getParameter(BET_ID));
        Integer position = Integer.valueOf(request.getParameter(POSITION));
        boolean betResultDetermined = determineBetResult(betId, position);
        if (betResultDetermined) {
            request.setAttribute(MESSAGE_ATTRIBUTE, MSG_BET_RESULT_DETERMINED_SUCCESSFULLY);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_BET_RESULT_DETERMINATION_FAILED);
        }
        return getCommand(GetUnviewedBetsCommand.COMMAND).execute(request, response);
    }

    /**
     * Determines bet result.
     * <p>
     * If determined successfully - returns <code>true</code>. Otherwise
     * <code>false</code>.
     * </p>
     * <p>
     * Determination is process of setting bet stake from
     * {@link ua.epam.horseraceapp.util.dao.entity.BetState#ACCEPTED} to
     * {@link ua.epam.horseraceapp.util.dao.entity.BetState#LOSE} or
     * {@link ua.epam.horseraceapp.util.dao.entity.BetState#WON_WAITING_FOR_PAY}.
     * This process can be done only if horse position is determined.
     * </p>
     *
     * @param betId identificator of bet to determine result
     * @param position position of horse that was beted
     * @return <code>true</code> if determined successfully. Otherwise
     * <code>false</code>
     */
    boolean determineBetResult(Integer betId, Integer position) {
        BetDao betDao = factory.createBetDao();
        if (Objects.equals(position, WINNER_POSITION)) {
            return betDao.waitForPayBet(betId);
        } else {
            return betDao.loseBet(betId);
        }
    }

}
