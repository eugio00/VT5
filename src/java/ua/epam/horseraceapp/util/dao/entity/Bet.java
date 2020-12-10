package ua.epam.horseraceapp.util.dao.entity;

import java.sql.Timestamp;

/**
 * Class that representes bet.
 * <p>
 * Such information can be stored:
 * <ul>
 * <li>Bet identificator {@link #id}</li>
 * <li>Bet owner {@link #owner}</li>
 * <li>Bet state {@link #state}</li>
 * <li>Name of the horse that bet was made on {@link #horseName}</li>
 * <li>Bet coefficient {@link #coefficient}</li>
 * <li>Bet amount {@link #amount}</li>
 * <li>Race place {@link #racePlace}</li>
 * <li>Race start time {@link #raceStartTime}</li>
 * <li>Bet place time {@link #betPlaceTime}</li>
 * <li>Position of horse in race {@link #horsePosition}</li>
 * </ul>
 * </p>
 *
 * @see BetState
 * @author Koroid Daniil
 */
public class Bet {

    /**
     * Bet identificator.
     */
    private Integer id;
    /**
     * Bet owner.
     */
    private User owner;
    /**
     * Bet state.
     */
    private BetState state;
    /**
     * Name of the horse that bet was made on.
     */
    private String horseName;
    /**
     * Bet coefficient.
     */
    private Double coefficient;
    /**
     * Bet amount.
     */
    private Integer amount;
    /**
     * Race place.
     */
    private String racePlace;
    /**
     * Race start time.
     */
    private Timestamp raceStartTime;
    /**
     * Bet place time.
     */
    private Timestamp betPlaceTime;
    /**
     * Position of horse in race.
     */
    private Integer horsePosition;

    /**
     * Creates empty Bet object.
     */
    public Bet() {
    }

    /**
     * Creates Bet objects with given parameters.
     *
     * @param id bet identificator
     * @param owner bet owner
     * @param state bet state
     * @param horseName name of the horse that bet was made on
     * @param coefficient bet coefficient
     * @param amount bet amount
     * @param racePlace race place
     * @param raceStartTime race start time
     * @param betPlaceTime bet place time
     * @param horsePosition position of horse in race
     */
    public Bet(Integer id, User owner, BetState state, String horseName, Double coefficient, Integer amount, String racePlace, Timestamp raceStartTime, Timestamp betPlaceTime, Integer horsePosition) {
        this.id = id;
        this.owner = owner;
        this.state = state;
        this.horseName = horseName;
        this.coefficient = coefficient;
        this.amount = amount;
        this.racePlace = racePlace;
        this.raceStartTime = raceStartTime;
        this.betPlaceTime = betPlaceTime;
        this.horsePosition = horsePosition;
    }

    /**
     * Retrieves bet identificator.
     *
     * @return bet identificator
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set bet identificator.
     *
     * @param id bet identificator to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves bet owner.
     *
     * @return bet owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set bet owner.
     *
     * @param owner bet owner to set
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Retrieves bet state.
     *
     * @return bet state
     */
    public BetState getState() {
        return state;
    }

    /**
     * Set bet state.
     *
     * @param state bet state to set
     */
    public void setState(BetState state) {
        this.state = state;
    }

    /**
     * Retrieves name of the horse that bet was made on.
     *
     * @return name of the horse that bet was made on
     */
    public String getHorseName() {
        return horseName;
    }

    /**
     * Set name of the horse that bet was made on.
     *
     * @param horseName name of the horse that bet was made on to set
     */
    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    /**
     * Retrieves bet coefficient.
     *
     * @return bet coefficient
     */
    public Double getCoefficient() {
        return coefficient;
    }

    /**
     * Set bet coefficient.
     *
     * @param coefficient bet coefficient to set
     */
    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * Retrieves bet amount.
     *
     * @return bet amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Set bet amount.
     *
     * @param amount bet amount to set
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Retrieves race place.
     *
     * @return race place
     */
    public String getRacePlace() {
        return racePlace;
    }

    /**
     * Set race place.
     *
     * @param racePlace race place to set
     */
    public void setRacePlace(String racePlace) {
        this.racePlace = racePlace;
    }

    /**
     * Retrieves race start time.
     *
     * @return race start time
     */
    public Timestamp getRaceStartTime() {
        return raceStartTime;
    }

    /**
     * Set race start time.
     *
     * @param raceStartTime race start time to set
     */
    public void setRaceStartTime(Timestamp raceStartTime) {
        this.raceStartTime = raceStartTime;
    }

    /**
     * Retrieves bet place time.
     *
     * @return bet place time
     */
    public Timestamp getBetPlaceTime() {
        return betPlaceTime;
    }

    /**
     * Set bet place time.
     *
     * @param betPlaceTime bet place time to set
     */
    public void setBetPlaceTime(Timestamp betPlaceTime) {
        this.betPlaceTime = betPlaceTime;
    }

    /**
     * Retrieves position of horse in race.
     *
     * @return position of horse in race
     */
    public Integer getHorsePosition() {
        return horsePosition;
    }

    /**
     * Set position of horse in race.
     *
     * @param horsePosition position of horse in race to set
     */
    public void setHorsePosition(Integer horsePosition) {
        this.horsePosition = horsePosition;
    }
}
