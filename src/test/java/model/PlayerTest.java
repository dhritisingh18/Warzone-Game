package model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Testing of player class methods
 *
 * @author Dhriti Singh
 */
public class PlayerTest extends Player {



    Player p = new Player();


    String d_CountryValid, d_CountryInvalid;
    List<Country> d_CapturedCountries = new ArrayList<>();
    Country country1 = new Country();
    Country country2 = new Country();
    Country country3 = new Country();
    Country country4 = new Country();
    Country country5 = new Country();
    GameMatrix d_GameMatrix = GameMatrix.getInstance();

    /**
     * To initialise the vales for test cases
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void Intialise() throws Exception {


        p.setReinforcementArmies(10); // Set initial reinforcement armies to 10.
        p.setName("John");
        country1.setName("Italy");
        country2.setName("France");
        country3.setName("Ireland");
        country4.setName("UK");
        country5.setName("Germany");
        d_CountryValid = "Italy";
        d_CountryInvalid = "Pakistan";
        d_CapturedCountries.add(country1);
        d_CapturedCountries.add(country2);
        d_CapturedCountries.add(country3);
        d_CapturedCountries.add(country4);
        d_CapturedCountries.add(country5);
        p.setCapturedCountries(d_CapturedCountries);
    }

    /**
     * To clear out the values after every test case
     * @throws Exception when execution fails
     */
    @After
    public void cleanUp() throws Exception {
        d_GameMatrix.getContinents().clear();
        d_GameMatrix.getCountries().clear();
        d_GameMatrix.getPlayers().clear();
    }


    /**
     * This is the test method to check the get name function
     *
     */

    @Test
    public void checkGetName() {
        assertEquals("John", p.getName());
    }

    /**
     * This is the test method to check if Country exists
     *
     */
    @Test
    public void checkIfValidCountryExists() {

        assertTrue(p.checkIfCountryExistsInCapturedCountries(d_CountryValid,p));
    }
    /**
     * This is the test method to check if Country does not exist
     *
     */
    @Test
    public void checkIfInvalidCountryExists() {

        assertFalse(p.checkIfCountryExistsInCapturedCountries(d_CountryInvalid,p));
    }
    /**
     * This is the test method to check if valid number of armies are reinforced
     *
     */

    @Test
    public void checkValidReinforcedArmiesForPlayer() {
        assertTrue(p.deployArmiesForPlayer(6)); // Deploying 6 armies, which is valid.
        assertEquals(4, p.getReinforcementArmies()); // Check if the remaining reinforcement armies are correct.
    }
    /**
     * This is the test method to check if invalid number of armies are reinforced
     *
     */
    @Test
    public void checkInvalidReinforcedArmiesFromPlayer() {
        assertFalse(p.deployArmiesForPlayer(15)); // Trying to deploy more armies than available, which is invalid.
        assertEquals(10, p.getReinforcementArmies()); // Ensure that the reinforcement armies remain unchanged.
    }
}

