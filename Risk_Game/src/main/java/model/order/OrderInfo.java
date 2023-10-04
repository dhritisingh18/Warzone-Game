package model.order;

import model.Country;
import model.Player;

/**
 * A class with the information of Order details
 *  * @author Mohammad Ehtesham Arif
 *  * @author Dhriti Singh
 *  * @author Rabia Tahir
 *  * @author Simran Simran
 *  * @author Ritik Gulati
 *  * @author Ritika Dhamija
 *  * @version 1.0.0
 *  */
public class OrderInfo {

    private Player d_Player;
    private Country d_Departed;
    private String d_Location;
    private int d_ArmyCount;

    /**
     * A function to get information of player
     * @return the player object
     */
    public Player getPlayer() {

        return d_Player;
    }

    /**
     * A function to set information of player
     * @param d_Player the player object
     */
    public void setPlayer(Player d_Player) {

        this.d_Player = d_Player;
    }

    /**
     * A function to get the armies departed from order
     * @return  departed country object
     */
    public Country getDeparted() {

        return d_Departed;
    }

    /**
     * A function to set the armies departed from order
     * @param d_Departure departed country object
     */
    public void setDeparted(Country d_Departure) {

        this.d_Departed = d_Departure;
    }

    /**
     * A function to get final Location of armies
     * @return the final location of armies
     */
    public String getLocation() {

        return d_Location;
    }

    /**
     * A function to set final Location of armies
     * @param d_Location the final location of armies
     */
    public void setLocation(String d_Location) {

        this.d_Location = d_Location;
    }

    /**
     * A function to get army count in the order
     * @return count of armies
     */
    public int getArmyCount() {

        return d_ArmyCount;
    }

    /**
     * A function to set army count in the order
     * @param d_ArmyCount count of armies
     */
    public void setArmyCount(int d_ArmyCount) {

        this.d_ArmyCount = d_ArmyCount;
    }

}
