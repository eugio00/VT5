package ua.epam.horseraceapp.controller.command;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Factory for commands.
 *
 * @author Koroid Daniil
 */
public class CommandFactory {

    /**
     * Instance of factory.
     */
    private static final CommandFactory instance = new CommandFactory();

    /**
     * Pool of commands.
     */
    private final Map<String, AbstractCommand> commandsMap;

    /**
     * Contructs factory object.
     * <p>
     * Puts values in command pool.
     * </p>
     */
    private CommandFactory() {
        commandsMap = new HashMap<>();
        commandsMap.put(AcceptBetCommand.COMMAND, new AcceptBetCommand());
        commandsMap.put(CreateResultCommand.COMMAND, new CreateResultCommand());
        commandsMap.put(DeclineBetCommand.COMMAND, new DeclineBetCommand());
        commandsMap.put(DetermineBetResultCommand.COMMAND, new DetermineBetResultCommand());
        commandsMap.put(GetAllRacesCommand.COMMAND, new GetAllRacesCommand());
        commandsMap.put(GetRaceInfoCommand.COMMAND, new GetRaceInfoCommand());
        commandsMap.put(GetUnresultedRacesCommand.COMMAND, new GetUnresultedRacesCommand());
        commandsMap.put(GetUnviewedBetsCommand.COMMAND, new GetUnviewedBetsCommand());
        commandsMap.put(GetUserBetsCommand.COMMAND, new GetUserBetsCommand());
        commandsMap.put(LogInCommand.COMMAND, new LogInCommand());
        commandsMap.put(LogOutCommand.COMMAND, new LogOutCommand());
        commandsMap.put(MakeBetCommand.COMMAND, new MakeBetCommand());
        commandsMap.put(PayBetCommand.COMMAND, new PayBetCommand());
        commandsMap.put(RechargeBalanceCommand.COMMAND, new RechargeBalanceCommand());
        commandsMap.put(RegisterCommand.COMMAND, new RegisterCommand());
    }

    /**
     * Get instance of factory.
     * <p>
     * Singleton.
     * </p>
     *
     * @return single instance of command factory
     */
    public static CommandFactory getInstance() {
        return instance;
    }

    /**
     * Get command associated with given command name.
     * <p>
     * If there is no command associated with given command name - returns
     * default command which execution generates default site page.
     * </p>
     *
     * @param commandName name of command
     * @return instance of command
     */
    public AbstractCommand getCommand(String commandName) {
        AbstractCommand command = commandsMap.get(commandName);
        if (command == null) {
            Logger log = Logger.getLogger(CommandFactory.class);
            log.warn("Unknown command string was given: " + commandName);
            
            return new AbstractCommand() {

                @Override
                public String execute(HttpServletRequest request, HttpServletResponse response) {
                    return "index.jsp";
                }
            };
        } else {
            return command;
        }
    }
}
