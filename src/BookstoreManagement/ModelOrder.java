/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookstoreManagement;

/**
 *
 * @author ADMIN
 */
public class ModelOrder {
    private int order_id;
    private int user_id;
    private int cart_id;
    private String order_date;
    private String total_amount;

    public ModelOrder(int order_id, int user_id, int cart_id, String order_date, String total_amount) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.cart_id = cart_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getTotal_amount() {
        return total_amount;
    }
    

    

   
    
}
