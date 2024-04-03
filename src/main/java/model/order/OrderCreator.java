package model.order;

import model.Player;

/**
 * A class to create Orders in the game.
 *
 *  * @author Dhriti Singh
 *  * @version 1.0.0
 *  */
public class OrderCreator {
    /**
     * A function to create orders
     * @param p_cmd the command received
     * @param player the object parameter
     * @return l_Order, the order
     */
    public static Order orderCreation(String[] p_cmd, Player player){
        String l_CmdType = p_cmd[0].toLowerCase();
        Order l_Order;
        if ("deploy".equals(l_CmdType)) {
            l_Order = new OrderDeployment();
            l_Order.setOrderInfo(orderDeploymentInfo(p_cmd, player));
        } else {
            System.out.println("\nOrder Creation failed due to invalid arguments");
            l_Order = new Order();
        }
        return l_Order;
    }

    /**
     * A function to generate the information about Order Deployment
     * @param p_Cmd the command received
     * @param p_Player object parameter
     * @return l_OrderInfo, the order information
     */
    private static OrderInfo orderDeploymentInfo(String[] p_Cmd, Player p_Player) {
        String l_CountryID = p_Cmd[1];
        int l_ArmyCount = Integer.parseInt(p_Cmd[2]);

        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setLocation(l_CountryID);
        l_OrderInfo.setArmyCount(l_ArmyCount);
        return l_OrderInfo;
    }

}
