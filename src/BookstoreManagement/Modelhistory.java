/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookstoreManagement;

import java.util.Date;

/**
 *
 * @author shado
 */
public class Modelhistory {
    private int order_id;
    private String title;
    private int quantity;
    private double price;
    private Date order_date;

    public Modelhistory(int order_id, String title, int quantity, double price, Date order_date) {
        this.order_id = order_id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.order_date = order_date;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Date getOrder_date() {
        return order_date;
    }

    
    
    
    
}
