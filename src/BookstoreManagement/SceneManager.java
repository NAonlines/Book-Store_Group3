package BookstoreManagement;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {

    private Stage stage;
    private HashMap<String, Scene> scenes = new HashMap<>();
    private HashMap<String, Object> controllers = new HashMap<>();

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void addScene(String name, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scenes.put(name, scene);
        controllers.put(name, loader.getController());
    }

    public void switchTo(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            stage.setScene(scene);
            stage.show();
        }
    }

    public Object getController(String name) {
        return controllers.get(name);
    }
}
