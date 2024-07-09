package BookstoreManagement;

import Li_Alert.Alert;
import Li_Encrypt.KeyManager;
import Li_Encrypt.LibaryEncrypt;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.crypto.SecretKey;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetPasswordController implements Initializable {
    @FXML
    private Label label_email;
    @FXML
    private AnchorPane fNewPassword, fMainforget, fForgetpassword;

    @FXML
    private TextField txt_code, txt_email;

    @FXML
    private PasswordField txt_confirmpassword, txt_password;

    @FXML
    private Button btn_close, btn_minimize, btn_sendemail, btn_verify;

    private Alert alert = new Alert();
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String otp;
    LibaryEncrypt CryptoUtils = new LibaryEncrypt();
    private SecretKey secretKey;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConnectDB.ConnectDB.getConnectDB();
        if (conn == null) {
            alert.showAlert("Connect to server failed");
        }
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
    }

    @FXML
    void handle_close(ActionEvent event) {
        Stage stage = (Stage) fMainforget.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handle_minimize(ActionEvent event) {
        Stage stage = (Stage) fMainforget.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void handle_sendEmail(ActionEvent event) {
        SecureRandom random = new SecureRandom();
        int min = 100000;
        int max = 999999;
        otp = String.valueOf(random.nextInt(max - min + 1) + min);
        System.out.println("Generated OTP: " + otp); // Debugging statement

        if (txt_email.getText().isEmpty()) {
            alert.showAlert("Please enter the email.");
        } else {
            try {
                String check = "SELECT * FROM Users WHERE email = ?";
                pstmt = conn.prepareStatement(check);
                pstmt.setString(1, txt_email.getText());
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    if (sendEmail(txt_email.getText(), otp)) {
                        label_email.setText(txt_email.getText());
                        alert.showAlert("OTP sent, please check your email.");
                        txt_email.setText("");
                    } else {
                        alert.showAlert("Failed to send OTP. Please try again.");
                    }
                    try {
                        if (rs != null) rs.close();
                        if (pstmt != null) pstmt.close();
                    } catch (Exception e) {
                            e.printStackTrace();
                    }
                } else {
                    alert.showAlert("Email not found.");
                }
            } catch (Exception e) {
                System.out.println("Errors: " + e.getMessage());
                e.printStackTrace();
                alert.showAlert("An error occurred while checking the email.");
            }
        }
    }

    private boolean sendEmail(String to, String otp) {
        String from = "tenshiyami98@gmail.com";
        String host = "smtp.gmail.com";
        final String username = "tenshiyami98@gmail.com";
        final String password = "sebu bkzv zpev hocs";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);

            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

    @FXML
    void handle_verify(ActionEvent event) {


        if (txt_code.getText().equals(otp)) {
            Platform.runLater(() -> {
                fForgetpassword.setVisible(false);
                fNewPassword.setVisible(true);
            });
            alert.showAlert("Verified successfully.");
        } else {
            alert.showAlert("Invalid OTP. Please try again.");
        }
    }
    private boolean ValidPassword(String password) {
        return password.length() > 5 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    @FXML
    void handle_resetPassword(ActionEvent event) {

        if (txt_password.getText().isEmpty() || txt_confirmpassword.getText().isEmpty()) {
            alert.showAlert("Please fill in all password fields.");
        } else {
            try {
                
                if (txt_password.getText().isEmpty() || txt_confirmpassword.getText().isEmpty()) {
                        alert.showAlert("Please fill in all password fields.");
                        return;
                }

               if (!txt_password.getText().equals(txt_confirmpassword.getText())) {
                        alert.showAlert("Passwords do not match. Please try again.");
                        return;
                }
                
                String check = "SELECT * FROM Users WHERE email = ?";
                    pstmt = conn.prepareStatement(check);
                    pstmt.setString(1,label_email.getText());
                    rs =  pstmt.executeQuery();
                if(rs.next()){
                    
                    String checkmail = rs.getString("email");
                    
                    if(label_email.getText().equals(checkmail)){
                        
                        byte[] encryptedPassword = CryptoUtils.encrypt(txt_password.getText(), secretKey);
                        String update =  " UPDATE Users set password = ? WHERE email = ? ";
                        pstmt = conn.prepareStatement(update);                        
                        pstmt.setBytes(1,encryptedPassword );
                        pstmt.setString(2,label_email.getText());
                        int rows = pstmt.executeUpdate();
                        
                        if(rows>0){
                            alert.showAlert("Update password successfuly");
                            
                            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setTitle("Register");
                            stage.setScene(new Scene(root));
                            stage.setFullScreen(false);
                            stage.setResizable(false);
                            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                            stage.show();
                            fMainforget.getScene().getWindow().hide();
                            try {
                                if (rs != null) rs.close();
                                if (pstmt != null) pstmt.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else{
                            alert.showAlert("Update password failed");

                        }
                    
                    }else{
                        alert.showAlert("Invalid Email");
                    }
                
                }else{
                    alert.showAlert("can't change password");
                }
            }catch (Exception e){
                System.out.print("Errors: "+e.getMessage());
            
            }
        }
    }
    
    
    @FXML
    void handle_back(ActionEvent event) throws IOException{
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
            fMainforget.getScene().getWindow().hide();
    
    
    }
}
