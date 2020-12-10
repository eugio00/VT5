package ua.epam.horseraceapp.util.dao.entity;

import java.sql.Timestamp;

/**
 * Class that represents horse that participates in race.
 * <p>
 * ContestantHorse class describes horse that participates in race with
 * possibility of winning it.
 * </p>
 * <p>
 * Such information can be stored:
 * <ul>
 * <li>Contestant horse identificator {@link #id}</li>
 * <li>Name of the horse that currently contests {@link #horseName}</li>
 * <li>Time when contest race starts {@link #raceStartTime}</li>
 * <li>Place where contest race starts {@link #racePlace}</li>
 * <li>Distance of contest race {@link #raceDistance}</li>
 * <li>Position of horse in contest race {@link #position}</li>
 * <li>Coefficient of this horse to win in contest race
 * {@link #coefficient}</li>
 * </ul>
 * </p>
 *
 * @author Koroid Daniil
 */
public class ContestantHorse {

    /**
     * Contestant horse identificator.
     */
    private Integer id;
    /**
     * Name of the horse that currently contests.
     */
    private String horseName;
    /**
     * Time when contest race starts.
     */
    private Timestamp raceStartTime;
    /**
     * Place where contest race starts.
     */
    private String racePlace;
    /**
     * Distance of contest race.
     */
    private Integer raceDistance;
    /**
     * Position of horse in contest race.
     * <p>
     * If currently there are no contest race results - must be null.
     * </p>
     */
    private Integer position;
    /**
     * Coefficient of this horse to win in contest race.
     */
    private Double coefficient;

    /**
     * Creates empty ContestantHorse object.
     */
    public ContestantHorse() {
    }

    /**
     * Creates ContestantHorse object with given parameteres.
     *
     * @param id contestant horse identificator
     * @param horseName name of the horse that currently contests
     * @param raceStartTime time when contest race starts
     * @param racePlace place where contest race starts
     * @param raceDistance distance of contest race
     * @param position position of horse in contest race
     * @param coefficient coefficient of this horse to win in contest race
     */
    public ContestantHorse(Integer id, String horseName, Timestamp raceStartTime, String racePlace, Integer raceDistance, Integer position, Double coefficient) {
        this.id = id;
        this.horseName = horseName;
        this.raceStartTime = raceStartTime;
        this.racePlace = racePlace;
        this.raceDistance = raceDistance;
        this.position = position;
        this.coefficient = coefficient;
    }

    /**
     * Retrieves contestant horse identificator.
     *
     * @return contestant horse identificator
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set contestant horse identificator.
     *
     * @param id contestant horse identificator to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves name of the horse that currently contests.
     *
     * @return name of the horse that currently contests
     */
    public String getHorseName() {
        return horseName;
    }

    /**
     * Set name of the horse that currently contests.
     *
     * @param horseName name of the horse that currently contests to set
     */
    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    /**
     * Retrieves time when contest race starts.
     *
     * @return time when contest race starts
     */
    public Timestamp getRaceStartTime() {
        return raceStartTime;
    }

    /**
     * Set time when contest race starts.
     *
     * @param raceStartTime time when contest race starts to set
     */
    public void setRaceStartTime(Timestamp raceStartTime) {
        this.raceStartTime = raceStartTime;
    }

    /**
     * Retrieves place where contest race starts.
     *
     * @return place where contest race starts
     */
    public String getRacePlace() {
        return racePlace;
    }

    /**
     * Set place where contest race starts.
     *
     * @param racePlace place where contest race starts to ser
     */
    public void setRacePlace(String racePlace) {
        this.racePlace = racePlace;
    }

    /**
     * Retrieves distance of contest race.
     *
     * @return distance of contest race
     */
    public Integer getRaceDistance() {
        return raceDistance;
    }

    /**
     * Set distance of contest race.
     *
     * @param raceDistance distance of contest race to set
     */
    public void setRaceDistance(Integer raceDistance) {
        this.raceDistance = raceDistance;
    }

    /**
     * Retrieves position of horse in contest race.
     *
     * @return position of horse in contest race
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * Set position of horse in contest race.
     *
     * @param position position of horse in contest race to set
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * Retrieves coefficient of this horse to win in contest race.
     *
     * @return coefficient of this horse to win in contest race
     */
    public Double getCoefficient() {
        return coefficient;
    }

    /**
     * Set coefficient of this horse to win in contest race.
     *
     * @param coefficient coefficient of this horse to win in contest race to
     * ser
     */
    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
}
