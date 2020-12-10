package ua.epam.horseraceapp.util.dao.entity;

/**
 * Enumeration that containes types of bets.
 * <p>
 * This enumeration contains such bet types:
 * <li>{@link #WON_PAYED} bet won and was payed</li>
 * <li>{@link #WON_WAITING_FOR_PAY} bet won and is waiting for being paid</li>
 * <li>{@link #LOSE} bet lose</li>
 * <li>{@link #ACCEPTED} bet was accepted by bookmaker and is waiting for race
 * result</li>
 * <li>{@link #DECLINED} bet was declined by bookmaker. user's money must be
 * returned to user</li>
 * <li>{@link #WAITING_FOR_ACCEPT} bet is waiting to be accepted by
 * bookmaker</li>
 * </p>
 *
 * @author Koroid Daniil
 */
public enum BetState {

    /**
     * Bet won and was payed.
     * <p>
     * Bet won and was payed by bookmaker. Appropriate sum was added to user's
     * balance.
     * </p>
     * <p>
     * This is one of final bet states.
     * </p>
     */
    WON_PAYED,
    /**
     * Bet won and is waiting for being paid.
     * <p>
     * Bet won and is waiting for being paid by bookmaker. Appropriate sum must
     * be added to user's balance.
     * </p>
     * <p>
     * After paying appropriate sum to user bet state must be changed to
     * {@link #WON_PAYED}.
     * </p>
     */
    WON_WAITING_FOR_PAY,
    /**
     * Bet lose.
     * <p>
     * Bet lose. Horse that user maid bet on has not won the race.
     * </p>
     * <p>
     * This is one of final bet states.
     * </p>
     */
    LOSE,
    /**
     * Bet was accepted by bookmaker and is waiting for race result.
     * <p>
     * Bet was accepted by bookmaker and is waiting for race result. After race
     * end bet's state must be changed.
     * </p>
     * <p>
     * After race end bet's state must be changed either to
     * {@link #WON_WAITING_FOR_PAY} (if bet won) or to {@link #LOSE} (if bet
     * lose).
     * </p>
     */
    ACCEPTED,
    /**
     * Bet was declined by bookmaker.
     * <p>
     * Bet was declined by bookmaker. User's money must be returned to user.
     * </p>
     * <p>
     * This is one of final bet states.
     * </p>
     */
    DECLINED,
    /**
     * Bet is waiting to be accepted by bookmaker.
     * <p>
     * Bet is waiting to be accepted by bookmaker. Bookmaker must review this
     * bet before race starts.
     * </p>
     * <p>
     * After bookmaker's review bet's state must be changed either to
     * {@link #ACCEPTED} (if bet was accepted by bookmaker) or to
     * {@link #DECLINED} (if bet was declined by bookmaker).
     * </p>
     */
    WAITING_FOR_ACCEPT,;

    /**
     * Returns bet state in view for use in resource bundling.
     * <p>
     * Returns bet state name {@link #name()} in lower case
     * {@link String#toLowerCase()} with all symbols '_' replaced with '.'
     * {@link String#replaceAll(java.lang.String, java.lang.String)}.
     * </p>
     *
     * @return bet state in view for use in resource bundling
     */
    public String toBundleString() {
        return name().toLowerCase().replaceAll("_", ".");
    }
}
