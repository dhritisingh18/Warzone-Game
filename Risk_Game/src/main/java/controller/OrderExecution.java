package controller;

import model.GameHandler;
import model.GameMatrix;
import model.GameCycle;
import model.order.Order;
import static model.Player.OrderList;

/**
 * This is a class which contains the Execute Order phase
 *
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati 
 * @author Ritika Dhamija
 * @version 1.0.0
 */
public class OrderExecution implements GameHandler {
    GameCycle d_NextGameCycle = GameCycle.Reinforcement;
    GameCycle d_GameCycle = GameCycle.OrderExecution;
    GameMatrix d_GameGrid;

    /**
     *Default Constructor
     *
     */
    public void OrderExecutionPhase(){
    	d_GameGrid = GameMatrix.getInstance();
    }
  
    /**
     * This method starts the current game phase
     *
     * @param p_GameCycle the current game phase
     * @return d_NextGameCycle gives the next game phase
     * @throws Exception when execution fails
     */
    @Override
    public GameCycle begin(GameCycle p_GameCycle) throws Exception {
    	d_GameCycle = p_GameCycle;
		System.out.println(PerformOrders() ? "Orders executed successfully." : "Order execution failed.");
        return p_GameCycle.nextState(d_NextGameCycle);
    }

    /**
     * This method executes each order in the order list
     * @return true if execution is successful
     */
    private boolean PerformOrders()
    {
        for (Order l_Order : OrderList){
            if(!l_Order.execute()){
                return false;
            }
        }
        return true;
    }
}