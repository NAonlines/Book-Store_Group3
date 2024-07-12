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
    private int order_detail_id;
    private int order_id;
    private int cart_id;
    private int book_id;
    private int quantity;
    private double price;
    private int discount;
    private Date created_at;
    private String email;


    public Modelhistory(int order_detail_id, int order_id, int cart_id, int book_id, int quantity, double price, int discount, Date created_at, String email) {
        this.order_detail_id = order_detail_id;
        this.order_id = order_id;
        this.cart_id = cart_id;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.created_at = created_at;
        this.email = email;
    }

    public int getOrder_detail_id() {
        return order_detail_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public String getEmail() {
        return email;
    }

    
    
    
    
}
