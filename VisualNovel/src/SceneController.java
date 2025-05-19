import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javafx.stage.Stage;

public class SceneController implements SceneCompleteListener{
    private Stage stage;
    private Map<String, Supplier<GameScene>> sceneFactories = new HashMap<>();
    private List<Supplier<GameScene>> sceneList = new ArrayList<>();
    private List<String> sceneTree;
    private GameScene currentScene;
    private int currentSceneIndex;


    public SceneController(Stage stage, List<String>tree) {
        this.stage = stage;
        this.sceneTree = tree;
        this.currentSceneIndex = 0;

    }

    public void registerScene(String id, Supplier<GameScene> factory) {
        sceneFactories.put(id, factory);
        
    }
    
    public void setScene(String id) {
        Supplier<GameScene> factory = sceneFactories.get(id);
        if (factory != null) {
            currentScene = factory.get();
            currentScene.setSceneCompleteListener(this); 
            stage.setScene(currentScene.getScene());
            stage.show();
        } else {
            System.err.println("Scene factory not found: " + id);
        }
    }
    
    
    
	@Override
	public void onSceneComplete(GameScene completedScene) {
		System.out.println("[INFO] current scene id: " + getId(this.currentSceneIndex));
		System.out.println("[INFO] next scene id: "+ getId(this.currentSceneIndex + 1));
		String nextId = getId(this.currentSceneIndex + 1);
		this.currentSceneIndex ++;
		setScene(nextId);
	}
	

	private String getId(int index) {
		return sceneTree.get(index);
	}
	
	
	private Integer findNextId(String id) {
		for(int i=0; i< sceneTree.size();i++) {
			if(sceneTree.get(i)==id) {
				return i;
			}
		}
		return null;
	}

}
