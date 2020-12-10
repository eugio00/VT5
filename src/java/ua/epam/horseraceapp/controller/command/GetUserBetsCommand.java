package ua.epam.horseraceapp.controller.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.BetDao;
import ua.epam.horseraceapp.util.dao.entity.Bet;
import ua.epam.horseraceapp.util.dao.entity.User;

/**
 * Class represents command to get user bets.
 *
 * @author Koroid Daniil
 */
public class GetUserBetsCommand extends AbstractCommand {

    /**
     * Command to get user bets.
     */
    static final String COMMAND = "get_user_bets";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(USER);
        List<Bet> userBets = getUserBetsById(user.getId());

        request.setAttribute(MY_BETS, userBets);
        request.setAttribute(REQ_ATTRIBUTE, MY_BETS);
        request.setAttribute(COM_ATTRIBUTE, COMMAND);

        return USER_BETS_PAGE;
    }

    /**
     * Get all user bets by user identificator.
     *
     * @param userId user identificator
     * @return all user bets by user identificator
     */
    private List<Bet> getUserBetsById(Integer userId) {
        BetDao betDao = factory.createBetDao();
        return betDao.findUserBets(userId);
    }

}
