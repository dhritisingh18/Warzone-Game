package model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to manage player orders
 *
 *  * @author Dhriti Singh
 *  * @version 1.0.0
 *  */
public class Order {
    private static Order d_Order;
    private String d_Type;
    private OrderInfo d_OrderInfo;
    private List<Order> d_OrderList = new ArrayList<Order>();

    /**
     * A function to get list of orders
     *
     * @return the type of Order list class
     */
    public List<Order> getOrderList() {

        return d_OrderList;
    }

    /**
     * A function to set list of orders
     *
     * @param p_OrderList the type Order list class
     */
    public void setOrderList(List<Order> p_OrderList) {

        this.d_OrderList = p_OrderList;
    }

    /**
     * A function to append orders in order list
     *
     * @param p_Order The order to be appended
     */
    public void AddToOrderList(Order p_Order){

        d_OrderList.add(p_Order);
    }

    /**
     * A function to get class Order instance
     *
     * @return the class Order instance
     */
    public static Order getInstance() {
        if (Objects.isNull(d_Order)) {
            d_Order = new Order();
        }
        return d_Order;
    }
    /**
     * A function to get info of orders
     *
     * @return the info of orders in an object
     */
    public OrderInfo getOrderInfo() {

        return d_OrderInfo;
    }

    /**
     * A function to the set info of orders
     *
     * @param p_OrderInfo info of orders of type OrderInfo in an object
     */
    public void setOrderInfo(OrderInfo p_OrderInfo) {

        this.d_OrderInfo = p_OrderInfo;
    }

    /**
     * A function to return the order type
     *
     * @return String which indicates the order type
     */
    public String getType() {

        return d_Type;
    }

    /**
     * A function to set the order type
     *
     * @param p_Type String which indicates the order type
     */
    public void setType(String p_Type) {

        this.d_Type = p_Type;
    }

    /**
     * A function to be overridden by the Child class
     *
     * @return false due to absence of order to be executed
     */
    public boolean execute() {
        System.out.println("Void order execution failed");
        return false;
    }

}
