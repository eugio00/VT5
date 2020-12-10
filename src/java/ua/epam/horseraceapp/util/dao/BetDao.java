package ua.epam.horseraceapp.util.dao;

import java.util.List;
import ua.epam.horseraceapp.util.dao.entity.Bet;
import ua.epam.horseraceapp.util.dao.entity.BetState;
import ua.epam.horseraceapp.util.dao.entity.User;
import ua.epam.horseraceapp.util.dao.entity.UserType;

/**
 * Interface to represent DAO for Bet.
 *
 * @see Bet
 * @author Koroid Daniil
 */
public interface BetDao {

    /**
     * Find all bets of user with given identificator.
     * <p>
     * If there are no bets - returns empty list.
     * </p>
     *
     * @param userId identificator of user whose stakes must be found
     * {@link User#id}
     * @return all bets of user with given identificator
     */
    List<Bet> findUserBets(Integer userId);

    /**
     * Find all unviewed bets.
     * <p>
     * Unviewed bets are such bets that need to be viewed by bookmaker
     * {@link UserType#BOOKMAKER}.
     * </p>
     * <p>
     * If there are no unviewed bets - returns empty list.
     * </p>
     *
     * @return all unviewed bets
     */
    List<Bet> findUnviewedBets();

    /**
     * Creates bet with given amount with user with given identificator as an
     * owner.
     * <p>
     * Creates bet with given amount. This bet's owner is seted user with given
     * identificator.
     * </p>
     * <p>
     * Before creating checks user balance for having enough sum to make this
     * stake. If user's balance is less than given amount - returns
     * <code>false</code> as bet is not done.
     * </p>
     * <p>
     * If user's balance is OK, charges bet amount off an balance and creates
     * bet. If with one of this operations went wrong - nothing performs,
     * <code>false</code> is returned. If this operations went seccessfully -
     * returns <code>true</code>.
     * </p>
     *
     * @param userId bet owner identificator
     * @param amount bet amount
     * @param contestantHorseId beted contestant horse identificator
     * @return <code>true</code> if bet was successfully done. Otherwise
     * <code>false</code>
     */
    boolean makeBet(Integer userId, Integer amount, Integer contestantHorseId);

    /**
     * Changes bet state from {@link BetState#WAITING_FOR_ACCEPT} to
     * {@link BetState#ACCEPTED}.
     * <p>
     * To accept bet it's state previously must be only
     * {@link BetState#WAITING_FOR_ACCEPT}. If it is not - returnes
     * <code>false</code>. If something went wrong while accepting - bet state
     * don't changes and <code>false</code> is returned. If bet state changing
     * to {@link BetState#ACCEPTED} was successful - returns <code>true</code>.
     * </p>
     *
     * @param betId identificator of bet to accept
     * @return <code>true</code> if accepting was right and successful.
     * Otherwise <code>false</code>
     */
    boolean acceptBet(Integer betId);

    /**
     * Changes bet state from {@link BetState#WAITING_FOR_ACCEPT} to
     * {@link BetState#DECLINED}.
     * <p>
     * To decline bet it's state previously must be only
     * {@link BetState#WAITING_FOR_ACCEPT}. If it is not - returnes
     * <code>false</code>. If something went wrong while declining - bet state
     * don't changes and <code>false</code> is returned. If bet state changing
     * to {@link BetState#DECLINED} was successful - returns <code>true</code>.
     * </p>
     *
     * @param betId identificator of bet to decline
     * @return <code>true</code> if declining was right and successful.
     * Otherwise <code>false</code>
     */
    boolean declineBet(Integer betId);

    /**
     * Changes bet state from {@link BetState#ACCEPTED} to
     * {@link BetState#LOSE}.
     * <p>
     * To perform this operation race result that bet was made on must be known.
     * To lose bet it's state previously must be only {@link BetState#ACCEPTED}.
     * If it is not - returnes <code>false</code>. If something went wrong while
     * changing state - bet state don't changes and <code>false</code> is
     * returned. If bet state changing to {@link BetState#LOSE} was successful -
     * returns <code>true</code>.
     * </p>
     *
     * @param betId identificator of bet to change state
     * @return <code>true</code> if declining was right and successful.
     * Otherwise <code>false</code>
     */
    boolean loseBet(Integer betId);

    /**
     * Changes bet state from {@link BetState#ACCEPTED} to
     * {@link BetState#WON_WAITING_FOR_PAY}.
     * <p>
     * To perform this operation race result that bet was made on must be known.
     * To won (but not currently paid) bet it's state previously must be only
     * {@link BetState#ACCEPTED}. If it is not - returnes <code>false</code>. If
     * something went wrong while changing state - bet state don't changes and
     * <code>false</code> is returned. If bet state changing to
     * {@link BetState#WON_WAITING_FOR_PAY} was successful - returns
     * <code>true</code>.
     * </p>
     *
     * @param betId identificator of bet to change state
     * @return <code>true</code> if changing state was right and successful.
     * Otherwise <code>false</code>
     */
    boolean waitForPayBet(Integer betId);

    /**
     * Changes bet state from {@link BetState#WON_WAITING_FOR_PAY} to
     * {@link BetState#WON_PAYED}.
     * <p>
     * To pay bet it's state previously must be only
     * {@link BetState#WON_WAITING_FOR_PAY}. If it is not - returnes
     * <code>false</code>. If something went wrong while changing state - bet
     * state don't changes and <code>false</code> is returned. If something went
     * wrong with paying - bet state don't changes and <code>false</code> is
     * returned. If bet state changing to {@link BetState#WON_PAYED} was
     * successful and bet on win amount ware paid - returns <code>true</code>.
     * </p>
     *
     * @param betId identificator of bet to change state
     * @return <code>true</code> if changing state was right and successful.
     * Otherwise <code>false</code>
     */
    boolean payBet(Integer betId);

    /**
     * Get amount that must be payed on bet win.
     * <p>
     * Returns amount that must be payed on bet win. This amount is calculated
     * by multiplying bet amount {@link Bet#amount} by bet coefficient
     * {@link Bet#coefficient} and flooring that value.
     * </p>
     * <p>
     * If there is not bet with such identificator or something went wrong -
     * <code>null</code> is returned.
     * </p>
     *
     * @param betId identificator of bet to get amount on win
     * @return amount that must be payed on bet win
     */
    Integer getBetOnWinAmount(Integer betId);

    /**
     * Get amount of bet.
     * <p>
     * If there is not bet with such identificator or something went wrong -
     * <code>null</code> is returned.
     * </p>
     *
     * @param betId identificator of bet to get amount
     * @return amount of bet
     */
    Integer getBetAmount(Integer betId);

    /**
     * Get bet owner identificator.
     * <p>
     * If there is not bet with such identificator or something went wrong -
     * <code>null</code> is returned.
     * </p>
     *
     * @param betId identificator of bet to get it's owner
     * @return bet owner identificator
     */
    Integer getBetOwnerId(Integer betId);
}
