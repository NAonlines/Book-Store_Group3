package BookstoreManagement;

import Li_Alert.Alert;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BooksProductController implements Initializable {
    

    @FXML
    private Button books_bookbuy;

    @FXML
    private ImageView books_bookimgpr;

    @FXML
    private Label books_bookprice, books_namebooks;

    @FXML
    private AnchorPane books_bookview;

    private ModelBooks dataBooks;

    private Image img;
    
    
    
        Alert alert = new Alert();
        private Connection conn;
        private PreparedStatement pstmt;
        private ResultSet rs;

    public void setData(ModelBooks Data) {
        this.dataBooks = Data;

        String imagePath = Data.getBookimg();
        File file = new File(imagePath);
        if (file.exists()) {
            img = new Image(file.toURI().toString(), 290, 340, false, true);
            books_bookimgpr.setImage(img);
        } else {
            System.out.println("Image file not found: " + imagePath);
        }

        books_bookprice.setText(String.valueOf(Data.getPrice()+"$"));
        books_namebooks.setText(Data.getTitle());
    }
    
    @FXML
    void handle_book(ActionEvent event) throws SQLException, IOException {
    
    String sql = "SELECT * FROM Books WHERE title = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setString(1, books_namebooks.getText());
    rs = pstmt.executeQuery();
    
    if (rs.next()) {
        
        Integer stock = rs.getInt("stock");
        Integer booksId = rs.getInt("book_id");
        String bookName = rs.getString("title");
        
        if (stock > 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMain.fxml"));
            Parent root = loader.load();
            
            UserMainController userMain = loader.getController();
            userMain.setBookID(booksId);
            userMain.setBookTitle(bookName);
            
            Stage stage = (Stage) books_bookview.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            alert.showAlert("The book is out of stock.");
        }
        
    } else {
        alert.showAlert("The book you are looking for is not available.");
    }
}

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = ConnectDB.ConnectDB.getConnectDB();
        if(conn==null){
            System.out.println("Connect Failed");
        }
    }
}
