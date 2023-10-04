package model.order;

import model.Country;
import model.Player;

/**
 * Class OrderDeployment used to execute the orders, which is a child of Order
 *
 *  * @author Mohammad Ehtesham Arif
 *  * @author Dhriti Singh
 *  * @author Rabia Tahir
 *  * @author Simran Simran
 *  * @author Ritik Gulati
 *  * @author Ritika Dhamija
 *  * @version 1.0.0
 *  */
public class OrderDeployment extends Order {
    /**
     * Constructor for class OrderDeployment
     */
    public OrderDeployment() {
        super();
        setType("deploy");
    }
    /**
     * Overriding function for the order
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        if (getOrderInfo().getPlayer() == null || getOrderInfo().getLocation() == null) {
            System.out.println("Deploy order execution failed: Invalid order info.");
            return false;
        }
        Player l_Player = getOrderInfo().getPlayer();
        String l_Location = getOrderInfo().getLocation();
        int l_ArmiesToDeploy = getOrderInfo().getArmyCount();
        for(Country l_Country : l_Player.getCapturedCountries()){
            if(l_Country.getName().equals(l_Location)){
                l_Country.deployArmies(l_ArmiesToDeploy);
                System.out.println("The country " + l_Country.getName() + " deployed with " + l_Country.getArmies() + " armies.");
            }
        }
        System.out.println("\nCompleted Execution: deployed " + l_ArmiesToDeploy + " armies to " + l_Location + ".");
        System.out.println("----------------------------------------------------------------------------------------");
        return true;
    }

}
