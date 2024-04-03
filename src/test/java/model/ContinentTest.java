package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * This class tests the functionalities for Continent class
 *
 * @author Rabia Tahir
 * @author Ritik Gulati
 */

public class ContinentTest extends Continent {
    GameMatrix d_GameMatrix = GameMatrix.getInstance();
    Continent c =new Continent();

    /**
     * To initialise the vales for test cases
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void Intialise() throws Exception {
        c.setName("Europe");

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
     * This is the test method to check the get continent name function.
     */
    @Test
    public void checkContinentName(){
        assertEquals("Europe", c.getName());
    }

}
