import java.util.HashMap;
import java.util.Map;

public class Character {
    private String name;
    private String path;  // maybe a folder or base path for character resources
    protected Map<String, String> stateImages = new HashMap<>();
    private int x;
    private int y;
    private double scale;


    // Default constructor
    public Character() {
    }
    
    // Constructor with initialization
    public Character(String name, String path) {
        this.name = name;
        this.stateImages.put("defual", path);
        this.path = path;
        selectXY();
    }

    private void selectXY() {
    	if(this.name.equals("Sky")) {
    		if(this.path.substring(this.path.length()-5).equals("happy")) {
    			this.x=0;
    			this.y=0;
    			this.scale=1.0;
    		}else {
    			this.x=-100;
        		this.y=0;
        		this.scale=1.5;
    		}
    		
    	}
    	if(this.name.equals("Clark")){
    		this.x=0;
    		this.y=0;
    		this.scale = 1.0;
    	}
    }
    
    public double getScale() {return this.scale;}
    public int getX() {return this.x;}
    public int getY() {return this.y;}
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
    
    public String toString() {
    	return this.name;
    }
}
