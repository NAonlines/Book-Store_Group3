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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.crypto.SecretKey;

public class MainController implements Initializable {
    @FXML
    private AnchorPane BOOKREVIEW, BOOKS, DASHBOARD, DISCOUNT, MEMBERS, ORDERS, PAYMENT, PUBLISHER, SETTING, SHOPPINGCART, PROFILE,CHANGEPASSWORD,EDITPROFILEUSER,PAYMENTHISTORY;
    @FXML
    private Button btn_books, btn_close, btn_dashboard, btn_discount, btn_members, btn_minimize, btn_orders, btn_payment, btn_publisher, btn_review, btn_setting, btn_shoppingcart;
    @FXML
    private MenuItem btn_logoutmenu,btn_paymentmenu;
    @FXML
    private SplitMenuButton btn_edittprofile;
    @FXML
    private AnchorPane fMain,pane_img,pane_imgprofile;
    @FXML
    private Label name_user,path_user,label_pathprofile ;

//----------------------NA--------------------------------------------------------
    @FXML
    private TableColumn<Modelmember, Date> cl_birthday;
    @FXML
    private TableColumn<Modelmember, Timestamp> cl_creattime;
    @FXML
    private TableColumn<Modelmember, String> cl_email;
    @FXML
    private TableColumn<Modelmember, String> cl_gender;
    @FXML
    private TableColumn<Modelmember, Integer> cl_id;
    @FXML
    private TableColumn<Modelmember, byte[]> cl_img;
    @FXML
    private TableColumn<Modelmember, String> cl_role;
    @FXML
    private TableColumn<Modelmember, String> cl_username;
   @FXML
    private TableColumn<Modelmember, String> cl_cardnumber;
  
    
    
//    history
    
    
    @FXML
    private TableColumn<Modelhistory,Date> cl_pdate;
    @FXML
    private TableColumn<Modelhistory,String> cl_pfbookname;

    
    
//---------------------    
    
    
    @FXML
    private DatePicker date_birthday,date_profileuser;

    @FXML
    private ImageView img_user,btn_hidenpass,btn_showpass,img_profileuser,avatar_user;
    @FXML
    private TableView<Modelmember> tb_member,tb_paymenthistory;
    @FXML
    private TextField txt_emailmember, txt_idmember, txt_passwordmember,txt_cardnumber, txt_search, txt_usernamemember,profile_username,profile_oldpassword,profile_newpassword,profile_comfirmpassword,profile_email,profile_oldpassword_visible,profile_changpassworduser;
    @FXML
    private Button btn_adduser, btn_deleteuser, btn_edituser, btn_reset, btn_search, btn_img,btn_profileupdate,btn_editprofileuser,btn_changepassword,btn_logouteditprofile,btn_profileupdatepassword,btn_profilechangeimg,btn_profilehistory;
    @FXML
    private ChoiceBox<String> gender, role;
    
    @FXML
    private CheckBox profile_female,profile_male;
    private String[] sgender = {"Male", "Female"};
    private String[] srole = {"User", "Admin"};
    


    public void selectgender(ActionEvent event) {
        String choicegender = gender.getValue();
    }

    public void selectrole(ActionEvent event) {
        String choicerole = role.getValue();
    }

    public void setUsername(String Username){
        name_user.setText("Hello, "+Username);

    }
    
    
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
    }

    @FXML
    void handle_minimize(ActionEvent event) {
        Stage stage = (Stage) fMain.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
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


    
    
    public void swtichForm(ActionEvent event) {
        if (event.getSource() == btn_dashboard) {
            DASHBOARD.setVisible(true);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_books) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(true);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_review) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(true);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            SETTING.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_publisher) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(true);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_orders) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(true);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_payment) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(true);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_discount) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(true);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_members) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(true);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_setting) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(true);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_shoppingcart) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(true);
            PROFILE.setVisible(false);
        } else if (event.getSource() == btn_edittprofile) {
            DASHBOARD.setVisible(false);
            BOOKS.setVisible(false);
            BOOKREVIEW.setVisible(false);
            PUBLISHER.setVisible(false);
            ORDERS.setVisible(false);
            PAYMENT.setVisible(false);
            DISCOUNT.setVisible(false);
            MEMBERS.setVisible(false);
            SETTING.setVisible(false);
            SHOPPINGCART.setVisible(false);
            PROFILE.setVisible(true);
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) fMain.getScene().getWindow();
        stage.close();
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.getItems().addAll(sgender);
        gender.setOnAction(this::selectgender);
        role.getItems().addAll(srole);
        role.setOnAction(this::selectrole);
        
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
        showDataPublisher();
        showDataOrder();
        selectDataPublisher();
        selectDataOrder();
                
        showDataMember();
        btn_hidenpass.setVisible(false);
        profile_oldpassword_visible.setVisible(false);
        profile_oldpassword.textProperty().bindBidirectional(profile_oldpassword_visible.textProperty()); 
    }
    
    
    
    
    
//    -----------------------------------------NA------------------------------------------------------
    
//    MEMBER
  

    
    
    //  upload hình member
    private String lastUsedDirectory = null;

    public void insertImage(){
        FileChooser open = new FileChooser();
        if (lastUsedDirectory != null) {
            File initialDirectory  = new File(lastUsedDirectory);
            if (initialDirectory.exists()) {
                open.setInitialDirectory(initialDirectory);
            }
        }
        Stage stage = (Stage)pane_img.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if(file != null){
            String path = file.getAbsolutePath();
            lastUsedDirectory = file.getParent();

            path = path.replace("\\", "\\\\");
            path_user.setText(path);
            Image image = new Image(file.toURI().toString(), 110, 110, false, true);
            img_user.setImage(image);

        }else{
                System.out.println("NO DATA EXIST!");
        }
       
    }
    
   
    
    public void selectDataMember(){
        Modelmember data = tb_member.getSelectionModel().getSelectedItem();
        int num = tb_member.getSelectionModel().getSelectedIndex();
        if (num < 0) return;

        txt_idmember.setText(String.valueOf("ID : " + data.getUser_id()));
        txt_usernamemember.setText(data.getUsername());
        
        try {
            String decryptedPassword = CryptoUtils.decrypt(data.getPassword(), secretKey);
            String TextPassword = new String(decryptedPassword);
        txt_passwordmember.setText(TextPassword);
        } catch (Exception e) {
            alert.showAlert("Error decrypting password: " + e.getMessage());
        }
        
        
        
        txt_emailmember.setText(data.getEmail());
        if (data.getBirthday() != null) {
            Date utilDate = new Date(data.getBirthday().getTime());
            LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            date_birthday.setValue(localDate);
        } else {
            alert.showAlert("Birthday is NULL");
        }
        role.setValue(data.getRole());
        gender.setValue(data.getGender());
        
        
        String picture ="file:" +  data.getImage();
        Image image = new Image(picture, 110, 110, false, true);
        img_user.setImage(image);
        String path =data.getImage();
        path_user.setText(path);
        
        txt_cardnumber.setText(data.getCardnumber());
    }
    
    
        @FXML
    void handle_adduser(ActionEvent event) throws Exception {
         if(txt_usernamemember.getText().isEmpty() 
                || txt_passwordmember.getText().isEmpty()
                || date_birthday.getValue() == null
                || txt_emailmember.getText().isEmpty())
        {
           alert.showAlert("Please input full information");
        }else{
            
            try {
                

                if (!ValidPassword(txt_passwordmember.getText())) {
                   alert.showAlert("Password must be longer than 5 characters, contain at least one capital letter and one number");
                   return;
                }
                if (!ValidEmail(txt_emailmember.getText())) {
                   alert.showAlert("Emails must end with @gmail.com or @hotmail.com");
                   return;
                }
                
               byte[] encryptedPassword = CryptoUtils.encrypt(txt_passwordmember.getText(), secretKey);
                String sql = "{CALL sp_validUser(?, ?, ?, ?, ?, ?,?,?)}";
                stmt = conn.prepareCall(sql);
                stmt.setString(1, txt_usernamemember.getText());
                stmt.setBytes(2, encryptedPassword);
                stmt.setString(3, txt_emailmember.getText());
                stmt.setDate(4, java.sql.Date.valueOf(date_birthday.getValue()));
                stmt.setString(5, gender.getValue());
                stmt.setString(6,path_user.getText());
                stmt.setString(7, role.getValue());
                stmt.setString(8,txt_cardnumber.getText());
                stmt.executeUpdate();    
                alert.showAlert("ADD Successfully.");
                showDataMember();
                

           } catch (SQLException e) {
               if (e.getMessage().contains("Username already exists.")) {
                   alert.showAlert("Username already exists.");
               } else if (e.getMessage().contains("Email already exists.")) {
                   alert.showAlert("Email already exists.");
               }else if (e.getMessage().contains("Gender is required.")) {
                   alert.showAlert("Gender is required.");
               }else if (e.getMessage().contains("Invalid role value.")) {
                   alert.showAlert("Role is required.");
               }else if (e.getMessage().contains("Year of birth must be from 1920 to 2020.")) {
                   alert.showAlert("Year of birth must be from 1920 to 2020.");
               }else{
                   alert.showAlert("Failed");
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
        void EnterPressedSearch(KeyEvent event) throws SQLException {
            if (event.getCode() == KeyCode.ENTER) {
                handle_search(null);
            }
        }
        
        
    
    @FXML
    void handle_search(ActionEvent event) {
        String keyword = txt_search.getText().trim();
        ObservableList<Modelmember> search = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Users WHERE username LIKE ? OR email LIKE ?";
        boolean DateSearch = false;
        boolean YearSearch = false;
        LocalDate birthdaySearch = null;
        int yearSearch = 0;

        try {
            birthdaySearch = LocalDate.parse(keyword, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            sql += " OR birthday = ?";
            DateSearch = true;
        } catch (Exception e) {
        }

       
        if (!DateSearch) {
            try {
                yearSearch = Integer.parseInt(keyword);
                if (String.valueOf(yearSearch).length() == 4) {
                    sql += " OR YEAR(birthday) = ?";
                    YearSearch = true;
                }
            } catch (Exception e) {
            }
        }

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            if (DateSearch) {
                pstmt.setDate(3, java.sql.Date.valueOf(birthdaySearch));
            } else if (YearSearch) {
                pstmt.setInt(3, yearSearch);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Modelmember data = new Modelmember(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getBytes("password"),
                    rs.getString("email"),
                    rs.getDate("birthday"),
                    rs.getString("gender"),
                    rs.getString("image"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at"),
                        rs.getString("cardnumber")
                );
                search.add(data);
            }

        } catch (Exception e) {
            alert.showAlert("Error: " + e.getMessage());
        }

        tb_member.setItems(search);
    }
    
    
     @FXML
    void handle_deleteuser(ActionEvent event) throws SQLException {
        try {

            String sql = "DELETE from Users Where user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(txt_idmember.getText().replaceAll("[^\\d]", ""))); 

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                alert.showAlert("User has been successfully deleted.");
                showDataMember();
            } else {
                alert.showAlert("Delete failed.");
            }
        } catch (Exception e) {
            alert.showAlert("Invalid user ID.");
        }

    }


    @FXML
    void handle_reset(ActionEvent event) {
        txt_usernamemember.setText("");
        txt_emailmember.setText("");
        txt_passwordmember.setText("");
        txt_idmember.setText("");
        date_birthday.setValue(null);
        gender.setValue(null);
        role.setValue(null);
        img_user.setImage(null);
        path_user.setText("");
        txt_cardnumber.setText("");
    }

    @FXML
    void handle_updateuser(ActionEvent event) throws Exception {
        try {
                if (!ValidPassword(txt_passwordmember.getText())) {
                   alert.showAlert("Password must be longer than 5 characters, contain at least one capital letter and one number");
                   return;
                }
                if (!ValidEmail(txt_emailmember.getText())) {
                   alert.showAlert("Emails must end with @gmail.com or @hotmail.com");
                   return;
                }
                
                 byte[] encryptedPassword = CryptoUtils.encrypt(txt_passwordmember.getText(), secretKey);
                String sql = "UPDATE Users SET username = ?, password = ?, email = ?, birthday = ?, gender = ?, image = ?, role = ? , cardnumber = ? WHERE user_id = ?";                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, txt_usernamemember.getText());
                pstmt.setBytes(2, encryptedPassword);
                pstmt.setString(3, txt_emailmember.getText());
                pstmt.setDate(4, java.sql.Date.valueOf(date_birthday.getValue()));
                pstmt.setString(5, gender.getValue());
                pstmt.setString(6,path_user.getText());
                pstmt.setString(7, role.getValue());
                pstmt.setString(8, txt_cardnumber.getText());
                pstmt.setInt(9, Integer.parseInt(txt_idmember.getText().replaceAll("[^\\d]", "")));

                int rows = pstmt.executeUpdate();
                
                if (rows > 0) {
                    alert.showAlert("User updated successfully.");
                    showDataMember();
                } else {
                    alert.showAlert("Update failed");
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
                   alert.showAlert("Failed");
                   System.out.print("Error closing statement: " + e.getMessage());
               }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public ObservableList<Modelmember> dataListMember() {
        ObservableList<Modelmember> dataList = FXCollections.observableArrayList();
        String sql = "Select * from Users";

        try {
            if (conn == null) {
                alert.showAlert("Lost connect to database");
                return dataList;
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Modelmember data = new Modelmember(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getBytes("password"),
                    rs.getString("email"),
                    rs.getDate("birthday"),
                    rs.getString("gender"),
                    rs.getString("image"),
                    rs.getString("role"),
                    rs.getTimestamp("created_at"),
                    rs.getString("cardnumber")
                );
                dataList.add(data);
                System.out.print("cardnumber");

            }
        } catch (Exception e) {
            System.out.println("Errors: " + e.getMessage());
        }
        return dataList;
    }
    public void showDataMember() {
        ObservableList<Modelmember> showList = dataListMember();
        cl_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        cl_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        cl_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        cl_birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        cl_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cl_img.setCellValueFactory(new PropertyValueFactory<>("image"));
        cl_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        cl_creattime.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        cl_cardnumber.setCellValueFactory(new PropertyValueFactory<>("cardnumber"));
        tb_member.setItems(showList);
    }
    
    
//  PROFILE
    
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
                System.out.println("NO DATA EXIST!");
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
        if(profile_email.getText().isEmpty() || date_birthday == null ||Profile_Gender() == null){
            alert.showAlert("Please input full information");

        }else{
            try {
            
                 if (!ValidEmail(profile_email.getText())) {
                   alert.showAlert("Emails must end with @gmail.com or @hotmail.com");
                   return;
                }
                
                String sql = "UPDATE Users SET  email = ?, birthday = ?, gender = ?, image = ? WHERE username = ?";                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, profile_email.getText());
                pstmt.setDate(2, java.sql.Date.valueOf(date_profileuser.getValue()));
                pstmt.setString(3, Profile_Gender());
                pstmt.setString(4,label_pathprofile.getText());
                pstmt.setString(5,profile_username.getText());

                int rows = pstmt.executeUpdate();
                
                if (rows > 0) {
                    alert.showAlert("Update profile successfully.");
                    showDataMember();
                } else {
                    alert.showAlert("Update failed");
                }


           } catch (SQLException e) {
               if (e.getMessage().contains("Email already exists.")) {
                   alert.showAlert("Email already exists.");
               }else if (e.getMessage().contains("Gender is required.")) {
                   alert.showAlert("Gender is required.");
               }else if (e.getMessage().contains("Year of birth must be from 1920 to 2020.")) {
                   alert.showAlert("Year of birth must be from 1920 to 2020.");
               }else{
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
                            showDataMember();

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
    
    
//    QUANG
    
    
                
        //publisher
            @FXML
            private Button btnaddpublisher;
            @FXML
            private Button btndeletepublisher;
            @FXML
            private Button btnexitpublisher;
            @FXML
            private Button btnfixpublisher;
            @FXML
            private Button btnsavepublisher;
            @FXML
            private Button btnsearchpublisher;
            @FXML
            private TableColumn<ModelPublisher, String> col_address;
            @FXML
            private TableColumn<ModelPublisher, String> col_email;
            @FXML
            private TableColumn<ModelPublisher, Integer> col_id;
            @FXML
            private TableColumn<ModelPublisher, String> col_name;
            @FXML
            private TableColumn<ModelPublisher, String> col_phone;
            @FXML
            private TableView<ModelPublisher> tablepublisher;
            @FXML
            private TextField txtaddress;

            @FXML
            private TextField txtemail;
            @FXML
            private TextField txtname;

            @FXML
            private TextField txtphone;

            @FXML
            private TextField txtsearch;


        //ORDER
            @FXML
            private Button btndeleteorder;
            @FXML
            private Button btndetailorder;
            @FXML
            private Button btnexitorder;
            @FXML
            private Button btnprintorder;
            @FXML
            private Button btnsearchorder;
            @FXML
            private TableColumn<ModelOrder, Integer> col_idorder;
            @FXML
            private TableColumn<ModelOrder, Integer> col_iduser;
            @FXML
            private TableColumn<ModelOrder, Integer> col_idcart;
            @FXML
            private TableColumn<ModelOrder, String> col_orderdate;
            @FXML
            private TableColumn<ModelOrder, String> col_amount;
            
            @FXML
            private TableView<ModelOrder> tableorder;
            @FXML
            private TextField txtamount;
            @FXML
            private TextField txtidorder;
            @FXML
            private TextField txtiduser;
             @FXML
            private TextField txtsearchorder;
            
       //Orderdetail
            @FXML
            private TextField txtidbook;
            @FXML
            private TextField txtidorderdetail;
            @FXML
            private TextField txtprice;
            @FXML
            private TextField txtquantily;
          


        //Publisher    
        public ObservableList<ModelPublisher> datapublisher(){ 
                //goi ham 
                ObservableList<ModelPublisher> datapublisher = FXCollections.observableArrayList(); 
               try{ 

                    if(conn==null){
                        alert.showAlert("Can't connect to database");

                        return datapublisher;

                    }
                    String sql = "select * from Publishers"; 
                    pstmt = conn.prepareStatement(sql); 
                    rs = pstmt.executeQuery(); 
                    ModelPublisher data; 
                    while(rs.next()){ 
                        data= new ModelPublisher(
                                rs.getInt("publisher_id"),  
                                rs.getString("name"), 
                                rs.getString("address"),  
                                rs.getString("phone"), 
                                rs.getString("email") 
                        ); 
                        datapublisher.add(data); 
                    } 
                }catch(Exception e){ 
                    System.out.println(e.getMessage()); 
                } 
                return datapublisher; 
            }

        public void showDataPublisher(){ 
                ObservableList<ModelPublisher> showList = datapublisher(); 
                col_id.setCellValueFactory(new PropertyValueFactory<>("publisher_id")); 
                col_name.setCellValueFactory(new PropertyValueFactory<>("name")); 
                col_address.setCellValueFactory(new PropertyValueFactory<>("address")); 
                col_phone.setCellValueFactory(new PropertyValueFactory<>("phone")); 
                col_email.setCellValueFactory(new PropertyValueFactory<>("email")); 
                tablepublisher.setItems(showList);   
            } 

        public void selectDataPublisher(){
                ModelPublisher data = tablepublisher.getSelectionModel().getSelectedItem();
                int num = tablepublisher.getSelectionModel().getSelectedIndex();
                if((num-1) < -1)
                    return;
                txtname.setText(data.getName());
                txtphone.setText(data.getPhone());
                txtaddress.setText(data.getAddress());
                txtemail.setText(data.getEmail());
        }

        public void insertPublisher() {
            
              String name = txtname.getText();
              String phone = txtphone.getText();
              String email = txtemail.getText();
              String address = txtaddress.getText();
                if (txtname.getText().isEmpty() ||
                    txtphone.getText().isEmpty() || 
                    txtemail.getText().isEmpty() || 
                    txtaddress.getText().isEmpty()) {
                    alert.showAlert("Please fill in all fields.");
                } else {
                    
                    try{
                        String checkEmail = "SELECT * FROM Publishers where email= '"+ txtemail.getText() + "'";
                        pstmt = conn.prepareStatement(checkEmail);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            alert.showAlert("Email này đã được đăng ký");
                        }else{
                             String sql = "INSERT INTO Publishers (name, phone, address, email) VALUES (?, ?, ?, ?)";
                                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                                pstmt.setString(1, name);
                                pstmt.setString(2, phone);
                                pstmt.setString(3, address);
                                pstmt.setString(4, email);

                                int result = pstmt.executeUpdate();
                                if (result > 0) {
                                    alert.showAlert("Publisher added successfully!");
                                    showDataPublisher(); // Refresh the table view
                                } else {
                                    alert.showAlert("Error occurred while adding the publisher.");
                                }
                        }
                    
                    }catch(Exception ex){
                        System.out.print("Erors : " +ex.getMessage());
                    }
                        
                    }

                }
    

        public void deletePublisher() {
                int selectedIndex = tablepublisher.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0) {
                      ModelPublisher selectedPublisher = tablepublisher.getItems().get(selectedIndex);
                  int publisherId = selectedPublisher.getPublisher_id();
                  String sql = "DELETE FROM Publishers WHERE publisher_id = ?";
                     try {
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, publisherId);
                        int result = pstmt.executeUpdate();
                        if (result > 0) {
                            alert.showAlert("Publisher deleted successfully!");
                            showDataPublisher(); // Refresh the table view
                     } else {
                         alert.showAlert("Error occurred while deleting the publisher.");
                        }
                    } catch (Exception e) {}
                }
            }

        public void FixPublisher() {
            try {
                ModelPublisher selectedPublisher = tablepublisher.getSelectionModel().getSelectedItem();
                if (selectedPublisher == null) {
                    alert.showAlert("Please select a publisher to update.");
                    return;
                }

                String name = txtname.getText();
                String phone = txtphone.getText();
                String email = txtemail.getText();
                String address = txtaddress.getText();
                int publisherId = selectedPublisher.getPublisher_id();

                String sql = "UPDATE Publishers SET name = ?, phone = ?, address = ?, email = ? WHERE publisher_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, phone);
                pstmt.setString(3, address);
                pstmt.setString(4, email);
                pstmt.setInt(5, publisherId);

                int result = pstmt.executeUpdate();
                if (result > 0) {
                    alert.showAlert("Publisher updated successfully!");
                    showDataPublisher(); // Refresh the table view
                } else {
                    alert.showAlert("Error occurred while updating the publisher.");
                }
            } catch (Exception e) {}
        }

        public void exitPublisher() {
            Stage stage = (Stage) btnexitpublisher.getScene().getWindow();
            stage.close();
        }

        public void savePublisher() {
            txtname.setText("");
            txtphone.setText("");
            txtemail.setText("");
            txtaddress.setText("");

        }
       
        
 

        public void searchPublisherById(ActionEvent event) {
                String searchText = txtsearch.getText().trim();

            if (searchText.isEmpty()) {
                String sql = "SELECT * FROM Publishers";
                try {
                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    ObservableList<ModelPublisher> allPublishers = FXCollections.observableArrayList();
                    while (rs.next()) {
                        ModelPublisher publisher = new ModelPublisher(
                                rs.getInt("publisher_id"),
                                rs.getString("name"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email")
                        );
                        allPublishers.add(publisher);
                    }
                    if (allPublishers.isEmpty()) {
                        alert.showAlert("No publishers found.");
                    } else {
                        tablepublisher.setItems(allPublishers);
                    }
                } catch (Exception e){}
            } else {
                int id;
                try {
                    id = Integer.parseInt(searchText);
                } catch (NumberFormatException e) {
                    alert.showAlert("ID must be an integer.");
                    return;
                }

                String sql = "SELECT * FROM Publishers WHERE publisher_id = ?";
                try {
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, id);
                    rs = pstmt.executeQuery();

                    ObservableList<ModelPublisher> searchResult = FXCollections.observableArrayList();
                    while (rs.next()) {
                        ModelPublisher publisher = new ModelPublisher(
                                rs.getInt("publisher_id"),
                                rs.getString("name"),
                                rs.getString("address"),
                                rs.getString("phone"),
                                rs.getString("email")
                        );
                        searchResult.add(publisher);
                    }

                    if (searchResult.isEmpty()) {
                        alert.showAlert("No publisher found with ID: " + id);
                    } else {
                        tablepublisher.setItems(searchResult);
                    }

                } catch (Exception e) {}
            }
        }
        
        














            //Order  
    public ObservableList<ModelOrder> dataorder(){ 
                //goi ham 
                ObservableList<ModelOrder> dataorder = FXCollections.observableArrayList(); 
                String sql = "select * from Orders"; 
               try{ 

                    if(conn==null){
                        alert.showAlert("Can't connect to database");

                        return dataorder;

                    }
                    pstmt = conn.prepareStatement(sql); 
                    rs = pstmt.executeQuery(); 
                    ModelOrder data; 
                    while(rs.next()){ 
                        data= new ModelOrder(
                                rs.getInt("order_id"),  
                                rs.getInt("user_id"), 
                                rs.getInt("cart_id"),
                                rs.getString("order_date"),  
                                rs.getString("total_amount")                                
                        ); 
                        dataorder.add(data); 
                        
                        
                    } 
                }catch(Exception e){ 
                    System.out.println(e.getMessage()); 
                } 
                return dataorder; 
            }

    public void showDataOrder(){ 
                ObservableList<ModelOrder> showList = dataorder(); 
                col_idorder.setCellValueFactory(new PropertyValueFactory<>("order_id")); 
                col_iduser.setCellValueFactory(new PropertyValueFactory<>("user_id")); 
                col_idcart.setCellValueFactory(new PropertyValueFactory<>("cart_id")); 
                col_orderdate.setCellValueFactory(new PropertyValueFactory<>("order_date")); 
                col_amount.setCellValueFactory(new PropertyValueFactory<>("total_amount")); 
                tableorder.setItems(showList);   
            }

    public void selectDataOrder(){
                ModelOrder data = tableorder.getSelectionModel().getSelectedItem();
                int num = tableorder.getSelectionModel().getSelectedIndex();
                if((num-1) < -1)
                    return;
                txtidorder.setText(String.valueOf(data.getOrder_id()));
                txtiduser.setText(String.valueOf(data.getUser_id()));
                txtamount.setText(data.getTotal_amount());
                
                
            }

    public void deletOrder() {
                int selectedIndex = tableorder.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0) {
                      ModelOrder selectedOrder = tableorder.getItems().get(selectedIndex);
                  int OrderId = selectedOrder.getOrder_id();
                  String sql = "DELETE FROM Orders WHERE order_id = ?";
                     try {
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, OrderId);
                        int result = pstmt.executeUpdate();
                        if (result > 0) {
                            alert.showAlert("Order deleted successfully!");
                            showDataOrder(); 
                     } else {
                         alert.showAlert("Error occurred while deleting the Order.");
                        }
                    } catch (Exception e) {}
                }
            }

    public void exitOrder() {
                Stage stage = (Stage) btnexitorder.getScene().getWindow();
                stage.close();
            }
            
    public void searchOrder(ActionEvent event) {
                String searchText = txtsearchorder.getText().trim();
                if (searchText.isEmpty()) {
                    String sql = "SELECT * FROM Orders";
                    try {
                        pstmt = conn.prepareStatement(sql);
                        rs = pstmt.executeQuery();
                        ObservableList<ModelOrder> allOrders = FXCollections.observableArrayList();
                        while (rs.next()) {
                            ModelOrder order = new ModelOrder(
                                    rs.getInt("order_id"),
                                    rs.getInt("user_id"),
                                    rs.getInt("cart_id"),
                                    rs.getString("order_date"),
                                    rs.getString("total_amount")
                            );
                            allOrders.add(order);
                        }
                        if (allOrders.isEmpty()) {
                            alert.showAlert("No orders found.");
                        } else {
                            tableorder.setItems(allOrders);
                        }
                    } catch (Exception e) {}
                } else {
                    try {
                        int id = Integer.parseInt(searchText);
                        String sql = "SELECT * FROM Orders WHERE order_id = ?";
                        try {
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setInt(1, id);                          
                            rs = pstmt.executeQuery();
                            ObservableList<ModelOrder> searchResult = FXCollections.observableArrayList();
                            while (rs.next()) {
                                ModelOrder order = new ModelOrder(
                                        rs.getInt("order_id"),
                                        rs.getInt("user_id"),
                                        rs.getInt("cart_id"),
                                        rs.getString("order_date"),
                                        rs.getString("total_amount")
                                );
                                searchResult.add(order);
                            }
                            if (searchResult.isEmpty()) {
                                alert.showAlert("No order found with order ID" + id);
                            } else {
                                tableorder.setItems(searchResult);
                            }
                        } catch (Exception e) {}
                    } catch (Exception e) {}
                }
            }
            
    public void printOrder(ActionEvent event) {
                ModelOrder selectedOrder = tableorder.getSelectionModel().getSelectedItem();
                if (selectedOrder == null) {
                    alert.showAlert("No order selected.");
                    return;
                }                
                TextFlow textFlow = new TextFlow(
                    new Text("Order ID: " + selectedOrder.getOrder_id() + "\n"),
                    new Text("User ID: " + selectedOrder.getUser_id() + "\n"),
                        new Text("Cart ID: " + selectedOrder.getCart_id() + "\n"),
                    new Text("Order Date: " + selectedOrder.getOrder_date() + "\n"),
                    new Text("Total Amount: " + selectedOrder.getTotal_amount() + "\n")
                );               
                Printer printer = Printer.getDefaultPrinter();
                PrinterJob job = PrinterJob.createPrinterJob();
                if (printer != null && job != null) {
                    boolean proceed = job.showPrintDialog(tableorder.getScene().getWindow());
                    if (proceed) {
                        boolean printed = job.printPage(textFlow);
                        if (printed) {
                            job.endJob();
                            alert.showAlert("Order printed successfully!");
                        } else {
                            alert.showAlert("Failed to print the order.");
                        }
                    }
                } else {
                    alert.showAlert("No printer found or print job creation failed.");
                }
            }
            
           

            







        //Orderdetail
    public void getOrderdetailView(ActionEvent event) {
        ModelOrder selectedOrder = tableorder.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            new Alert().showAlert("No order selected");
            return;
        }
        try {
           int getOrderid = Integer.parseInt(txtidorder.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Detail.fxml"));
            Parent root = loader.load();
            DetailController detail = loader.getController();

            // Set the order ID in the DetailController
            detail.setOrderid(getOrderid);

            // Display the Detail.fxml view (assuming you want to show it)
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {}    
    }
    
    







      
}
