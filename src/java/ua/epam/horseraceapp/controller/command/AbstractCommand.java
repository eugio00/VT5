package ua.epam.horseraceapp.controller.command;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.DaoFactory;

/**
 * Abstract class for command in application.
 *
 * @author Koroid Daniil
 */
public abstract class AbstractCommand {

    /**
     * Pattern for email matching.
     */
    protected static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+\\.?\\w+@\\w+\\.\\w+\\.?\\w+");

    /**
     * Page to display all races.
     */
    protected static final String RACES_PAGE = "races.jsp";
    /**
     * Page to display race information.
     */
    protected static final String RACE_INFO_PAGE = "raceInfo.jsp";
    /**
     * Page that displays all unresulted races.
     */
    protected static final String UNRESULTED_RACES_PAGE = "app/admin/unresultedRaces.jsp";
    /**
     * Page that displays all unviewed bets.
     */
    protected static final String UNVIEWED_BETS_PAGE = "app/bookmaker/unviewedBets.jsp";
    /**
     * Page to display user bets.
     */
    protected static final String USER_BETS_PAGE = "app/myBets.jsp";
    /**
     * Log in page.
     */
    protected static final String LOG_IN_PAGE = "./login.jsp";
    /**
     * Page for admin ({@link UserType#ADMIN}) to be forwarded on successful log
     * in.
     */
    protected static final String ADMIN_MAIN_PAGE = "app/admin/main.jsp";
    /**
     * Page for bookmaker ({@link UserType#BOOKMAKER}) to be forwarded on
     * successful log in.
     */
    protected static final String BOOKMAKER_MAIN_PAGE = "app/bookmaker/main.jsp";
    /**
     * Page for user ({@link UserType#USER}) to be forwarded on successful log
     * in.
     */
    protected static final String USER_MAIN_PAGE = "app/main.jsp";
    /**
     * Site home page.
     */
    protected static final String SITE_HOME_PAGE = "./index.jsp";
    /**
     * Page to recharge balance.
     */
    protected static final String RECHARGE_PAGE = "app/recharge.jsp";
    /**
     * Registration page.
     */
    protected static final String REGISTRATION_PAGE = "register.jsp";

    /**
     * HTTP request parameters.
     */
    protected static final String AMOUNT = "amount";
    protected static final String BET_ID = "bet_id";
    protected static final String CONTESTANT_HORSE_ID = "contestant_horse_id";
    protected static final String EMAIL = "email";
    protected static final String ERROR_ATTRIBUTE = "error";
    protected static final String FIRST_NAME = "first_name";
    protected static final String LAST_NAME = "last_name";
    protected static final String MESSAGE_ATTRIBUTE = "message";
    protected static final String MY_BETS = "my_bets";
    protected static final String PASSWORD = "password";
    protected static final String PASSWORD_REPEAT = "password_repeat";
    protected static final String POSITION = "position";
    protected static final String RACE_ID = "race_id";
    protected static final String RACE_INFO = "race_info";
    protected static final String RACES = "races";
    protected static final String USER = "user";
    protected static final String UNRESULTED_RACES = "unresulted_races";
    protected static final String UNVIEWED_BETS = "unviewed_bets";
    protected static final String MSG_BET_SUCCESSFULLY_ACCEPTED = "message.bet.successfully.accepted";
    protected static final String MSG_BET_SUCCESSFULLY_DECLINED = "message.bet.successfully.declined";
    protected static final String ERR_FAILED_ACCEPT_STAKE = "error.failed.accept.stake";
    protected static final String ERR_CREATING_RESULT_FOR_RACE_WITH_RESULT = "error.creating.result.for.resulted.race";
    protected static final String ERR_CREATING_RESULT = "error.creating.result";
    protected static final String ERR_FAILED_DECLINE_STAKE = "error.failed.decline.stake";
    protected static final String MSG_BET_RESULT_DETERMINED_SUCCESSFULLY = "message.bet.result.determined.successfully";
    protected static final String ERR_BET_RESULT_DETERMINATION_FAILED = "error.bet.result.determination.failed";
    protected static final String ERR_EMPTY_EMAIL_POLE = "error.empty.email.pole";
    protected static final String ERR_INCORRECT_EMAIL = "error.incorrect.email";
    protected static final String ERR_USER_NOT_FOUND = "error.user.not.found";
    protected static final String ERR_INCORRECT_PASSWORD = "error.incorrect.password";
    protected static final String ERR_EMPTY_BET_AMOUNT_FIELD = "error.empty.bet.amount.field";
    protected static final String ERR_NONPOSITIVE_BET_AMOUNT = "error.nonpositive.bet.amount";
    protected static final String ERR_BET_AMOUNT_GREATER_THEN_USER_BALANCE = "error.bet.amount.gt.user.balance";
    protected static final String ERR_CANT_MAKE_BET = "error.cant.make.bet";
    protected static final String MSG_BET_SUCCESSFULLY_PAID = "message.bet.successfully.payed";
    protected static final String ERR_FAILED_PAYED_BET = "error.failed.payed.bet";
    protected static final String MSG_BALANCE_SUCCESSFULLY_RECHARGED = "message.balance.successfully.recharged";
    protected static final String ERR_FAILED_RECHARGE_BALANCE = "error.failed.recharge.balance";
    protected static final String ERR_EMPTY_FIRST_NAME = "error.empty.first.name";
    protected static final String ERR_EMPTY_LAST_NAME = "error.empty.last.name";
    protected static final String ERR_TOO_SHORT_PASSWORD = "error.too.short.password";
    protected static final String ERR_PASSWORDS_DONT_MATCH = "error.passwords.dont.match";
    protected static final String ERR_EMAIL_EXISTS = "error.email.exists";

    /**
     * HTTP request parameter used to to set command that was used.
     */
    protected static final String COM_ATTRIBUTE = "com";
    /**
     * HTTP request parameter used to set value that was added by last command/
     */
    protected static final String REQ_ATTRIBUTE = "req";

    /**
     * Factory to get DAO.
     */
    protected DaoFactory factory;

    /**
     * Instanciates Abstract command.
     * <p>
     * Also creates factory that is used to create Dao.
     * </p>
     */
    public AbstractCommand() {
        factory = DaoFactory.getInstance(DaoFactory.DaoType.MySQL);
    }

    /**
     * Method to execute command.
     *
     * @param request Http servlet request
     * @param response Http servlet response
     * @return path to forward request
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response);

    /**
     * Returns command by given string.
     *
     * @param command string to find command
     * @return command that is associated with given string
     */
    protected AbstractCommand getCommand(String command) {
        return CommandFactory.getInstance().getCommand(command);
    }
}
