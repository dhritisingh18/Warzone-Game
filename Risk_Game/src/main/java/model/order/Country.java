package model.order;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to set and get country and it's properties
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati
 * @author Ritika Dhamija
 * @version 1.0.0
 */
public class Country {
    private String d_Id;
    private String d_CountryName;
    private String d_ContinentName;
    private Player d_Player;
    private int d_Armies;
    private Set<Country> d_Neighbors;
    private Set<String> d_NeighborsName;

    public String getId() {
        return d_Id;
    }

    public void setId(String d_Id) {
        this.d_Id = d_Id;
    }

    public String getName() {
        return d_CountryName;
    }

    public void setName(String d_CountryName) {
        this.d_CountryName = d_CountryName;
    }

    public String getContinent() {
        return d_ContinentName;
    }

    public void setContinent(String d_ContinentName) {
        this.d_ContinentName = d_ContinentName;
    }

    public Player getPlayer() {
        return d_Player;
    }

    public void setPlayer(Player d_Player) {
        this.d_Player = d_Player;
    }

    public int getArmies() {
        return d_Armies;
    }

    public void setArmies(int d_Armies) {
        this.d_Armies = d_Armies;
    }

    public Set<Country> getNeighbors() {
        return d_Neighbors;
    }

    public void setNeighbors(Set<Country> d_Neighbors) {
        this.d_Neighbors = d_Neighbors;
    }

    public Set<String> getNeighborsName() {
        return d_NeighborsName;
    }

    public void setNeighborsName(Set<String> d_NeighborsName) {
        this.d_NeighborsName = d_NeighborsName;
    }

    public void deployArmies(int p_armies) {

        d_Armies += p_armies;
    }




    public void setNeighborsName(String p_NeighborCountryName) {
        if (d_NeighborsName == null) {
            d_NeighborsName = new HashSet<>();
        }
        d_NeighborsName.add(p_NeighborCountryName);
    }


    /**
     * Method to eliminate a neighbor from a country's list of neighboring countries.
     * @param p_NeighborCountryName neighbour name that would be eliminated
     */
    public void removeNeighborsName(String p_NeighborCountryName) {
        if (d_NeighborsName == null) {
            d_NeighborsName = new HashSet<>();
        }
        d_NeighborsName.remove(p_NeighborCountryName);
    }

    /**
     * Method that stores neighboring countries in a list
     * @param p_Neighbors in set, neighbors 
     * @return in string, neighbors 
     */
    
    public String createANeighborList(Set<Country> p_Neighbors){
        String result = "";
        for (Country l_Neighbor : p_Neighbors ){
            result += l_Neighbor.getName() + "-";
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";
    }
}
