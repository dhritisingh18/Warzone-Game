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
    GameCycle d_GameCycle = GameCycle.ExecuteOrder;
    GameMatrix d_GameGrid;

    /**
     *Default Constructor
     *
     */
    public OrderExecutionPhase(){
    	d_GameGrid = GameMatrix.getInstance();
    }
    /**
     * Method to start the game cycle
     * @param p_GameCycle the current game cycle
     * @return d_NextGameCycle will give next game cycle
     * @throws Exception if failed execution
     */
    @Override
    public GameCycle begin(GameCycle p_GameCycle) throws Exception {
    	d_GameCycle = p_GameCycle;
		System.out.println(PerformOrders() ? "Orders executed successfully." : "Order execution failed.");
        return p_GameCycle.nextState(d_NextGameCycle);
    }

    /**
     *Method to execute order in list
     * @return true on successful order execution
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