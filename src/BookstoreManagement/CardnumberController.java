/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package BookstoreManagement;

import Li_Alert.Alert;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextFormatter;
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
public class CardnumberController implements Initializable {

 Alert alert = new Alert();
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private SecretKey secretKey;
    
  
    @FXML
    private Button btn_close;

    @FXML
    private Button btn_minimize;

    @FXML
    private Button btn_signin;

    @FXML
    private AnchorPane fNumbercard;

    @FXML
    private PasswordField txt_cardnumber;

    @FXML
    void handle_close(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
            fNumbercard.getScene().getWindow().hide();
            
    }

    @FXML
    void handle_minimize(ActionEvent event) {
        Stage stage = (Stage) fNumbercard.getScene().getWindow();
        stage.setIconified(true);

    }

            
    @FXML
    void handle_signin(ActionEvent event) {
        if( txt_cardnumber.getText().isEmpty()){
                alert.showAlert("Please enter Card Number");

            }{
                try {
                    
                    if (conn == null) {
                        alert.showAlert("Can't connect to server.");
                        return;
                    }
                    
                    
                    String sql = "SELECT * FROM Users WHERE cardnumber = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1,txt_cardnumber.getText());
                    rs =  pstmt.executeQuery();

                    if(rs.next()){
                        String checkcardnumber = rs.getString("cardnumber");

                        if (checkcardnumber.equals(txt_cardnumber.getText())){
                            String role = rs.getString("role");
                            FXMLLoader loader = null;
                            Parent root = null;
                            String Name = rs.getString("username");
                            String Email = rs.getString("email");
                            Date dateOfBirth = rs.getDate("birthday");
                            String ImageProfile = rs.getString("image");
                            String Genderprofile = rs.getString("gender");


                            if ("Admin".equalsIgnoreCase(role)) {
                                loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                                root = loader.load();
                                MainController Main = loader.getController();
                                Main.setUsername(Name);
                                Main.ProfileUsername(Name);
                                Main.ProfileEmail(Email);
                                Main.ProfileBirthday(dateOfBirth);
                                Main.setProfileImage(ImageProfile);
                                Main.setGenderprofile(Genderprofile);
                            } else {
                                loader = new FXMLLoader(getClass().getResource("UserMain.fxml"));
                                root = loader.load();
                                UserMainController Main = loader.getController();
                                Main.setUsername(Name);
                            }
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setTitle("BookStore Management");
                            stage.setScene(new Scene(root));
                            stage.setFullScreen(false);
                            stage.setResizable(false);
                            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                            stage.show();

                        fNumbercard.getScene().getWindow().hide();

                            try{
                                if (conn != null) conn.close();
                                if (pstmt != null) pstmt.close();
                                if (rs != null) rs.close();
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }else{
                            alert.showAlert("Card Number invalid");
                        }

                    }else{
                        alert.showAlert("Card Number invalid");
                    }
                }catch(Exception ex){
                    System.out.println("Errors: " + ex.getMessage());
                }
            }
    }
    
    TextFormatter<String> TextFormatter = new TextFormatter<>(change -> {
    if (change.getControlNewText().length() > 10) {
        return null;
    } else {
        return change;
    }
    });
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConnectDB.ConnectDB.getConnectDB();
        if (conn == null) {
            alert.showAlert("Connect to server failed");
            return;
        }


        txt_cardnumber.setTextFormatter(TextFormatter);

        txt_cardnumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String Checkcard) {
                if (Checkcard.length() == 10) {
                    handle_signin(null);
                } else if (Checkcard.length() > 10) {
                    alert.showAlert("Invalid Card Number");
                    txt_cardnumber.setText("");
                }
            }
        });

        
    }    
    
}
