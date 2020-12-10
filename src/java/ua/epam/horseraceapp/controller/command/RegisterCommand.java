package ua.epam.horseraceapp.controller.command;

import java.util.Objects;
import java.util.regex.Matcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.horseraceapp.util.dao.UserDao;
import ua.epam.horseraceapp.util.dao.entity.User;
import ua.epam.horseraceapp.util.dao.entity.UserType;

/**
 * Class represents command for registration.
 *
 * @author Koroid Daniil
 */
public class RegisterCommand extends AbstractCommand {

    /**
     * Command name to perform registration.
     */
    static final String COMMAND = "register";

    /**
     * Specifies the time, in seconds, between client requests before the
     * servlet container will invalidate this session.
     */
    private final Integer USER_SESSION_MAX_INACTIVE_INTERVAL = 60 * 60 * 2;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter(EMAIL);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);

        if (nullOrEmpty(email)) {
            return setErrorAndReturnRegistrationPage(request, ERR_EMPTY_EMAIL_POLE);
        }

        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);

        if (!emailMatcher.matches()) {
            return setErrorAndReturnRegistrationPage(request, ERR_INCORRECT_EMAIL);
        }

        if (nullOrEmpty(firstName)) {
            return setErrorAndReturnRegistrationPage(request, ERR_EMPTY_FIRST_NAME);
        }

        if (nullOrEmpty(lastName)) {
            return setErrorAndReturnRegistrationPage(request, ERR_EMPTY_LAST_NAME);
        }

        if (password.length() < 4) {
            return setErrorAndReturnRegistrationPage(request, ERR_TOO_SHORT_PASSWORD);
        }

        if (!Objects.equals(password, passwordRepeat)) {
            return setErrorAndReturnRegistrationPage(request, ERR_PASSWORDS_DONT_MATCH);
        }

        User user = new User(firstName, lastName, email, password, UserType.USER);

        if (!registerUser(user)) {
            return setErrorAndReturnRegistrationPage(request, ERR_EMAIL_EXISTS);
        }

        request.setAttribute(EMAIL, email);
        request.setAttribute(PASSWORD, password);

        return getCommand(LogInCommand.COMMAND).execute(request, response);
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
     * Registeres given user.
     *
     * @param user user to register
     * @return
     */
    private boolean registerUser(User user) {
        UserDao userDao = factory.createUserDao();
        return userDao.registerUser(user);
    }

    /**
     * Set given error message to given request and returns registration page.
     *
     * @param request request to set error
     * @param error error message
     * @return registration page
     */
    private String setErrorAndReturnRegistrationPage(HttpServletRequest request, String error) {
        request.setAttribute(ERROR_ATTRIBUTE, error);
        return REGISTRATION_PAGE;
    }
}
