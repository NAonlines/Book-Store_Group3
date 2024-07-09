/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookstoreManagement;

/**
 *
 * @author ADMIN
 */
public class ModelOrderdetail {
    private int order_detail_id;
    private int order_id;
    private int book_id;
    private int quantity;
    private double price;

    public ModelOrderdetail(int order_detail_id, int order_id, int book_id, int quantity, double price) {
        this.order_detail_id = order_detail_id;
        this.order_id = order_id;
        this.book_id = book_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrder_detail_id() {
        return order_detail_id;
    }

    public int getOrder_id() {
        return order_id;
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
}
