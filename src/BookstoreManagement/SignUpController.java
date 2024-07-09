/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package BookstoreManagement;
import Li_Alert.Alert;
import Li_Encrypt.KeyManager;
import Li_Encrypt.LibaryEncrypt;
import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.crypto.SecretKey;

/**
 * FXML Controller class
 *
 * @author shado
 */
public class SignUpController implements Initializable {
    @FXML
    private Button btn_close;

    @FXML
    private Button btn_minimize;

  
    @FXML
    private Button btn_register;

    @FXML
    private AnchorPane fRegister;

    @FXML
    private Hyperlink lk_fgpassword;

    @FXML
    private Hyperlink lk_signin;

    @FXML
    private PasswordField txt_confirmpassword;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_name;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_username;
    @FXML
    private CheckBox male;
    @FXML
    private CheckBox female;
    
    
    @FXML
    private DatePicker dt_birthday;
      
    @FXML
    void handle_close(ActionEvent event) {
       Stage stage = (Stage) fRegister.getScene().getWindow();
       stage.close();        
        
    }
    
    @FXML
    void handle_minimize(ActionEvent event) {

       Stage stage = (Stage) fRegister.getScene().getWindow();
       stage.setIconified(true);
    }  
 
        
    
    
    @FXML
    void ck_gender(ActionEvent event) {
        
        if(event.getSource()==male){
            if(male.isSelected()){
                female.setSelected(false);
            }
        }else if(event.getSource()==female){
            if(female.isSelected()){
                male.setSelected(false);
            }
        }

    }
    private String Gender() {
        if (male.isSelected()) {
            return "Male";
        } else if (female.isSelected()) {
            return "Female";
        } else {
            return null;
        }
    }

    @FXML
    void btn_register(ActionEvent event) throws SQLException, IOException, Exception {
        if(txt_username.getText().isEmpty() 
                || txt_password.getText().isEmpty()
                || txt_confirmpassword.getText().isEmpty()
                || dt_birthday.getValue() == null
                || txt_email.getText().isEmpty())
        {
           alert.showAlert("Please input full information");
        }else{
            
            try {
                

                if (!txt_password.getText().equals(txt_confirmpassword.getText())) {
                   alert.showAlert("Passwords do not match");
                   return;
                }
                if (!ValidPassword(txt_password.getText())) {
                   alert.showAlert("Password must be longer than 5 characters, contain at least one capital letter and one number");
                   return;
                }
                if (!ValidEmail(txt_email.getText())) {
                   alert.showAlert("Emails must end with @gmail.com or @hotmail.com");
                   return;
                }
                
               byte[] encryptedPassword = CryptoUtils.encrypt(txt_password.getText(), secretKey);
               String sql = "{CALL sp_validUser(?, ?, ?, ?, ?, ?,?,?)}";
               stmt = conn.prepareCall(sql);
               stmt.setString(1, txt_username.getText());
               stmt.setBytes(2, encryptedPassword);
               stmt.setString(3, txt_email.getText());
               stmt.setDate(4, Date.valueOf(dt_birthday.getValue()));
               stmt.setString(5, Gender());
               stmt.setBytes(6, null);
               stmt.setString(7, "User");
               stmt.setString(8, null);

                int rows = stmt.executeUpdate();
                   
                   if(rows> 0){
                       
                    Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setTitle("Login");
                    stage.setScene(new Scene(root));
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    stage.show();
                    fRegister.getScene().getWindow().hide();
                    alert.showAlert("User registered successfully");
                    try{
                    if (conn != null) conn.close();
                    if (stmt != null) stmt.close();
                    if (rs != null) rs.close();
                        }catch (Exception ex) {
                            ex.printStackTrace();
                    }
                   }else{
                    alert.showAlert("User registered failed.");

                   }

           } catch (SQLException e) {
               if (e.getMessage().contains("Username already exists.")) {
                   alert.showAlert("Username already exists.");
               } else if (e.getMessage().contains("Email already exists.")) {
                   alert.showAlert("Email already exists.");
               }else if (e.getMessage().contains("Gender is required.")) {
                   alert.showAlert("Gender is required.");
               }else if (e.getMessage().contains("Year of birth must be from 1920 to 2020.")) {
                   alert.showAlert("Year of birth must be from 1920 to 2020.");
               }else{
                   alert.showAlert("Registered failed");
                   System.out.println("E: "+e.getMessage());
               }
           }
        }
        
    }

    private boolean ValidPassword(String password) {
        return password.length() > 5 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    private boolean ValidEmail(String email) {
        return email.endsWith("@gmail.com") || email.endsWith("@hotmail.com");
    }
  
       
    @FXML
    void lk_clickfgpassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ForgetPassword.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
            fRegister.getScene().getWindow().hide();

    }

    @FXML
    void lk_clicksignin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
        fRegister.getScene().getWindow().hide();

    }
    
    
    private SecretKey secretKey;
    LibaryEncrypt CryptoUtils = new LibaryEncrypt();
    Alert alert = new Alert();
    private CallableStatement stmt;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
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
        if(conn == null){
           alert.showAlert("Connect to server failed");
           return;
        }
    }
    private Stage Stage;    
    private double xOffset = 0;
    private double yOffset = 0;
    void initStage(Stage Stage) {
     this.Stage = Stage;
        fRegister.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        fRegister.setOnMouseDragged(event -> {
            Stage.setX(event.getScreenX() - xOffset);
            Stage.setY(event.getScreenY() - yOffset);
        });
    }
}
