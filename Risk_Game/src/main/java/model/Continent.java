import java.util.HashSet;
import java.util.Set;

/**
 * Class to set and get Continent and it's properties
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati
 * @author Ritika Dhamija
 * @version 1.0.0
 */
public class Continent {
    private String d_Id;
    private String d_ContinentName;
    private int d_ArmiesAwarded;
    private boolean d_ArmiesCredited;
    private Set<Country> d_Countries;

    /**
     * Method to get the continent id.
     * @return d_Id, the continent id.
     */
    public String getId() {
        return d_Id;
    }

    /**
     * Method to set the continent identifier.
     * @param p_Id, the continent id.
     */
    public void setId(String p_Id) {
        this.d_Id = p_Id;
    }

    /**
     * Method to get the name of the continent
     * @return d_Name, the continent's name.
     */
    public String getName() {
        return d_ContinentName;
    }

    /**
     * Method to set the name of the continent
     * @param p_name, name of the continent
     */
    public void setName(String p_name) {
        this.d_ContinentName = p_name;
    }

    /**
     * Method to get the Armies Awarded
     * @return d_AwardArmies, the Armies Awarded to the continent
     */
    public int getAwardArmies() {
        return d_ArmiesAwarded;
    }

    /**
     * Method to set the Armies Awarded to the continent
     * @param p_AwardArmies, Armies Awarded
     */
    public void setAwardArmies(int p_AwardArmies) {
        this.d_ArmiesAwarded = p_AwardArmies;
    }

    /**
     * Function to check if armies are credited
     * @return true if the armies are credited;
     */
    public boolean isCredited() {
        return d_ArmiesCredited;
    }

    /**
     * Method to count the number of armies credited
     * @param p_Credited Armies Credited
     */
    public void setCredited(boolean p_Credited) {
        this.d_ArmiesCredited = p_Credited;
    }

    /**
     * Method that returns countries in the continent.
     * @return set of countries
     */
    public Set<Country> getCountries() {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }
}
