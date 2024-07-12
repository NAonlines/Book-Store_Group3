/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookstoreManagement;

import java.sql.Timestamp;


/**
 *
 * @author shado
 */
public class ModelBooks {
    private int book_id;
    private String bookimg;
    private String title;
    private String author;
    private String genre;
    private double price;
    private int stock;
    private Timestamp created_at;

    public ModelBooks(int book_id, String bookimg, String title, String author, String genre, double price, int stock, Timestamp created_at) {
        this.book_id = book_id;
        this.bookimg = bookimg;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
        this.created_at = created_at;
    }

    public int getBook_id() {
        return book_id;
    }

    public String getBookimg() {
        return bookimg;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    
    
//    set
    
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setBookimg(String bookimg) {
        this.bookimg = bookimg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
    
    
    
    @Override
    public String toString() {
        return this.title;
    }
    
    
}
