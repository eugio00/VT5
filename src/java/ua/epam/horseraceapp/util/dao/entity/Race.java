package ua.epam.horseraceapp.util.dao.entity;

import java.sql.Timestamp;

/**
 * Class represents Race.
 * <p>
 * Such information can be stored:
 * <ul>
 * <li>Race identificator {@link #id}</li>
 * <li>Race start time {@link #startTime}</li>
 * <li>Race place {@link #place}</li>
 * <li>Race distance {@link #distance}</li>
 * </ul>
 * </p>
 *
 * @author Koroid Daniil
 */
public class Race {

    /**
     * Race identificator.
     */
    private Integer id;
    /**
     * Time when race takes place.
     */
    private Timestamp startTime;
    /**
     * Race place.
     */
    private String place;
    /**
     * Race distance.
     */
    private Integer distance;

    /**
     * Creates empty Race object.
     */
    public Race() {
    }

    /**
     * Creates Race object with given parameters.
     *
     * @param id race identificator
     * @param startTime race start time
     * @param place race place
     * @param distance race distance
     */
    public Race(Integer id, Timestamp startTime, String place, Integer distance) {
        this.id = id;
        this.startTime = startTime;
        this.place = place;
        this.distance = distance;
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
     * Retrieves race start time.
     *
     * @return race start time
     */
    public Timestamp getStartTime() {
        return startTime;
    }

    /**
     * Set race start time.
     *
     * @param startTime race start time
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * Retrieve race place.
     *
     * @return race place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Set race place.
     *
     * @param place race place to set
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Retrieve race distance.
     *
     * @return race distance
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Set race distance.
     *
     * @param distance race distance to set
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
