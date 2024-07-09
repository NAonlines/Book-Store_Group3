
package BookstoreManagement;

import java.util.Date;


public class customerData {
    private final Integer cart_id;
    private final Integer book_id;
    private final String title;
    private final Integer quantity;
    private final Double price;
    private final Date createat;

    public customerData(Integer cart_id, Integer book_id, String title, Integer quantity, Double price, Date createat) {
        this.cart_id = cart_id;
        this.book_id = book_id;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.createat = createat;
    }

    public Integer getCart_id() {
        return cart_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Date getCreateat() {
        return createat;
    }

    

    
}
