package ua.epam.horseraceapp.controller.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.epam.horseraceapp.util.dao.UserDao;
import ua.epam.horseraceapp.util.dao.entity.User;
import ua.epam.horseraceapp.util.dao.entity.UserType;

/**
 * Class that represents log in command.
 *
 * @author Koroid Daniil
 */
public class LogInCommand extends AbstractCommand {

    /**
     * Command to perform log in.
     */
    static final String COMMAND = "login";

    /**
     * Map that associates user type with page to forward user with such type on
     * successful log in.
     */
    static final Map<UserType, String> mainPageMap = new HashMap<UserType, String>() {
        {
            put(UserType.USER, USER_MAIN_PAGE);
            put(UserType.ADMIN, ADMIN_MAIN_PAGE);
            put(UserType.BOOKMAKER, BOOKMAKER_MAIN_PAGE);
        }
    };

    /**
     * Specifies the time, in seconds, between client requests before the
     * servlet container will invalidate this session.
     */
    private final Integer USER_SESSION_MAX_INACTIVE_INTERVAL = 60 * 60 * 2;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = getEmailFromRequest(request);
        if (nullOrEmpty(email)) {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_EMPTY_EMAIL_POLE);
            return LOG_IN_PAGE;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (!matcher.matches()) {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_INCORRECT_EMAIL);
            return LOG_IN_PAGE;
        }

        User user = getUserByEmail(email);

        if (user == null) {
            request.setAttribute(ERROR_ATTRIBUTE, ERR_USER_NOT_FOUND);
            return LOG_IN_PAGE;
        }

        String inputedPassword = getPasswordFromRequest(request);

        if (!Objects.equals(inputedPassword, user.getPassword())) {
            Logger log = Logger.getLogger(LogInCommand.class);
            log.info("Tried to log in as user with id " + user.getId() + " with non-matching password.");
            
            request.setAttribute(ERROR_ATTRIBUTE, ERR_INCORRECT_PASSWORD);
            return LOG_IN_PAGE;
        }

        HttpSession session = request.getSession();
        session.setAttribute(USER, user);
        session.setMaxInactiveInterval(USER_SESSION_MAX_INACTIVE_INTERVAL);

        String mainPage = mainPageMap.get(user.getType());
        return mainPage;

    }

    /**
     * Gets password from request.
     * <p>
     * If password is not seted as a parameter - gets is if it was an attribute.
     * </p>
     *
     * @param request request to find password in.
     * @return password
     */
    private String getPasswordFromRequest(HttpServletRequest request) {
        String password = request.getParameter(PASSWORD);
        if (password == null) {
            password = (String) request.getAttribute(PASSWORD);
        }
        return password;
    }

    /**
     * Gets email from request.
     * <p>
     * If email is not seted as a parameter - gets is if it was an attribute.
     * </p>
     *
     * @param request request to find email in.
     * @return email
     */
    private String getEmailFromRequest(HttpServletRequest request) {
        String email = request.getParameter(EMAIL);
        if (email == null) {
            email = (String) request.getAttribute(EMAIL);
        }
        return email;
    }

    /**
     * Checks if given string is null or empty.
     * <p>
     * If given string is null or empty - returns <code>true</code>. Otherwise
     * returns <code>false</code>.
     * </p>
     *
     * @param string checked string
     * @return if given string is null or empty - returns <code>true</code>.
     * Otherwise returns <code>false</code>
     */
    private boolean nullOrEmpty(String string) {
        return (string == null) || ("".equals(string));
    }

    /**
     * Get user by his email.
     * <p>
     * If there is no user with such email - returns <code>null</code>.
     * </p>
     *
     * @param email user email
     * @return user associated with given email
     */
    private User getUserByEmail(String email) {
        UserDao userDao = factory.createUserDao();
        return userDao.getUserByEmail(email);
    }

}
