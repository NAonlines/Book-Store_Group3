package BookstoreManagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookstoreManagement extends Application {

    @Override
    public void start(Stage Stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMain.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage.initStyle(StageStyle.UNDECORATED);
            Stage.setScene(scene);
            Stage.show();
            LoginController controller = loader.getController();
            controller.initStage(Stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    

}
