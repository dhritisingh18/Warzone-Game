package model;

/**
 * This interface contains {@code start} method for the
 * game phase controllers
 *
 * @author Dhriti Singh
 * @version 1.0
 */

public interface GameHandler {
    /**
     *
     * @param p_GamePhase that holds the current game phase
     * @return each phase returns the succeeding game phase
     * @throws  Exception is thrown if encountered an issue
     */
    GameCycle begin(GameCycle p_GamePhase) throws Exception;
}
