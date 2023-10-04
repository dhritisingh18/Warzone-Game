package model.order;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
    private Deque<Order> d_Orders = new ArrayDeque<>();
    private int d_ReinforcementArmies;

    public static List<Order> OrderList = new ArrayList<>();

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
     * Get orders
     *
     * @return list of orders
     */
    public Deque<Order> getOrders() {
        return d_Orders;
    }

    /**
     *  set the orders
     *
     * @param p_Orders the list of orders
     */
    private void setOrders(Deque<Order> p_Orders) {
        this.d_Orders = p_Orders;
    }

    /**
     * A function to add the orders to the issue order list
     *
     * @param p_Order The order which is to be added
     */
    private void addOrder(Order p_Order) {
        d_Orders.add(p_Order);
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
     * A function to get the issue order from player and add to the order list
     *
     * @param p_Commands the type of order issued
     */
    public void issueOrder(String p_Commands) {
        String[] l_CommandArr = p_Commands.split(" ");

        String countryName = l_CommandArr[1];
        int reinforcementArmies = Integer.parseInt(l_CommandArr[2]);

        boolean isValidOrder = validateOrder(countryName, reinforcementArmies);

        if (isValidOrder) {
            Order order = OrderCreator.OrderCreation(l_CommandArr, this);
            OrderList.add(order);
            addOrder(order);
            System.out.println("Order has been added to" + order.getOrderInfo().getLocation() + " with " + order.getOrderInfo().getNumberOfArmy() + " armies");
            System.out.println("**********************************************************************************************");
        }
    }

    private boolean validateOrder(String countryName, int reinforcementArmies) {
        if (!checkIfCountryExists(countryName, this)) {
            System.out.println("The country does not belong to you");
            return false;
        }

        if (!deployArmiesForPlayer(reinforcementArmies)) {
            System.out.println("You do not have enough Reinforcement Armies to deploy.");
            return false;
        }

        return true;
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

