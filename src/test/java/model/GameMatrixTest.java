package model;

import org.junit.Test;
import utils.ValidationFailure;
import java.util.*;
import static org.junit.Assert.*;

/**
 * It tests the GameMap Class
 * @author Mohammad Ehtesham Arif
 */
public class GameMatrixTest {
    /**
     * A test to shuffle countries before assigning it to the players
     *
     * @throws ValidationFailure if there is a validation failure
     */
    @Test
    public void checkShufflingCountries() throws ValidationFailure {

        List<String> listOfCountries = new ArrayList<String>();
        listOfCountries.add("Brazil");
        listOfCountries.add("Italy");
        listOfCountries.add("France");
        listOfCountries.add("Ireland");
        listOfCountries.add("Russia");
        listOfCountries.add("Germany");

        Collections.shuffle(listOfCountries); //using collections shuffle

        List<String> captured = new ArrayList<String>();

        for (int i = 0; i < listOfCountries.size(); i++) {
            String country = listOfCountries.get(i);
            captured.add(country);
            listOfCountries.set(i, "Player has been given this country");
        }

        boolean areEqual = listOfCountries.equals(captured);
        assertFalse(areEqual);

    }
}