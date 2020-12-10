package ua.epam.horseraceapp.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ua.epam.horseraceapp.util.dao.UserDao;
import ua.epam.horseraceapp.util.dao.entity.User;

/**
 * Class that represents command to recharge balance.
 *
 * @author Koroid Daniil
 */
public class RechargeBalanceCommand extends AbstractCommand {

    /**
     * Command to recharge balance.
     */
    static final String COMMAND = "recharge";
    /**
     * Default amount of recharging.
     */
    static final Integer DEFAULT_RECHARGE_AMOUNT = 100;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Integer userId = user.getId();
        Integer rechargeAmount = getRechargeAmount();
        boolean balanceRecharged = rechargeBalance(userId, rechargeAmount);
        if (balanceRecharged) {
            request.setAttribute(MESSAGE_ATTRIBUTE, MSG_BALANCE_SUCCESSFULLY_RECHARGED);
            resetUserInSession(session, userId);
        } else {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_FAILED_RECHARGE_BALANCE);
        }
        return RECHARGE_PAGE;
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
     * Recharges user balance with given recharge amount.
     *
     * @param userId identificator of user to recharge balance
     * @param rechargeAmount recharge amount
     * @return <code>true</code> if recharging was successful. Otherwise
     * <code>false</code>
     * @see UserDao#rechargeBalance(java.lang.Integer, java.lang.Integer)
     */
    private boolean rechargeBalance(Integer userId, Integer rechargeAmount) {
        UserDao userDao = factory.createUserDao();
        return userDao.rechargeBalance(userId, rechargeAmount);
    }

    /**
     * Get balance recharge amount.
     *
     * @return balance recharge amount
     */
    private Integer getRechargeAmount() {
        return DEFAULT_RECHARGE_AMOUNT;
    }

}
