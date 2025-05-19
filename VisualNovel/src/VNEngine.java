import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class VNEngine extends Application {
    private SceneController controller;
    private List<String> sceneTree;

    @Override
    public void start(Stage stage) {
    	loadSceneTree();
        controller = new SceneController(stage,sceneTree);

        controller.registerScene("ch1_0", () -> new IntroScene(800, 600));
        controller.registerScene("ch1_1"),() -> new GameScene());
        // register many more scenes...
        
        controller.setScene("ch1_0");
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	private void loadSceneTree() {
		sceneTree = new ArrayList<String>();
		sceneTree.add("ch1_0");
		sceneTree.add("ch1_1");
	}
}
