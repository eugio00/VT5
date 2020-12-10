package ua.epam.horseraceapp.util.dao.entity;

/**
 * Represents User object.
 * <p>
 * Such information can be stored:
 * <ul>
 * <li>User identificator {@link #id}</li>
 * <li>User first name {@link #firstName}</li>
 * <li>User last name {@link #lastName}</li>
 * <li>User email {@link #email}</li>
 * <li>User password {@link #password}</li>
 * <li>User balance {@link #balance}</li>
 * <li>User type {@link #type}</li>
 * <li></li>
 * </ul>
 * </p>
 *
 * @see UserType
 * @author Koroid Daniil
 */
public class User {

    /**
     * Initial balance of user.
     * <p>
     * Can also be used at new user creation/registration.
     * </p>
     */
    private final static Integer INITIAL_BALANCE = 100;

    /**
     * User identificator.
     */
    private Integer id;
    /**
     * User first name.
     */
    private String firstName;
    /**
     * User last name.
     */
    private String lastName;
    /**
     * User email.
     * <p>
     * Must be checked with proper regular expression.
     * </p>
     */
    private String email;
    /**
     * User password.
     */
    private String password;
    /**
     * User balance.
     * <p>
     * Mustn't be negative.
     * </p>
     */
    private Integer balance;
    /**
     * User type.
     */
    private UserType type;

    /**
     * Creates empty User object.
     */
    public User() {
    }

    /**
     * Creates User object with given parameters.
     *
     * @param id user identificator
     * @param firstName user first name
     * @param lastName user last name
     * @param email user email
     * @param password user password
     * @param balance user balance
     * @param type user type
     */
    public User(Integer id, String firstName, String lastName, String email, String password, Integer balance, UserType type) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.type = type;
    }

    /**
     * Creates User object with given parameters.
     * <p>
     * Must be used at user registration. User's identificator {@link #id} will
     * be created in database. User's balance {@link #balance} will be set to
     * initial balance {@link #INITIAL_BALANCE}.
     * </p>
     *
     * @param firstName user first name
     * @param lastName user last name
     * @param email user email
     * @param password user password
     * @param type user type
     */
    public User(String firstName, String lastName, String email, String password, UserType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.type = type;
        balance = INITIAL_BALANCE;
    }

    /**
     * Retrieves user identificator.
     *
     * @return user identificator
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set user identificator.
     *
     * @param id user identificator to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves user first name.
     *
     * @return user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set user first name.
     *
     * @param firstName user first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves user last name.
     *
     * @return user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set user last name.
     *
     * @param lastName user last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves user email.
     *
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user email.
     *
     * @param email user email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieve user password.
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password.
     *
     * @param password user password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieve user balance.
     *
     * @return user balance
     */
    public Integer getBalance() {
        return balance;
    }

    /**
     * Set user balance.
     *
     * @param balance user balance to set
     */
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    /**
     * Retrieve user type.
     *
     * @return user type
     */
    public UserType getType() {
        return type;
    }

    /**
     * Set user type.
     *
     * @param type user type to set
     */
    public void setType(UserType type) {
        this.type = type;
    }
}
