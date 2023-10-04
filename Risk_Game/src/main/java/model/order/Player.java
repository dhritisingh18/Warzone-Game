package model.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to set and get Player details
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati
 * @author Ritika Dhamija
 * @version 1.0.0
 */
public class Player {
    private int d_Id;
    private String d_PlayerName;
    private List<Country> d_CountriesCaptured = new ArrayList<>();
    private int d_ReinforcementArmies;
    


    /**
     * Method to get the player ID
     * @return  d_ID, player id
     */
    
    public int getId() {
        return d_Id;
    }

    /**
     * Method to set player ID
     * @param p_Id, player ID
     */
    
    public void setId(int p_Id) {

        this.d_Id = p_Id;
    }

    /**
     * Method to get player name
     * @return d_PlayerName name of player
     */
    
    public String getName() {

        return d_PlayerName;
    }

    /**
     * Method to set player name
     * @param p_PlayerName player name
     */
    
    public void setName(String p_PlayerName) {
        this.d_PlayerName = p_PlayerName;
    }

    /**
     * Method to get list of captured countries
     * @return d_CountriesCaptured captured countries list
     */
    
    public List<Country> getCapturedCountries() {
        return d_CountriesCaptured;
    }

    /**
     * Method to get the captured countries
     * @param p_CapturedCountries, captured countries list
     */
    
    public void setCapturedCountries(List<Country> p_CapturedCountries) {
        this.d_CountriesCaptured = p_CapturedCountries;
    }


    

    /**
     *  Method to retrieve the reinforcement armies allocated to each player.
     * @return Int type assignment of armies to a player.
     */
    
    public int getReinforcementArmies() {
        return d_ReinforcementArmies;
    }

    /**
     * Method to set reinforcement armies for each player.
     * @param p_AssignedArmies number of armies assigned to player
     */
    
    public void setReinforcementArmies(int p_AssignedArmies) {
        this.d_ReinforcementArmies = p_AssignedArmies;
    }


    /**
     * Method to check if the country exists in the country list of the player
     * @param p_Country To check if the country exists or not
     * @param p_Player The name of player for whom country is to be checked
     * @return true if country is existing in the country list
     */

    public boolean checkIfCountryExistsInCapturedCountries(String p_Country, Player p_Player) {
        for (Country l_Country : p_Player.getCapturedCountries()) {
            if (l_Country.getName().equals(p_Country)) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method checks whether the army to be deployed is valid or not.
     * @param p_ArmyTotal The no of armies to be deployed by the country
     * @return true if the number of armies are valid and deducted from the reinforcement army else false
     */
    public boolean deployArmiesForPlayer(int p_ArmyTotal) {
        if (p_ArmyTotal < 0 || p_ArmyTotal > d_ReinforcementArmies) {
            return false;
        }
        d_ReinforcementArmies -= p_ArmyTotal;
        return true;
    }


    /**
     *  A method  to create a list of countries assigned to player
     * @param p_Captured The list of countries given to the player
     * @return string
     */
    public String buildCapturedCountriesList(List<Country> p_Captured) {
        StringBuilder sb = new StringBuilder();
        for (Country captured : p_Captured) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(captured.getName());
        }
        return sb.toString();
    }

}

