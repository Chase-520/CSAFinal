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
        
        controller.registerScene("Title", () -> new IntroScene(1280, 720, "bg/StartManu.png"));
        controller.registerScene("ch1_0", () -> new GameScene(1280, 720, "bg/bg001.png", "H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\ch1_0.json","H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM05.mp3"));
        controller.registerScene("ch1_1", () -> new GameScene(1280, 720, "bg/ev003el.png", "H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\ch1_1.json","H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM03.mp3"));
        controller.registerScene("ch1_2", () -> new GameScene(1280, 720, "bg/ev003el.png", "H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\ch1_2.json","H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM04.mp3"));

        // register many more scenes...
        
        controller.setScene("Title");
    }

    public static void main(String[] args) {
        launch(args);
    }
    
	private void loadSceneTree() {
		// TODO read from file??
		sceneTree = new ArrayList<String>();
		sceneTree.add("Title");
		sceneTree.add("ch1_0");
		sceneTree.add("ch1_1");
		sceneTree.add("ch1_2");
	}
}
