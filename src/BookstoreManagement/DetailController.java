package BookstoreManagement;

import Li_Alert.Alert;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 */
public class DetailController implements Initializable {
    @FXML
    private TextField txtidorder;
    @FXML
    private TextField txtcartid;
    @FXML
    private TextField txtcreated;
    @FXML
    private TextField txtdiscount;
    @FXML
    private TextField txtemailor;
    @FXML
    private TextField txtidbook;
    @FXML
    private TextField txtidorderdetail;
    @FXML
    private TextField txtprice;
    @FXML
    private TextField txtquantily;

    private Connection conn; 
    private PreparedStatement pst; 
    private ResultSet rs; 
    private Alert alert = new Alert();

    public void setOrderid(Integer getOrderid) {
        txtidorder.setText(getOrderid.toString());
        selectOrderDetail();
    }

    public void selectOrderDetail() {
        String sql = "SELECT * FROM OrderDetails WHERE order_id = ?";
        try {
            if (conn == null) {
                alert.showAlert("Database connection is not initialized.");
                return;
            }
            pst = conn.prepareStatement(sql);
            pst.setString(1,  txtidorder.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtidorderdetail.setText(Integer.toString(rs.getInt("order_detail_id")));
                txtidorder.setText(Integer.toString(rs.getInt("order_id")));
                txtcartid.setText(Integer.toString(rs.getInt("cart_id")));
                txtidbook.setText(Integer.toString(rs.getInt("book_id")));
                txtdiscount.setText(Integer.toString(rs.getInt("discount")));
                txtemailor.setText(rs.getString("email"));
                txtcreated.setText(rs.getDate("created_at").toString());
                txtprice.setText(Double.toString(rs.getDouble("price")));
                txtquantily.setText(Integer.toString(rs.getInt("quantity")));
                System.out.println("Order details loaded successfully."
                        + "");
            } else {
                alert.showAlert("No order details found for the given order ID");
                System.out.println("No order details found for order ID: " + txtidorder.getText());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConnectDB.ConnectDB.getConnectDB();
        if (conn == null) {
            alert.showAlert("Connection to server failed");
            System.out.println("Database connection failed.");
            return;
        }
        System.out.println("Database connection successful.");
    }       
}
