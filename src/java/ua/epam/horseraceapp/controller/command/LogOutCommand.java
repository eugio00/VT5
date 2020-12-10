package ua.epam.horseraceapp.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that represents log out command.
 *
 * @author Koroid Daniil
 */
public class LogOutCommand extends AbstractCommand {

    /**
     * Command to perform log out.
     */
    static final String COMMAND = "logout";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER);
        return SITE_HOME_PAGE;
    }

}
