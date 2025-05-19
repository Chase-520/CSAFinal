import java.util.HashMap;
import java.util.Map;

public class Character {
    private String name;
    private String path;  // maybe a folder or base path for character resources
    protected Map<String, String> stateImages = new HashMap<>();

    // Default constructor
    public Character() {
    }

    // Constructor with initialization
    public Character(String name, String path) {
        this.name = name;
        this.stateImages.put("defual", path);
        this.path = path;
        
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for path
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    // Methods to manipulate state images
    public void addStateImage(String state, String imagePath) {
        stateImages.put(state, imagePath);
    }

    public String getStateImage(String state) {
        return stateImages.get(state);
    }
}
