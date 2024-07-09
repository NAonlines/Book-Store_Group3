
package BookstoreManagement;

import java.util.Date;


public class paymentData {
    private final int payment_id;
    private final int order_id;
    private final Date payment_date;
    private final int total;
    private final String payment_method;

    public paymentData(int payment_id, int order_id, Date payment_date, int total, String payment_method) {
        this.payment_id = payment_id;
        this.order_id = order_id;
        this.payment_date = payment_date;
        this.total = total;
        this.payment_method = payment_method;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public int getTotal() {
        return total;
    }

    public String getPayment_method() {
        return payment_method;
    }

    Object paymentIdProperty() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
