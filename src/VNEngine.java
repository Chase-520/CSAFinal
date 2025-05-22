/*
 * path: /h/git/CSAFinal
 */

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
        
        controller.registerScene("ch1_0", () -> new IntroScene(800, 600, "bg/bg001a.png"));
        controller.registerScene("ch1_1", () -> new IntroScene(800, 600, "bg/bg001c.png"));
        controller.registerScene("ch1_2", () -> new GameScene(800, 600, "bg/bg001.png", "H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\something.csv"));
        controller.registerScene("ch1_3", () -> new GameScene(800, 600, "bg/ev003el.png", "H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\ch1_1.csv"));
        // register many more scenes...
        
        controller.setScene("ch1_0");
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	private void loadSceneTree() {
		// TODO read from file??
		sceneTree = new ArrayList<String>();
		sceneTree.add("ch1_0");
		sceneTree.add("ch1_1");
		sceneTree.add("ch1_2");
		sceneTree.add("ch1_3");
	}
}
