package utils;

import model.GameMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * It checks the methods related to validation of map
 *
 * @author Simran Simran
 */
public class MapTest {
    GameMatrix d_GameMatrix;

    /**
     * to initialise the test cases
     *
     * @throws Exception if execution fails
     */
    @Before
    public void initialise() throws Exception {
        d_GameMatrix = GameMatrix.getInstance();
        d_GameMatrix.addContinent("Asia", "5");
        d_GameMatrix.addContinent("Africa", "5");
        d_GameMatrix.addContinent("NorthAmerica", "5");
        d_GameMatrix.addContinent("SouthAmerica", "5");
        d_GameMatrix.addContinent("Australlia", "5");

        d_GameMatrix.addCountry("India", "Asia");
        d_GameMatrix.addCountry("Egypt", "Africa");
        d_GameMatrix.addCountry("Brazil", "SouthAmerica");
        d_GameMatrix.addCountry("Sydney", "Australia");
        d_GameMatrix.addCountry("Canada", "NorthAmerica");

        d_GameMatrix.addNeighbor("India", "Egypt");
        d_GameMatrix.addNeighbor("Egypt", "India");
        d_GameMatrix.addNeighbor("India", "Brazil");
        d_GameMatrix.addNeighbor("Brazil", "India");
        d_GameMatrix.addNeighbor("Sydney", "Canada");
        d_GameMatrix.addNeighbor("Canada", "Sydney");
        d_GameMatrix.addNeighbor("Brazil", "Sydney");
        d_GameMatrix.addNeighbor("Sydney", "Brazil");
    }

    /**
     * This method executes to clear values after test cases
     *
     * @throws Exception when execution fails
     */
    @After
    public void cleanUp() throws Exception {
        d_GameMatrix.getContinents().clear();
        d_GameMatrix.getCountries().clear();
        d_GameMatrix.getPlayers().clear();
    }

    /**
     * It checks if there is no country in the continent
     *
     * @throws ValidationFailure if there is a validation failure
     */
    @Test
    public void checkIfEmptyContinent() throws ValidationFailure {
        d_GameMatrix.addContinent("Europe", "5");
        assertEquals(true, MapValidation.checkEmptyContinents(d_GameMatrix));
    }


    /**
     * This method tests if duplicate Neighbors exist
     *
     * @throws ValidationFailure if validation fails
     */
    @Test
    public void checkIfMapDuplicateContinents() throws ValidationFailure {
        d_GameMatrix.addContinent("Europe", "5");
        assertEquals(true, MapValidation.checkDuplicateContinents(d_GameMatrix));
    }


    /**
     * It checks if duplicate countries exist
     *
     * @throws ValidationFailure if there is a validation failure
     */
    @Test
    public void checkIfMapDuplicateCountries() throws ValidationFailure {
        d_GameMatrix.addCountry("India", "Asia");
        assertEquals(true, MapValidation.checkDuplicateCountries(d_GameMatrix));
    }

    /**
     * This method tests if duplicate Neighbors exist
     *
     * @throws ValidationFailure if validation fails
     */
    @Test
    public void checkDuplicateNeighbours() throws ValidationFailure {
        d_GameMatrix.addNeighbor("England", "England");
        assertTrue(MapValidation.checkDuplicateNeighbours(d_GameMatrix));
    }

    /**
     * This method tests if continent the continents are connected
     *
     * @throws ValidationFailure if there is a validation failure
     */
    @Test
    public void checkIfContinentsConnection() throws ValidationFailure {
        assertTrue(MapValidation.checkIfContinentIsConnected(d_GameMatrix));
    }

    /**
     * It checks whether the map is connected
     */
    @Test
    public void checkConnectionOfMap() {

        assertTrue(MapValidation.checkIfMapIsConnected(d_GameMatrix));
    }


    /**
     * It validates if the map contains disconnected continents
     *
     * @throws ValidationFailure if there is a validation failure
     */

    @Test
    public void checkValidateMapDisconnectedContinent() throws ValidationFailure {
        d_GameMatrix.addContinent("Asia", "5");
        d_GameMatrix.addContinent("Europe", "5");
        d_GameMatrix.addCountry("India", "Asia");
        d_GameMatrix.addCountry("France", "Europe");

        boolean result = MapValidation.validateMap(d_GameMatrix, 2);
        assertFalse(result);
    }

    /**
     * It checks whether the map is disconnected graph
     *
     * @throws ValidationFailure if there is a validation failure
     */


    @Test
    public void checksValidateMapDisconnectedGraph() throws ValidationFailure {
        d_GameMatrix.addContinent("Asia", "5");
        d_GameMatrix.addCountry("India", "Asia");
        d_GameMatrix.addCountry("China", "Asia");
        d_GameMatrix.addCountry("Australia", "Asia"); // Not connected to India and China

        boolean result = MapValidation.validateMap(d_GameMatrix, 3);
        assertFalse(result);
    }

    /**
     * It checks the country count
     *
     * @throws ValidationFailure if there is a validation failure
     */
    @Test
    public void checkCountryCount() throws ValidationFailure {

        // Add some countries to the game map
        d_GameMatrix.addCountry("India", "Asia");
        d_GameMatrix.addCountry("Japan", "Asia");
        d_GameMatrix.addCountry("China", "Asia");
        // Test when the country count is greater than the minimum
        boolean result = MapValidation.checkCountOfCountry(d_GameMatrix, 2);
        assertTrue(result);
    }
}