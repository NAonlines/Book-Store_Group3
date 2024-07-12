package BookstoreManagement;

import Li_Alert.Alert;
import Li_Encrypt.KeyManager;
import Li_Encrypt.LibaryEncrypt;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.crypto.SecretKey;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class UserMainController implements Initializable {
    @FXML
    private AnchorPane BOOKS,  DISCOUNT, ORDERS, PAYMENT1, SHOPPINGCART1, PROFILE,CHANGEPASSWORD,EDITPROFILEUSER,PAYMENTHISTORY;
    @FXML
    private Button btn_close;
    private Button btn_members;
    @FXML
    private Button  btn_minimize, btn_orders, btn_payment, btn_shoppingcart,btn_homebook;
    @FXML
    private MenuItem btn_logoutmenu,btn_paymentmenu;
    @FXML
    private SplitMenuButton btn_edittprofile;
    @FXML
    private AnchorPane fMain,pane_imgprofile;
    @FXML
    private Label name_user,label_pathprofile ;

//----------------------NA--------------------------------------------------------
    @FXML
    private TableColumn<Modelhistory,String> cl_pfbookname;

    
    
//---------------------    
    
    
    @FXML
    private DatePicker date_profileuser;

    @FXML
    private ImageView btn_hidenpass,btn_showpass,img_profileuser,avatar_user;
    @FXML
    private TableView<Modelmember> tb_paymenthistory;
    @FXML
    private TextField profile_username,profile_oldpassword,profile_newpassword,profile_comfirmpassword,profile_email,profile_oldpassword_visible,profile_changpassworduser;
    @FXML
    private Button btn_profileupdate,btn_editprofileuser,btn_changepassword,btn_logouteditprofile,btn_profileupdatepassword,btn_profilechangeimg,btn_profilehistory;
    
    @FXML
    private CheckBox profile_female,profile_male;
    private String[] sgender = {"Male", "Female"};
    private String[] srole = {"User", "Admin"};
    @FXML
    private AnchorPane PAYMENT;
    @FXML
    private AnchorPane SHOPPINGCART;
    @FXML
    private TableColumn<?, ?> cl_pfdate;
    @FXML
    private TableColumn<?, ?> cl_pfprice;
    @FXML
    private TableColumn<?, ?> cl_pfquantity;
    @FXML
    private TableColumn<?, ?> cl_pftotal;
    @FXML
    private TextField txt_firstname;
    



    public void setUsername(String Username){
        name_user.setText("Hello, "+Username);

    }
    
    
    @FXML
    public void logout() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
        fMain.getScene().getWindow().hide();
        clear();
        clearOldData();
    
    }
//    ------------------------------------------------------------
    
    private Alert alert = new Alert();
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    LibaryEncrypt CryptoUtils = new LibaryEncrypt();
    private SecretKey secretKey;
    private CallableStatement stmt;

    
    
    
    @FXML
    void handle_close(ActionEvent event) {
        closeWindow();
        
        clear();
        clearOldData();
    }

    @FXML
    void handle_minimize(ActionEvent event) {
        Stage stage = (Stage) fMain.getScene().getWindow();
        stage.setIconified(true);
    }

    void handlesplitmenu(ActionEvent event) {
        SplitMenuButton splitMenuButton = new SplitMenuButton();
        splitMenuButton.setText("Options");

        MenuItem item1 = new MenuItem("Option 1");
        MenuItem item2 = new MenuItem("Option 2");
        MenuItem item3 = new MenuItem("Option 3");

        splitMenuButton.getItems().addAll(item1, item2, item3);

        StackPane root = new StackPane(splitMenuButton);
        Scene scene = new Scene(root, 300, 200);
        
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }


    
    
    @FXML
    public void swtichForm(ActionEvent event) {
        if (event.getSource() == btn_homebook) {
            BOOKS.setVisible(true);
            ORDERS.setVisible(false);
            PAYMENT1.setVisible(false);
            DISCOUNT.setVisible(false);
            SHOPPINGCART1.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_orders) {
            BOOKS.setVisible(false);
            ORDERS.setVisible(true);
            PAYMENT1.setVisible(false);
            DISCOUNT.setVisible(false);
            SHOPPINGCART1.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_payment) {
            BOOKS.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT1.setVisible(true);
            DISCOUNT.setVisible(false);

            SHOPPINGCART1.setVisible(true);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_members) {
            BOOKS.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT1.setVisible(false);
            DISCOUNT.setVisible(false);
            SHOPPINGCART1.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_shoppingcart) {
            BOOKS.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT1.setVisible(false);
            DISCOUNT.setVisible(false);
            SHOPPINGCART1.setVisible(true);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_edittprofile) {
            BOOKS.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT1.setVisible(false);
            DISCOUNT.setVisible(false);
            SHOPPINGCART1.setVisible(false);
            PROFILE.setVisible(true);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) fMain.getScene().getWindow();
        stage.close();
    }

    private double xOffset = 0;
    private double yOffset = 0;
    
//   =-----------------------------------------------------------------------------------------------------= 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            if (KeyManager.isKeyExist()) {
                secretKey = KeyManager.loadKey();
            } else {
                secretKey = KeyManager.generateKey();
                KeyManager.saveKey(secretKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
        
        
        conn = ConnectDB.ConnectDB.getConnectDB();
        if (conn == null) {
            alert.showAlert("Connect to server failed");
            return;
        }
        
        btn_hidenpass.setVisible(false);
        profile_oldpassword_visible.setVisible(false);
        profile_oldpassword.textProperty().bindBidirectional(profile_oldpassword_visible.textProperty()); 
        
        menuDisplayTotal();
        purchaseBookInfo();
        purchaseBookTitle();
        purchaseBookId();
        purchaseDisplayQuantity();
        menuDisplayBooks();
    }
    
//   =-----------------------------------------------------------------------------------------------------= 
    
    
    
//    -----------------------------------------NA------------------------------------------------------
    
   private boolean ValidPassword(String password) {
        return password.length() > 5 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    private boolean ValidEmail(String email) {
        return email.endsWith("@gmail.com") || email.endsWith("@hotmail.com");
    }    


    
//  PROFILE
    private String lastUsedDirectory = null;

    public void setProfileImage(String ImageProfile) {
       
            String picture ="file:" +  ImageProfile;
            Image image = new Image(picture, 110, 110, false, true);
            img_profileuser.setImage(image);
            avatar_user.setImage(image);
            String path =ImageProfile;
            label_pathprofile.setText(path);
        
    }
    
    public void setGenderprofile(String Genderprofile){
        if (Genderprofile.equalsIgnoreCase("Male")) {
        profile_male.setSelected(true);
        profile_female.setSelected(false);
    } else if (Genderprofile.equalsIgnoreCase("Female")) {
        profile_male.setSelected(false);
        profile_female.setSelected(true);
    } else {
        profile_male.setSelected(false);
        profile_female.setSelected(false);
    }
    
    
    }
      
    @FXML
    public void insertImageprofile(){
        FileChooser open = new FileChooser();
        if (lastUsedDirectory != null) {
            File initialDirectory  = new File(lastUsedDirectory);
            if (initialDirectory.exists()) {
                open.setInitialDirectory(initialDirectory);
            }
        }
        Stage stage = (Stage)pane_imgprofile.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if(file != null){
            String path = file.getAbsolutePath();
            lastUsedDirectory = file.getParent();
            path = path.replace("\\", "\\\\");
            label_pathprofile.setText(path);
            Image image = new Image(file.toURI().toString(), 110, 110, false, true);
            img_profileuser.setImage(image);

        }else{
               
        }
       
    }
    
    
    
    
    
    private boolean PasswordVisible = false;

    
    @FXML
        void handle_showpass(MouseEvent event) {
             PasswordVisible = !PasswordVisible;
            if (PasswordVisible) {
                profile_oldpassword_visible.setText(profile_oldpassword.getText());
                profile_oldpassword_visible.setVisible(true);
                profile_oldpassword.setVisible(false);
                btn_showpass.setVisible(false);
                btn_hidenpass.setVisible(true);
            } else {
                profile_oldpassword.setText(profile_oldpassword_visible.getText());
                profile_oldpassword.setVisible(true);
                profile_oldpassword_visible.setVisible(false);
                btn_showpass.setVisible(true);
                btn_hidenpass.setVisible(false);
            }
        }
    
    @FXML
    void swtichFormProfile(ActionEvent event) {
       
        if (event.getSource() == btn_editprofileuser) {
            EDITPROFILEUSER.setVisible(true);
            CHANGEPASSWORD.setVisible(false);
            PAYMENTHISTORY.setVisible(false);
           
        }else if(event.getSource() == btn_changepassword){
            EDITPROFILEUSER.setVisible(false);
            CHANGEPASSWORD.setVisible(true);
            PAYMENTHISTORY.setVisible(false);

        }else if(event.getSource() == btn_profilehistory){
            EDITPROFILEUSER.setVisible(false);
            CHANGEPASSWORD.setVisible(false);
            PAYMENTHISTORY.setVisible(true);
        
        }

    }

  
    
    @FXML
    void ck_profilegender(ActionEvent event) {
        if(event.getSource()==profile_male){
            if(profile_male.isSelected()){
                profile_female.setSelected(false);
            }
        }else if(event.getSource()==profile_female){
            if(profile_female.isSelected()){
                profile_male.setSelected(false);
            }
        }
    }
    
    private String Profile_Gender() {
        if (profile_male.isSelected()) {
            return "Male";
        } else if (profile_female.isSelected()) {
            return "Female";
        } else {
            return null;
        }
    }
     
    public void ProfileUsername(String P_Username){
        profile_username.setText(P_Username);
        profile_changpassworduser.setText(P_Username);

    }   
    public void ProfileEmail(String P_Email){
        profile_email.setText(P_Email);
    }
    
        public void ProfileBirthday(Date Birthday){
            
            Date utilDate = new Date(Birthday.getTime());
            LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            date_profileuser.setValue(localDate);
        }
     
   @FXML
void handle_updateprofileuser(ActionEvent event) throws Exception {
    if (profile_email.getText().isEmpty() || date_profileuser.getValue() == null || Profile_Gender() == null) {
        alert.showAlert("Please input full information");
        return;
    } else {
        try {
            if (!ValidEmail(profile_email.getText())) {
                alert.showAlert("Emails must end with @gmail.com or @hotmail.com");
                return;
            }

            String sql = "UPDATE Users SET email = ?, birthday = ?, gender = ?, image = ? WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, profile_email.getText());
            pstmt.setDate(2, java.sql.Date.valueOf(date_profileuser.getValue()));
            pstmt.setString(3, Profile_Gender());
            pstmt.setString(4, label_pathprofile.getText());
            pstmt.setString(5, profile_username.getText());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                alert.showAlert("Update profile successfully.");
            } else {
                alert.showAlert("Update failed");
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Email already exists.")) {
                alert.showAlert("Email already exists.");
            } else if (e.getMessage().contains("Gender is required.")) {
                alert.showAlert("Gender is required.");
            } else if (e.getMessage().contains("Year of birth must be from 1920 to 2020.")) {
                alert.showAlert("Year of birth must be from 1920 to 2020.");
            } else {
                alert.showAlert("Failed");
                System.out.print("Error closing statement: " + e.getMessage());
            }
        }
    }
    }
    
    
    @FXML
    void handle_changepassword(ActionEvent event) {
        
        if(profile_oldpassword.getText().isEmpty() 
           || profile_newpassword.getText().isEmpty() 
           || profile_comfirmpassword.getText().isEmpty()){
        
        
            alert.showAlert("Please enter all fields.");
           
        
        }else{
            try {
                
                
                String check = "SELECT * FROM Users WHERE username = ?";
                    pstmt = conn.prepareStatement(check);
                    pstmt.setString(1,profile_changpassworduser.getText());
                    rs =  pstmt.executeQuery();
                
                if(rs.next()){
                    
                    byte[] EncryptedPassword = rs.getBytes("password");
                    String decryptedPassword = CryptoUtils.decrypt(EncryptedPassword, secretKey);

                    if (!ValidPassword(profile_newpassword.getText())) {
                    alert.showAlert("Password must be longer than 5 characters, contain at least one capital letter and one number.");
                    return;
                    }
                    if (!profile_newpassword.getText().equals(profile_comfirmpassword.getText())) {
                    alert.showAlert("Confirm password does not match.");
                    return;
                    }
                    if(decryptedPassword.equals(profile_newpassword.getText())){
                    alert.showAlert("The new password must not be the same as the old password.");
                    return;

                    }
                    if(decryptedPassword.equals(profile_oldpassword.getText())){

                        byte[] encryptedPassword = CryptoUtils.encrypt(profile_newpassword.getText(), secretKey);
                        String sql  = "UPDATE Users set password = ? WHERE username = ? " ;
                        pstmt = conn.prepareStatement(sql);                        
                        pstmt.setBytes(1,encryptedPassword );
                        pstmt.setString(2, profile_changpassworduser.getText());
                        
                        int rows = pstmt.executeUpdate();
                        
                        if(rows > 0){
                            alert.showAlert("Change password Successfully.");

                        }
                    }else{
                        alert.showAlert("Old password not match.");
                    }
                }
                
                
            } catch (Exception e) {
                System.out.println("Errors: "+ e.getMessage());
            }
        }

    }
    
//    ----------------------------------------------------------------------------------------------------------------------
    
//   NGHỊA
    
    
    @FXML
 public void btnpayment(){
        PAYMENT1.setVisible(true);
        SHOPPINGCART1.setVisible(true);
        BOOKS.setVisible(false);
        
 }
 
 
     // *****************Kết nối tableview với customerData.java***************///

    // ***************** Nghia********************//
    @FXML
    private TableView<customerData> menu_tableview;
    @FXML
    private TableColumn<customerData, String> menu_tb_title;
    @FXML
    private TableColumn<customerData, Integer> menu_tb_quantity;
    @FXML
    private TableColumn<customerData, Double> menu_tb_price;
    @FXML
    private TableColumn<customerData, Integer> menu_tb_book_id;
    @FXML
    private TableColumn<customerData, Date> menu_tb_createat;
    @FXML
    private Button menu_removebtn1;
    @FXML
    private AnchorPane cardcheck;
    @FXML
    private Label paycard;
    
    @FXML
    private TextField txt_lastname;
    @FXML
    private TextField txt_cardnumber;
    @FXML
    private DatePicker txt_date;
    @FXML
    private TextField txt_cvc;
    @FXML
    private TextField txt_zipcode;
    @FXML
    private Button menu_paybtn1;
    @FXML
    private Button btnexit1;
    
    
    
        @FXML
    private ComboBox<Integer> purchase_bookID;
    @FXML
    private ComboBox<String> purchase_booktitle;
    @FXML
    private Button btnadd;
    @FXML
    private Label pruchase_info_bookID;
    @FXML
    private Label pruchase_info_booktitle;
    @FXML
    private Label pruchase_info_author;
    @FXML
    private Label pruchase_info_genre;
    @FXML
    private Label pruchase_info_createat;
    @FXML
    private Label pruchase_info_price;
    @FXML
    private Label pruchase_info_discount;
    @FXML
    private Label pruchase_info_email;
    @FXML
    private Label pruchase_info_orderid;
    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private AnchorPane Paymentmethod,shoppingcart_menu;
    @FXML
    private Button btncash;
    @FXML
    private Button btncard;
    @FXML
    private Button btnexit;
    @FXML
    private Label menu_total;
    @FXML
    private Button menu_paybtn;
    @FXML
    private Button menu_removebtn;

// *****************Các nút chức năng ***************///

    // ***************** Nghia********************//
    private void handlePaidButton(ActionEvent event) {
        try {

            conn.setAutoCommit(false);

            clearOldData();

            conn.commit();

            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Payment Successful");
            alert.setHeaderText(null);
            alert.setContentText("Payment successful!");
            alert.setOnCloseRequest(evt -> {
                Paymentmethod.setVisible(false);
            });
            alert.showAndWait();
            clear();

        } catch (SQLException e) {

        }
    }

    @FXML
    private void handlePayButton(ActionEvent event) {

        Paymentmethod.setVisible(true);
    }

    @FXML
    private void handleExitbutton(ActionEvent event) {
        Paymentmethod.setVisible(false);

    }

    @FXML
    private void handleExitbutton2(ActionEvent event) {
        shoppingcart_menu.setVisible(true);
        cardcheck.setVisible(false);

    }
    private boolean isAlertShowing = false;
    private int paymentId;
    private int order_id;

    @FXML
    private void handlCardButton(ActionEvent even) {

        Paymentmethod.setVisible(false);
        cardcheck.setVisible(true);
        shoppingcart_menu.setVisible(false);
    }
    double discount = 0.0;
    String paymentMethod = "Card";
@FXML
private void handleCashButton(ActionEvent event) throws JRException {
    menuGetTotal();

    int order_id = 0;
    int userId = 0;
    int cartId = 0;

    // Query to retrieve data from dbo.ShoppingCart
    String selectCartData = "SELECT user_id, cart_id FROM dbo.ShoppingCart ";

    // Replace with your actual cart ID or logic to retrieve cart data
    int cartIdFromCartTable = 0; // Retrieve or set the correct cart ID from ShoppingCart

    try (Connection conn = ConnectDB.ConnectDB.getConnectDB();
         PreparedStatement selectStmt = conn.prepareStatement(selectCartData)) {

        // Execute query to retrieve user_id and cart_id
        try (ResultSet rs = selectStmt.executeQuery()) {
            if (rs.next()) {
                userId = rs.getInt("user_id");
                cartId = rs.getInt("cart_id");
            } else {
                throw new SQLException("No matching cart found in dbo.ShoppingCart.");
            }
        }

        // Insert a new order into the Orders table and retrieve the generated order_id
        String insertOrder = "INSERT INTO dbo.Orders (user_id, cart_id,total_amount) VALUES (?, ?,?);";
        try (PreparedStatement prepareOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
            prepareOrder.setInt(1, userId); // Set user_id
            prepareOrder.setInt(2, cartId); // Set cart_id
             prepareOrder.setDouble(3, totalP); // Set total_amount
            prepareOrder.executeUpdate();

            // Retrieve the generated order_id
            try (ResultSet generatedKeys = prepareOrder.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order_id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to obtain order_id.");
                }
            }
        }

        // Insert payment details into the Payments table
        String insertPay = "INSERT INTO dbo.Payments (order_id, total, payment_date, payment_method) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparePay = conn.prepareStatement(insertPay)) {
            preparePay.setInt(1, order_id); // Use order_id for Payments table
            preparePay.setDouble(2, totalP);
            preparePay.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

            if (paymentMethod == null || paymentMethod.isEmpty()) {
                throw new IllegalArgumentException("Payment method not selected");
            }
            preparePay.setString(4, paymentMethod);

            preparePay.executeUpdate();

            pruchase_info_orderid.setText(String.valueOf(order_id));
            clear();
            menuGetTotal();
            clearOldData();

            showSuccessAlert();
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        showErrorAlert("Database error occurred: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        showErrorAlert(e.getMessage());
    }
}





private int getLatestOrderId() {
    int orderId = 0;

    try {
        String sql = "SELECT MAX(order_id) FROM dbo.Orders";
        try (Connection conn = ConnectDB.ConnectDB.getConnectDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return orderId;
}





    private int generateValidOrderId() {
        int orderId = 0;

        try {
            String sql = "SELECT MAX(book_id) FROM dbo.Books"; 
            try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    orderId = rs.getInt(1) + 1; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    private boolean isOrderIdValid(int orderId, Connection conn) throws SQLException {
        
        String sql = "SELECT 1 FROM dbo.Orders WHERE order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); 
            }
        }
    }

    private void showSuccessAlert() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Successful");
        alert.setHeaderText(null);
        alert.setContentText("Payment successful!");
        alert.setOnCloseRequest(evt -> {
            isAlertShowing = false;
            Paymentmethod.setVisible(false);
        });
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Payment Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // *****************Clear tất cả  ***************///
    // ***************** Nghia********************//
    private void clear() {
        pruchase_info_orderid.setText("");
        pruchase_info_booktitle.setText("");
        pruchase_info_author.setText("");
        pruchase_info_genre.setText("");
        pruchase_info_price.setText("");
        pruchase_info_discount.setText("");
        pruchase_info_bookID.setText("");
        pruchase_info_createat.setText("");
        purchase_booktitle.getSelectionModel().clearSelection();
        purchase_bookID.getSelectionModel().clearSelection();
        purchase_quantity.getValueFactory().setValue(0);
        menu_tableview.getItems().clear();
        menu_total.setText("$0.00");
        pruchase_info_email.setText("");
        txt_firstname.setText("");
        txt_lastname.setText("");
        txt_cardnumber.setText("");
        txt_cvc.setText("");
        txt_zipcode.setText("");

    }
    private SpinnerValueFactory<Integer> spinner;

    public void purchaseDisplayQuantity() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchase_quantity.setValueFactory(spinner);
    }
    private int qty;

    @FXML
    public void purchaseQuantity() {
        qty = purchase_quantity.getValue();
    }
    // *****************Hiện số tiền  ***************///

    // ***************** Nghia********************//
    private double totalP;

    public void menuGetTotal() {
        purchasepaymentid();
        conn = ConnectDB.ConnectDB.getConnectDB();
        String sql = "SELECT SUM(price * quantity) AS total FROM dbo.ShoppingCartItems";

        try (PreparedStatement prepare = conn.prepareStatement(sql)) {

            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    totalP = result.getDouble("total");
                } else {
                    totalP = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("$" + String.format("%.2f", totalP));
    }
    // ***************** Add Boook vào bảng vào info Shopping   ***************///

    // ***************** Nghia********************//
    public void purchaseBookInfo() {
        String selectedTitle = purchase_booktitle.getSelectionModel().getSelectedItem();
        if (selectedTitle == null || selectedTitle.isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM dbo.Books WHERE title = ?"; // sửa vào csdl
        try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement prepare = conn.prepareStatement(sql)) {

            prepare.setString(1, selectedTitle);
            try (ResultSet rs = prepare.executeQuery()) {
                if (rs.next()) {
                    pruchase_info_bookID.setText(rs.getString("book_id"));
                    pruchase_info_booktitle.setText(rs.getString("title"));
                    pruchase_info_author.setText(rs.getString("author"));
                    pruchase_info_genre.setText(rs.getString("genre"));
                    pruchase_info_createat.setText(rs.getString("created_at"));
                    pruchase_info_price.setText(rs.getString("price"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseBookId() {
        String sql = "SELECT book_id FROM dbo.Books";
        order_id = getLatestOrderId();
        pruchase_info_orderid.setText(String.valueOf(order_id));
        try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement prepare = conn.prepareStatement(sql); ResultSet rs = prepare.executeQuery()) {

            ObservableList<Integer> listData = FXCollections.observableArrayList();
            while (rs.next()) {
                listData.add(rs.getInt("book_id"));
            }
            purchase_bookID.setItems(listData);

            purchase_bookID.setOnAction(e -> purchaseBookTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseBookTitle() {
        Integer selectedBookId = purchase_bookID.getSelectionModel().getSelectedItem();
        if (selectedBookId == null) {

            return;
        }

        String sql = "SELECT title FROM dbo.Books WHERE book_id = ?";
        try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement prepare = conn.prepareStatement(sql)) {

            prepare.setInt(1, selectedBookId);
            try (ResultSet rs = prepare.executeQuery()) {
                ObservableList<String> listData = FXCollections.observableArrayList();
                while (rs.next()) {
                    listData.add(rs.getString("title"));
                }
                purchase_booktitle.setItems(listData);

                purchase_booktitle.setOnAction(e -> purchaseBookInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // *****************Select từ payment  ***************///
    // ***************** Nghia********************//
    private int purchasepaymentid() {
        int paymentId = 0;

        
        String sql = "SELECT MAX(payment_id) AS max_payment_id FROM dbo.Payments";

        try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement prepare = conn.prepareStatement(sql); ResultSet rs = prepare.executeQuery()) {

            if (rs.next()) {
                paymentId = rs.getInt("max_payment_id");
            }

            paymentId++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentId;
    }
    // ***************** Hiện dữ liệu trên tableview(payment) ***************///

    // ***************** Nghia********************//
    private Integer newInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0; // Default value if parsing fails
        }
    }
    private int book_id;

    public ObservableList<customerData> datacustomerList() {
        ObservableList<customerData> datacustomerList = FXCollections.observableArrayList();
        String sql = "SELECT cart_id, book_id, title, quantity, price, createat FROM dbo.ShoppingCartItems";

        try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement prepare = conn.prepareStatement(sql)) {
            // No need to set any parameters since your query doesn't have placeholders

            try (ResultSet rs = prepare.executeQuery()) {
                while (rs.next()) {
                    Integer cart_id = rs.getInt("cart_id");
                    Integer book_id = rs.getInt("book_id");
                    String title = rs.getString("title");
                    Integer quantity = rs.getInt("quantity");
                    Double price = rs.getDouble("price");
                    Date createat = rs.getDate("createat");
                    customerData data = new customerData(cart_id, book_id, title, quantity, price, createat);
                    datacustomerList.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datacustomerList;
    }

    public void showcustomerDataList() {
        ObservableList<customerData> showData = datacustomerList();
        Platform.runLater(() -> {
            menu_tb_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
            menu_tb_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            menu_tb_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            menu_tb_price.setCellValueFactory(new PropertyValueFactory<>("price"));
            menu_tb_createat.setCellValueFactory(new PropertyValueFactory<>("createat"));

            menu_tableview.getItems().clear();
            menu_tableview.setItems(showData);
        });
    }

    private int generateUniqueCartId() {
        int cartId = 0;

        String sql = "SELECT MAX(cart_id) AS max_cart_id FROM dbo.ShoppingCartItems";

        try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement prepare = conn.prepareStatement(sql); ResultSet rs = prepare.executeQuery()) {
            if (rs.next()) {
                cartId = rs.getInt("max_cart_id");
            }
            cartId++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartId;
    }

    @FXML
    public void purchaseadd() throws JRException, SQLException {
    // Check if the label is initialized
    if (pruchase_info_email == null) {
       
        return;
    }

    purchasepaymentid();
    purchaseQuantity();

    String fetchEmailSQL = "SELECT email FROM dbo.Users WHERE user_id = ?";

    try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); 
         PreparedStatement fetchEmailStmt = conn.prepareStatement(fetchEmailSQL)) {

        // Begin transaction
        conn.setAutoCommit(false);

        fetchEmailStmt.setInt(1, getCurrentUserId());
        ResultSet emailResult = fetchEmailStmt.executeQuery();
        String userEmail = null;
        if (emailResult.next()) {
            userEmail = emailResult.getString("email");
            
            pruchase_info_email.setText(userEmail);
        } else {
            alert.showAlert("User not found in the database.");
            return; // Exit if the user is not found
        }

        int cart_id;
        // Insert into dbo.ShoppingCart and retrieve the generated cart_id
        String insertCartSQL = "INSERT INTO dbo.ShoppingCart (user_id, createat) VALUES (?, ?)";
        try (PreparedStatement insertCartStmt = conn.prepareStatement(insertCartSQL, Statement.RETURN_GENERATED_KEYS)) {
            insertCartStmt.setInt(1, getCurrentUserId());
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            insertCartStmt.setDate(2, sqlDate);
            insertCartStmt.executeUpdate();

            ResultSet generatedKeys = insertCartStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                cart_id = generatedKeys.getInt(1);
            } else {
                conn.rollback();
                alert.showAlert("Failed to retrieve generated cart_id.");
                return;
            }
        }

        // Insert into dbo.ShoppingCartItems
        String sql = "INSERT INTO dbo.ShoppingCartItems (cart_id, book_id, title, quantity, price, createat, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepare = conn.prepareStatement(sql)) {
            if (purchase_booktitle.getSelectionModel().getSelectedItem() != null
                    && purchase_bookID.getSelectionModel().getSelectedItem() != null
                    && userEmail != null && !userEmail.isEmpty()) {

                prepare.setInt(1, cart_id);
                prepare.setInt(2, purchase_bookID.getSelectionModel().getSelectedItem());
                prepare.setString(3, purchase_booktitle.getSelectionModel().getSelectedItem());
                prepare.setInt(4, qty);

                String checkData = "SELECT price FROM dbo.Books WHERE title = ?";
                try (PreparedStatement priceStatement = conn.prepareStatement(checkData)) {
                    priceStatement.setString(1, purchase_booktitle.getSelectionModel().getSelectedItem());
                    ResultSet priceResult = priceStatement.executeQuery();
                    if (priceResult.next()) {
                        double priceD = priceResult.getDouble("price");
                        totalP = qty * priceD;
                        prepare.setDouble(5, priceD);
                    } else {
                        alert.showAlert("Book not found in the database.");
                        return; // Exit if the book is not found
                    }
                }

                java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
                prepare.setDate(6, sqlDate);
                prepare.setString(7, userEmail); // Use the fetched email

                prepare.executeUpdate();
                conn.commit(); // Commit transaction

                // Refresh the TableView to display the updated data
                showcustomerDataList();
                menuDisplayTotal(); // Update any other UI components as necessary
            } else {
                alert.showAlert("Please ensure all fields are filled out.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            conn.rollback(); // Rollback transaction in case of error
            alert.showAlert("Error inserting into ShoppingCartItems.");
        } finally {
            conn.setAutoCommit(true); // Restore auto-commit mode
        }
    }
}

private String fetchUserEmail(int userId) {
    // Implementation here if needed
    return ""; 
}

private int getCurrentUserId() {
    // Return the current user ID
    return 4; 
}





    

    

    public void clearOldData() {

        String deleteSql = "DELETE FROM dbo.ShoppingCartItems";
        try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement deletePrepare = conn.prepareStatement(deleteSql)) {

            deletePrepare.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // *****************Nút chức năng remove   ***************///

    // ***************** Nghia********************//
    @FXML
    public void menuRemoveBtn() {
        customerData selectedItem = menu_tableview.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Select the order you want to remove");
            alert.showAndWait();
        } else {
            Integer book_idToDelete = selectedItem.getBook_id();
            String sql = "DELETE FROM dbo.ShoppingCartItems WHERE book_id=?";
            try (Connection conn = ConnectDB.ConnectDB.getConnectDB(); PreparedStatement prepare = conn.prepareStatement(sql)) {
                prepare.setInt(1, book_idToDelete);
                prepare.executeUpdate();
                menu_tableview.getItems().remove(selectedItem);
                menuGetTotal();
                menu_total.setText("$" + String.format("%.2f", totalP));
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Order removed successfully!");
                alert.showAndWait();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    // *****************   Nút in hóa đơn (Tải các thư viên,thay đổi địa chỉ thư mục)Book.jrxml  ***************///

    // ***************** Nghia********************//
    @FXML
    public void menuReciptBtn() throws JRException, SQLException {

        try (Connection conn = ConnectDB.ConnectDB.getConnectDB();) {
            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\shado\\OneDrive\\Documents\\NetBeansProjects\\BookstoreManagement\\src\\Jasperreport\\Book.jrxml");
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jPrint = JasperFillManager.fillReport(jReport, parameters, conn);
            JasperViewer viewer = new JasperViewer(jPrint, false);
            viewer.setTitle("BookStore Report");

            viewer.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    Platform.runLater(() -> {

                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText("Thanks for your check!");
                        alert.showAndWait();
                    });
                }
            });

            viewer.setVisible(true);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }


 
    public void setBookID(Integer Booksid ){
        
    purchase_bookID.setValue(Booksid);
    BOOKS.setVisible(false);
    SHOPPINGCART1.setVisible(true);
    PAYMENT1.setVisible(true);
    fetchAndSetBookDetails(Booksid);

    }
    
    private void fetchAndSetBookDetails(Integer Booksid) {
        String sql = "SELECT * FROM Books WHERE book_id = ?";
         try {
             pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, Booksid);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                pruchase_info_bookID.setText(String.valueOf(rs.getInt("book_id")));
                pruchase_info_booktitle.setText(rs.getString("title"));
                pruchase_info_author.setText(rs.getString("author"));
                pruchase_info_genre.setText(rs.getString("genre"));
                pruchase_info_createat.setText(rs.getString("created_at"));
                pruchase_info_price.setText(rs.getString("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


   }

    @FXML
    public void initialize() {
    }
    public void setBookTitle(String BookName ){
     
    purchase_booktitle.setValue(BookName);

    }
//    ------------------------------------------------------------
    //    history

//    @FXML
//    private TableColumn<Modelhistory, Integer> col_idorderhs;
//    @FXML
//    private TableColumn<Modelhistory, Integer> col_idbookhs;
//    @FXML
//    private TableColumn<Modelhistory, Integer> col_idcarths;
//    @FXML
//    private TableColumn<Modelhistory, String> col_emailhs;
//
//    @FXML
//    private TableColumn<Modelhistory, Date> col_datehistory;
//    @FXML
//    private TableColumn<Modelhistory, Integer> col_quantityhs;
//    @FXML
//    private TableColumn<Modelhistory, Double> col_pricehs;
//    @FXML
//    private TableColumn<Modelhistory, Integer> col_discounths;
//    Quang
    
    
     //History
//     @FXML
//    private TableView<Modelhistory> tbPaymentHistory;
//
//    @FXML
//    private TableColumn<Modelhistory, Integer> colIdOrderHs;
//    @FXML
//    private TableColumn<Modelhistory, Integer> colIdBookHs;
//    @FXML
//    private TableColumn<Modelhistory, Integer> colIdCartHs;
//    @FXML
//    private TableColumn<Modelhistory, String> colEmailHs;
//    @FXML
//    private TableColumn<Modelhistory, Date> colDateHistory;
//    @FXML
//    private TableColumn<Modelhistory, Integer> colQuantityHs;
//    @FXML
//    private TableColumn<Modelhistory, Double> colPriceHs;
//    @FXML
//    private TableColumn<Modelhistory, Double> colDiscountHs;
//
//
//    public ObservableList<Modelhistory> datahistory() {
//        ObservableList<Modelhistory> datahistory = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM OrderDetails";
//        try {
//            if (conn == null) {
//                // Replace with your alert logic
//                System.out.println("Can't connect to database");
//                return datahistory;
//            }
//            pstmt = conn.prepareStatement(sql);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Modelhistory data = new Modelhistory(
//                    rs.getInt("idorderdetail"),
//                    rs.getInt("idorder"),
//                    rs.getInt("idcart"),
//                    rs.getInt("idbook"),
//                    rs.getInt("quantity"),
//                    rs.getDouble("price"),
//                    rs.getInt("discount"),
//                    rs.getDate("orderdate"),
//                    rs.getString("email")
//                );
//                datahistory.add(data);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return datahistory;
//    }
//
//    public void showDatahistory() {
//        ObservableList<Modelhistory> showList = datahistory();
//        colIdOrderHs.setCellValueFactory(new PropertyValueFactory<>("orderId"));
//        colIdCartHs.setCellValueFactory(new PropertyValueFactory<>("cartId"));
//        colIdBookHs.setCellValueFactory(new PropertyValueFactory<>("bookId"));
//        colEmailHs.setCellValueFactory(new PropertyValueFactory<>("email"));
//        colDateHistory.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
//        colQuantityHs.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        colPriceHs.setCellValueFactory(new PropertyValueFactory<>("price"));
//        colDiscountHs.setCellValueFactory(new PropertyValueFactory<>("discount"));
//        tbPaymentHistory.setItems(showList);
//    }
//    
    
    
    
//    BOOKS NA
    
    
    @FXML
    private GridPane books_gridloader;

    private ObservableList<ModelBooks> menuBooks = FXCollections.observableArrayList();

    public ObservableList<ModelBooks> getmenuBooks() {
    String sql = "SELECT * FROM Books";

    ObservableList<ModelBooks> listdata = FXCollections.observableArrayList();
    try {
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        ModelBooks databooks;

        while (rs.next()) {
            databooks = new ModelBooks(
                    rs.getInt("book_id"),
                    rs.getString("bookimg"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getDouble("price"),
                    rs.getInt("stock"),
                    rs.getTimestamp("created_at")
            );
            listdata.add(databooks);
        }

        } catch (Exception e) {
            System.out.println("Errors: " + e.getMessage());
            e.printStackTrace();
        }
        return listdata;
    }

    public void menuDisplayBooks() {
    menuBooks.clear();
    menuBooks.addAll(getmenuBooks());
    int row = 0;
    int column = 0;

    books_gridloader.getRowConstraints().clear();
    books_gridloader.getColumnConstraints().clear();

    books_gridloader.setHgap(30);
    books_gridloader.setVgap(30);

    for (int i = 0; i < 5; i++) { //  có 5 cột
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(20); // Mỗi cột chiếm 20% chiều rộng
        books_gridloader.getColumnConstraints().add(colConst);
    }

    RowConstraints rowConst = new RowConstraints();
    rowConst.setMinHeight(Region.USE_PREF_SIZE); // Chiều cao tối thiểu theo kích thước ưa thích
    books_gridloader.getRowConstraints().add(rowConst);

    for (int i = 0; i < menuBooks.size(); i++) {
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("BooksProduct.fxml"));
            AnchorPane pane = load.load();
            BooksProductController bookLoad = load.getController();

            bookLoad.setData(menuBooks.get(i));

            // Thêm lớp kiểu "book-pane"
            pane.getStyleClass().add("book-pane");

            if (column == 5) {
                column = 0;
                row += 1;
                // ràng buộc hàng mới khi chuyển sang hàng mới
                books_gridloader.getRowConstraints().add(rowConst);
            }
            books_gridloader.add(pane, column++, row);
        } catch (Exception e) {
            System.out.println("Errors: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

    
    

}
