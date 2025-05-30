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
        
        controller.registerScene("ch1_0", () -> new IntroScene(1280, 720, "bg/title.jpg"));
        controller.registerScene("ch1_1", () -> new IntroScene(1280, 720, "bg/bg001c.png"));
        controller.registerScene("ch1_2", () -> new GameScene(1280, 720, "bg/bg001.png", "H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\test.json","H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM03.mp3"));
        controller.registerScene("ch1_3", () -> new GameScene(1280, 720, "bg/ev003el.png", "H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\test.json","H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM03.mp3"));
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
		//sceneTree.add("ch1_1");
		sceneTree.add("ch1_2");
		sceneTree.add("ch1_3");
	}
}
