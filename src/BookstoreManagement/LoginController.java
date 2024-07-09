
    package BookstoreManagement;


    import Li_Alert.Alert;
    import Li_Encrypt.KeyManager;
    import Li_Encrypt.LibaryEncrypt;
    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.FileReader;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.sql.*;
    import java.net.URL;
    import java.util.ResourceBundle;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.CheckBox;
    import javafx.scene.control.Hyperlink;
    import javafx.scene.control.Label;
    import javafx.scene.control.PasswordField;
    import javafx.scene.control.TextField;
    import javafx.scene.image.ImageView;
    import javafx.scene.input.KeyCode;
    import javafx.scene.input.KeyCombination;
    import javafx.scene.input.KeyEvent;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.Stage;
    import javafx.stage.StageStyle;
    import javax.crypto.SecretKey;



    public class LoginController implements Initializable {
        @FXML
        private Button btn_close,btn_minimize,btn_signin;
        @FXML
        private CheckBox ck_remember;
        @FXML
        private AnchorPane fLogin;
        @FXML
        private Hyperlink lk_fgpassword,lk_signup,lk_cardnumber;
        @FXML
        private PasswordField txt_password;
        @FXML
        private TextField txt_user,txt_password_visible;
        @FXML
        private ImageView btn_showpass,btn_hidenpass;


        @FXML
        void EnterPressed(KeyEvent event) throws SQLException {
            if (event.getCode() == KeyCode.ENTER) {
                handle_signin(null);
            }
        }
        @FXML
        void lk_clickfgpassword(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("ForgetPassword.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Forget Password");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
            fLogin.getScene().getWindow().hide();

        }

        @FXML
        void lk_clicksignup(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
            fLogin.getScene().getWindow().hide();
        }

        @FXML
        void handle_close(ActionEvent event) {
           Stage stage = (Stage) fLogin.getScene().getWindow();
           stage.close();        

        }

        @FXML
        void handle_minimize(ActionEvent event) {
           Stage stage = (Stage) fLogin.getScene().getWindow();
           stage.setIconified(true);
        }


         private boolean PasswordVisible = false;

        
        @FXML
        void lk_clickcardnumber(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("Cardnumber.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Card Number");
            stage.setScene(new Scene(root));
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.show();
            fLogin.getScene().getWindow().hide();
            
            
        }



        @FXML
        void handle_showpass(MouseEvent event) {
             PasswordVisible = !PasswordVisible;
            if (PasswordVisible) {
                txt_password_visible.setText(txt_password.getText());
                txt_password_visible.setVisible(true);
                txt_password.setVisible(false);
                btn_showpass.setVisible(false);
                btn_hidenpass.setVisible(true);
            } else {
                txt_password.setText(txt_password_visible.getText());
                txt_password.setVisible(true);
                txt_password_visible.setVisible(false);
                btn_showpass.setVisible(true);
                btn_hidenpass.setVisible(false);
            }
        }
        @FXML
        void handle_signin(ActionEvent event) throws SQLException {

            if(txt_user.getText().isEmpty() || txt_password.getText().isEmpty()){
                alert.showAlert("Please enter Username and Password");

            }{
                try {
                    
                    

                    if (conn == null) {
                        alert.showAlert("Can't connect to server.");
                        return;
                    }
                    String sql = "SELECT * FROM Users WHERE username = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1,txt_user.getText());
                    rs =  pstmt.executeQuery();

                    if(rs.next()){
                        byte[] EncryptedPassword = rs.getBytes("password");
                        String decryptedPassword = CryptoUtils.decrypt(EncryptedPassword, secretKey);

                        if (decryptedPassword.equals(txt_password.getText())){
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
                                UserMainController UserMain = loader.getController();
                                UserMain.setUsername(Name);
                                UserMain.ProfileUsername(Name);
                                UserMain.ProfileEmail(Email);
                                UserMain.ProfileBirthday(dateOfBirth);
                                UserMain.setProfileImage(ImageProfile);
                                UserMain.setGenderprofile(Genderprofile);
                            }
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setTitle("BookStore Management");
                            stage.setScene(new Scene(root));
                            stage.setFullScreen(false);
                            stage.setResizable(false);
                            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                            stage.show();

                        fLogin.getScene().getWindow().hide();

                            try{
                                if (conn != null) conn.close();
                                if (pstmt != null) pstmt.close();
                                if (rs != null) rs.close();
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }else{
                            alert.showAlert("Username or Password is incorrect");
                        }

                    }else{
                        alert.showAlert("Account is not registered");
                    }
                }catch(Exception ex){
                    System.out.println("Errors: " + ex.getMessage());
                }
            }
        }
        @FXML
        LibaryEncrypt CryptoUtils = new LibaryEncrypt();

        Alert alert = new Alert();
        private Connection conn;
        private PreparedStatement pstmt;
        private ResultSet rs;
        private SecretKey secretKey;


//        private void saveUsername(String username) {
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter("username.txt"))) {
//                writer.write(username);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

//        private void loadUsername() {
//            try (BufferedReader reader = new BufferedReader(new FileReader("username.txt"))) {
//                String username = reader.readLine();
//                if (username != null) {
//                    txt_user.setText(username);
//                    ck_remember.setSelected(true);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }\
        
        

//        private void clearUsername() {
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter("username.txt"))) {
//                writer.write("");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        
        
        
        private Stage Stage;    
        private double xOffset = 0;
        private double yOffset = 0;
        void initStage(Stage Stage) {
         this.Stage = Stage;
            fLogin.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            fLogin.setOnMouseDragged(event -> {
                Stage.setX(event.getScreenX() - xOffset);
                Stage.setY(event.getScreenY() - yOffset);
            });
        }

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
            if(conn==null){
                alert.showAlert("Connect to server failed");
            }
//            loadUsername();
            btn_hidenpass.setVisible(false);
            txt_password_visible.setVisible(false);
            txt_password.textProperty().bindBidirectional(txt_password_visible.textProperty());
        }

    }
