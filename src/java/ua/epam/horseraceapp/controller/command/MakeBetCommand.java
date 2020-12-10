package ua.epam.horseraceapp.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ua.epam.horseraceapp.util.dao.BetDao;
import ua.epam.horseraceapp.util.dao.RaceDao;
import ua.epam.horseraceapp.util.dao.UserDao;
import ua.epam.horseraceapp.util.dao.entity.User;

/**
 * Class that represents command to make bet.
 *
 * @author Koroid Daniil
 * @see BetDao#makeBet(java.lang.Integer, java.lang.Integer, java.lang.Integer) 
 */
public class MakeBetCommand extends AbstractCommand {

    /**
     * Command to make bet.
     */
    static final String COMMAND = "make_bet";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Integer contestantHorseId = Integer.valueOf(request.getParameter(CONTESTANT_HORSE_ID));

        Integer betAmount = 0;
        try {
            betAmount = Integer.valueOf(request.getParameter(AMOUNT));
        } catch (NumberFormatException ex) {
            return setErrorAndReloadPage(request, response, ERR_EMPTY_BET_AMOUNT_FIELD, contestantHorseId);
        }
        Integer userBalance = user.getBalance();

        //TODO: time checkers (in real project)
        if (betAmount <= 0) {
            return setErrorAndReloadPage(request, response, ERR_NONPOSITIVE_BET_AMOUNT, contestantHorseId);
        } else if (betAmount > userBalance) {
            return setErrorAndReloadPage(request, response, ERR_BET_AMOUNT_GREATER_THEN_USER_BALANCE, contestantHorseId);
        }

        boolean betDone = makeBet(user.getId(), betAmount, contestantHorseId);

        if (betDone) {
            resetUserInSession(session, user.getId());
            return getCommand(GetUserBetsCommand.COMMAND).execute(request, response);
        } else {
            return setErrorAndReloadPage(request, response, ERR_CANT_MAKE_BET, contestantHorseId);
        }
    }

    /**
     * Make bet with given user identificator, contestant horse identificator
     * and bet amount.
     * <p>
     * Creates bet with given amount. This bet's owner is seted user with given
     * identificator.
     * </p>
     * <p>
     * Before creating checks user balance for having enough sum to make this
     * bet. If user's balance is less than given amount - returns
     * <code>false</code> as bet is not done.
     * </p>
     * <p>
     * If user's balance is OK, charges bet amount off an balance and creates
     * bet. If with one of this operations went wrong - nothing performs,
     * <code>false</code> is returned. If this operations went seccessfully -
     * returns <code>true</code>.
     * </p>
     *
     * @param userId user identificator
     * @param betAmount bet amount
     * @param contestantHorseId contestant horse id
     * @return <code>true</code> if bet successfully done. Otherwise
     * <code>false</code>
     * @see BetDao#makeBet(java.lang.Integer, java.lang.Integer,
     * java.lang.Integer)
     */
    private boolean makeBet(Integer userId, Integer betAmount, Integer contestantHorseId) {
        BetDao betDao = factory.createBetDao();
        return betDao.makeBet(userId, betAmount, contestantHorseId);
    }

    /**
     * Get user by user identificator.
     *
     * @param userId user identificator
     * @return user associated with given identificator
     * @see UserDao#getUserById(java.lang.Integer)
     */
    private User getUserById(Integer userId) {
        UserDao userDao = factory.createUserDao();
        return userDao.getUserById(userId);
    }

    /**
     * Resets user in given session to user with given identificator.
     *
     * @param session session to reset user
     * @param userId identificator of user to reset
     */
    private void resetUserInSession(HttpSession session, Integer userId) {
        User user = getUserById(userId);
        session.setAttribute(USER, user);
    }

    /**
     * Sets error message to request and executes command
     * {@link GetRaceInfoCommand#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)},
     * after what returns it's result.
     *
     * @param request HTTP servlet request
     * @param response HTTP servlet response
     * @param error error message to set
     * @param contestantHorseId reload page with information given by contestant
     * horse identificator
     * @return result of performing page reloading
     * @see GetRaceInfoCommand#execute(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    private String setErrorAndReloadPage(HttpServletRequest request, HttpServletResponse response,
            String error, Integer contestantHorseId) {
        request.setAttribute(ERROR_ATTRIBUTE, error);
        RaceDao raceDao = factory.createRaceDao();
        Integer raceId = raceDao.getRaceIdByContestantHorseId(contestantHorseId);
        request.setAttribute(RACE_ID, raceId);
        return getCommand(GetRaceInfoCommand.COMMAND).execute(request, response);
    }
}
