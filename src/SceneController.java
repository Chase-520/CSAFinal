import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javafx.stage.Stage;

public class SceneController {
    private Stage stage;
    private Map<String, Supplier<GameScene>> sceneFactories = new HashMap<>();
    private GameScene currentScene;

    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public void registerScene(String id, Supplier<GameScene> factory) {
        sceneFactories.put(id, factory);
    }

    public void setScene(String id) {
        Supplier<GameScene> factory = sceneFactories.get(id);
        if (factory != null) {
            currentScene = factory.get();
            stage.setScene(currentScene.getScene());
            stage.show();
        } else {
            System.err.println("Scene factory not found: " + id);
        }
    }
}
