import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class GameScene {
    protected String id;
    protected Scene scene;
    protected StackPane root;
    
    protected Label dialogueLabel;
    protected Label nameLabel;
    protected ImageView bgImageView;
    protected ImageView dialogueBg;
    
    protected int currentIndex = 0;
    protected DialogueManager DM;
    protected SceneCompleteListener sceneCompleteListener;

    public GameScene(int width, int height, String bg) {
        initCommonUI(width, height);
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-pref-width: 120; -fx-pref-height: 90;");
        startButton.setOnAction(e -> onSceneComplete());
        StackPane.setAlignment(startButton, Pos.CENTER_LEFT);
        StackPane.setMargin(startButton, new Insets(0, 0, 50, 0));
        setBackground(bg);
        root.getChildren().add(startButton);
    }

    public GameScene(int width, int height, String bgPath, String textPath) {
        initCommonUI(width, height);
        scene.setOnMouseClicked(e -> nextDialogue());
        setBackground(bgPath);
        initDialogueText();
        initCharacterName();
        loadDialogue(textPath);
        
    }
    
    private void initCommonUI(int width, int height) {
        root = new StackPane();
        root.setStyle("-fx-padding: 20px;");
        scene = new Scene(root, width, height);
    }
    
    protected void setBackground(String bgPath) {
    	Image bgImage = new Image(bgPath);
    	bgImageView = new ImageView(bgImage);
    	bgImageView.setFitWidth(scene.getWidth());
    	bgImageView.setFitHeight(scene.getHeight());
    	bgImageView.setPreserveRatio(false);
    	root.getChildren().add(bgImageView);
    }
    
    private void initDialogueBackground() {
    	Image bgImage = new Image(""); // TODO add patht o dialogue background
    	dialogueBg = new ImageView(bgImage);
    	dialogueBg.setFitWidth(400);
    	dialogueBg.setFitHeight(200);
    	StackPane.setAlignment(dialogueBg, Pos.BOTTOM_CENTER);
    }
    
    private void initCharacterName() {
    	nameLabel = new Label();
    	nameLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    	nameLabel.setWrapText(true);
    	nameLabel.setOpacity(1);
    	StackPane.setAlignment(nameLabel, Pos.BOTTOM_LEFT);
    	StackPane.setMargin(nameLabel, new Insets(0,0,100,100));
    	root.getChildren().add(nameLabel);
    }
    private void initDialogueText() {
    	dialogueLabel = new Label();
    	dialogueLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    	dialogueLabel.setWrapText(true);
    	dialogueLabel.setOpacity(0.8);
    	StackPane.setAlignment(dialogueLabel, Pos.BOTTOM_CENTER);
    	// insets top right bottom left
    	StackPane.setMargin(dialogueLabel, new Insets(0,0,100,0));
    	root.getChildren().add(dialogueLabel);
    }
    
    
    public Scene getScene() {
    	return scene;
    }


    protected void loadDialogue(String textPath) {
        DM = new DialogueManager(_loadDialogues(textPath));
        if (DM != null && !DM.isEmpty()) {
            DialogueLine line = DM.getCurrentLine();
            showDialogue(line);
            DM.nextLine();
        }
    }

    protected void showDialogue(DialogueLine line) {
        dialogueLabel.setText(line.getText());
        nameLabel.setText(line.getCharacter().getName());
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), dialogueLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    protected void nextDialogue() {
        if (DM != null && DM.hasNext()) {
            DialogueLine line = DM.getCurrentLine();
            showDialogue(line);
            DM.nextLine();
        } else {
        	System.out.println("mouse click triggered complete");
            onSceneComplete(); // When no more dialogue
        }
    }
    
    public void setSceneCompleteListener(SceneCompleteListener listener) {
        this.sceneCompleteListener = listener;
    }


    protected void onSceneComplete() {
        if (sceneCompleteListener != null) {
        	FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
        	fadeOut.setFromValue(100);
        	fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> sceneCompleteListener.onSceneComplete(this));
        	fadeOut.play();
            //sceneCompleteListener.onSceneComplete(this);
        }
    }

    protected List<DialogueLine> _loadDialogues(String textPath) {
    	return loadTest.loadDialoguesFromCSV(textPath);
    }
}
