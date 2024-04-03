package controller;

import model.GameMatrix;
import model.GameCycle;
import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.InvalidExecutionFailure;
import utils.ValidationFailure;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the functionalities for Reinforcement phase
 *
 * @author Dhriti Singh
 */
public class ReinforcementTest extends Player {
    GameCycle d_NextGameCycle = GameCycle.OrderIssuance;
    GameMatrix d_GameMatrix;
    ReinforcementPhase l_Reinforcement;
    /**
     * This method is for initializing of continent names and country names and player names
     * @throws Exception if there is execution failure
     */
    @Before
    public void initialise() throws Exception {
        d_GameMatrix = GameMatrix.getInstance();
        d_GameMatrix.addContinent("Asia", "5");
        d_GameMatrix.addContinent("NorthAmerica", "5");
        d_GameMatrix.addContinent("SouthAmerica", "5");

        d_GameMatrix.addCountry("India", "Asia");
        d_GameMatrix.addCountry("Canada", "NorthAmerica");
        d_GameMatrix.addCountry("USA", "NorthAmerica");
        d_GameMatrix.addCountry("Japan", "Asia");
        d_GameMatrix.addCountry("Brazil", "SouthAmerica");

        d_GameMatrix.addPlayer("Player1");
        d_GameMatrix.addPlayer("Player2");
        d_GameMatrix.addPlayer("Player3");
        d_GameMatrix.addPlayer("Player4");
        d_GameMatrix.assignCountries();
        l_Reinforcement = new ReinforcementPhase();
        l_Reinforcement.d_GamePhase = GameCycle.Reinforcement;
    }
    /**
     * It will clear out data of every test case
     *
     * @throws Exception when there is a execution failure
     */
    @After
    public void cleanUp() throws Exception {
        d_GameMatrix.getContinents().clear();
        d_GameMatrix.getCountries().clear();
        d_GameMatrix.getPlayers().clear();
    }

    /**
     * This method validates the next game phase
     *
     * @throws ValidationFailure if there is a validation failure
     * @throws InvalidExecutionFailure if validation is invalid
     */
    @Test
    public void checkvalidationOfNextPhase() throws ValidationFailure, InvalidExecutionFailure {
        assertTrue(d_NextGameCycle == l_Reinforcement.begin(GameCycle.Reinforcement));
    }


    /**
     * This method tests whether the reinforcements set are valid or not
     * @throws ValidationFailure if there is validation failure
     * @throws InvalidExecutionFailure if there is invalid execution failure
     */
    @Test
    public void checkReinforcementsForDifferentPlayer() throws ValidationFailure, InvalidExecutionFailure {
        // Set a player with fewer captured countries
        Player playerWithFewerCountries = d_GameMatrix.getPlayer("Player1");
        playerWithFewerCountries.getCapturedCountries().clear();

        l_Reinforcement.d_CurrentPlayer = playerWithFewerCountries;
        l_Reinforcement.setReinforcement();

        int reinforcementArmies = l_Reinforcement.d_CurrentPlayer.getReinforcementArmies();
        assertEquals(5, reinforcementArmies);
    }





}
