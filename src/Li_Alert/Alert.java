
package Li_Alert;

import javafx.scene.control.Alert.AlertType;


public class Alert {
     public void showAlert(String content){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert( AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
