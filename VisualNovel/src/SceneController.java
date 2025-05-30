import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javafx.stage.Stage;

public class SceneController implements SceneCompleteListener{
    private Stage stage;
    // Map to store suppliers, potentially with a cache of weak references to GameScene instances
    private Map<String, SceneEntry> sceneEntries = new HashMap<>();
    private List<String> sceneTree;
    private GameScene currentScene;
    private int currentSceneIndex;
    private String previousId;

    // Inner class to hold the supplier and a weak reference to the GameScene instance
    private static class SceneEntry {
        final Supplier<GameScene> factory;
        WeakReference<GameScene> cachedScene;

        SceneEntry(Supplier<GameScene> factory) {
            this.factory = factory;
        }

        GameScene getScene() {
            GameScene scene = null;
            if (cachedScene != null) {
                scene = cachedScene.get();
            }
            if (scene == null) {
                scene = factory.get();
                cachedScene = new WeakReference<>(scene);
            }
            return scene;
        }
    }

    public SceneController(Stage stage, List<String>tree) {
        this.stage = stage;
        this.sceneTree = tree;
        this.currentSceneIndex = 0;
    }

    public void registerScene(String id, Supplier<GameScene> factory) {
        sceneEntries.put(id, new SceneEntry(factory));
    }

    public void setScene(String id) {
        SceneEntry entry = sceneEntries.get(id);
        if (entry != null) {
            currentScene = entry.getScene(); // Get the scene, potentially from cache or created by factory
            currentScene.setSceneCompleteListener(this);
            stage.setScene(currentScene.getScene());
            stage.show();
            // Note: You removed the line sceneFactories.put(this.previousId, null);
            // which was setting the previous supplier to null.
            // With WeakReference, the scene itself can be garbage collected if not strongly referenced elsewhere.
            // You might not need to explicitly remove it from the map unless you want to unregister the supplier entirely.
        } else {
            System.err.println("Scene factory not found: " + id);
        }
    }

    @Override
    public void onSceneComplete(GameScene completedScene) {
        System.out.println("[INFO] current scene id: " + getId(this.currentSceneIndex));
        this.previousId = getId(this.currentSceneIndex);
        if(this.currentSceneIndex + 1 >= sceneTree.size()) {
            return;
        }
        System.out.println("[INFO] next scene id: "+ getId(this.currentSceneIndex + 1));
        String nextId = getId(this.currentSceneIndex + 1);
        this.currentSceneIndex++;
        setScene(nextId);
    }

    private String getId(int index) {
        return sceneTree.get(index);
    }

    // The findNextId method is not used in your current logic but is kept for completeness.
    // It would be more robust to use sceneTree.indexOf(id) if you needed to find an index by ID.
    private Integer findNextId(String id) {
        for(int i = 0; i < sceneTree.size(); i++) {
            if(sceneTree.get(i).equals(id)) { // Use .equals() for String comparison
                return i;
            }
        }
        return null;
    }
}